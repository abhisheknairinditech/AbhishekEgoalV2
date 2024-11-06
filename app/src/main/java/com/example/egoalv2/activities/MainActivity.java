package com.example.egoalv2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egoalv2.interfaces.ApiClient;
import com.example.egoalv2.R;
import com.example.egoalv2.interfaces.ApiService;
import com.example.egoalv2.request.LoginRequest;
import com.example.egoalv2.response.LoginResponse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private CheckBox rememberPasswordCheckBox;
    private TextView forgotPasswordTextView;
    private ProgressBar progressBar;
    private ImageView togglePasswordVisibility;
    private boolean isPasswordVisible = false;
    private String pass;

    private SharedPreferences sharedPreferences, sharedPref;
    private static final String PREF_NAME = "LOGINDETAILS";
    // Encryption key and salt (make sure to store them securely)
    private static final String SECRET_KEY = "S7h^k9Lz*R!8rL1uNb#X@zPQ5uT3gBsH";
    private static final String SALT = "a1b2c3d4e5f6g7h8"; // Ensure this is securely managed and unique
    private static final String SALT_HEADER = "Salted__";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        rememberPasswordCheckBox = findViewById(R.id.remember_password);
        forgotPasswordTextView = findViewById(R.id.forgot_password);
        progressBar = findViewById(R.id.progress_bar);
        togglePasswordVisibility = findViewById(R.id.toggle_password_visibility);

        sharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Set up the click listener for the toggle icon
        togglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                if (isPasswordVisible) {
                    // Hide password
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    togglePasswordVisibility.setImageResource(R.drawable.baseline_lock_24); // Lock icon when password is hidden
                } else {
                    // Show password
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    togglePasswordVisibility.setImageResource(R.drawable.baseline_lock_open_24); // Unlock icon when password is shown
                }
                // Toggle the boolean flag
                isPasswordVisible = !isPasswordVisible;

                // Move cursor to the end of the text
                passwordEditText.setSelection(passwordEditText.length());
            }
        });
        // Load saved credentials if Remember Password is checked
        loadSavedCredentials();

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (!username.isEmpty() && !password.isEmpty()) {
                // Perform login
                //new LoginTask().execute(username, encryptPassword(password));
                pass=password;
                login(username, encryptPassword(password));
            } else {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Forgot Password click
        forgotPasswordTextView.setOnClickListener(v -> {
            // Handle forgot password action (e.g., navigate to another activity)
            Toast.makeText(MainActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
        });
    }

    public static SecretKey generateKey(int n) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(n); // n can be 128, 192, or 256
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    //    private String encryptPassword(String password) {
//        try {
//            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
//            @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
//            return Base64.encodeToString(encrypted, Base64.DEFAULT).trim();
//        } catch (Exception e) {
//            Log.e("EncryptionError", "Error during password encryption", e);
//            return null; // Handle this better, maybe show a message to the user
//        }
//    }
    private String encryptPassword(String password) {
        try {
            // Generate a random salt
            byte[] salt = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);

            // Derive key and IV from the password and salt
            byte[] keyIv = deriveKeyAndIv(SECRET_KEY.getBytes(), salt);
            byte[] key = new byte[32]; // AES-256 needs a 256-bit key (32 bytes)
            byte[] iv = new byte[16];  // AES uses a 128-bit IV (16 bytes)

            System.arraycopy(keyIv, 0, key, 0, 32);
            System.arraycopy(keyIv, 32, iv, 0, 16);

            // Create the cipher instance and initialize it
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Encrypt the password
            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            // Combine salt and encrypted data (prefix with "Salted__" + salt)
            byte[] saltedEncrypted = new byte[SALT_HEADER.getBytes().length + salt.length + encrypted.length];
            System.arraycopy(SALT_HEADER.getBytes(), 0, saltedEncrypted, 0, SALT_HEADER.getBytes().length);
            System.arraycopy(salt, 0, saltedEncrypted, SALT_HEADER.getBytes().length, salt.length);
            System.arraycopy(encrypted, 0, saltedEncrypted, SALT_HEADER.getBytes().length + salt.length, encrypted.length);

            // Encode the result in Base64
            return Base64.encodeToString(saltedEncrypted, Base64.DEFAULT).trim();

        } catch (Exception e) {
            Log.e("EncryptionError", "Error during password encryption", e);
            return null; // Handle this better, maybe show a message to the user
        }
    }

    // Derive the key and IV from the password and salt using OpenSSL's algorithm
    private static byte[] deriveKeyAndIv(byte[] password, byte[] salt) throws Exception {
        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] keyAndIv = new byte[48]; // 32 bytes for key, 16 bytes for IV
        byte[] buffer = null;
        int currentPos = 0;

        // Iteratively generate the key and IV
        while (currentPos < 48) {
            if (buffer != null) {
                md5.update(buffer);
            }
            md5.update(password);
            md5.update(salt);
            buffer = md5.digest();

            int length = Math.min(buffer.length, 48 - currentPos);
            System.arraycopy(buffer, 0, keyAndIv, currentPos, length);
            currentPos += length;
        }
        return keyAndIv;
    }

    private void login(String username, String password) {
        if (password != null) {
            Log.d("EncryptedPassword", password);
        } else {
            Log.e("EncryptionError", "Password encryption returned null");
        }
        progressBar.setVisibility(View.VISIBLE);

        // Perform login here (API call)

        LoginRequest loginRequest = new LoginRequest(username, password);
        Log.d("LOGINREQUEST", loginRequest.toString());
        // Use Retrofit to call the login API
        ApiService apiService = ApiClient.getApiService();
        Call<LoginResponse> call = apiService.login(loginRequest);

        // Enqueue the API call to execute asynchronously
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Log.d("LOGINRESPONSE", loginResponse.toString());
                    SharedPreferences.Editor editor1 = sharedPref.edit();

                    // If login is successful, save credentials if Remember Password is checked
                    if (rememberPasswordCheckBox.isChecked()) {

                        editor1.putString("username", username);
                        editor1.putString("password", pass); // Store encrypted password for security
                        editor1.apply();
                    }
                    // Store details in SharedPreferences
                    sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("sessiontoken", loginResponse.getToken());
                    editor.putString("concessionaireId", loginResponse.getUserConcessionaireId());
                    editor.putString("firstname", loginResponse.getFirstName());
                    editor.putString("userrole", loginResponse.getUserRole());
                    String propertyaccess = "";
                    for (Object st : loginResponse.getPropertyId()) {
                        propertyaccess += st;

                    }
                    editor.putString("propertyaccess", propertyaccess);
                    editor.apply();

                    // Handle the successful login (e.g., store session token)
                    if (!loginResponse.getUserRole().equals("sadmin") && !loginResponse.getUserRole().equals("tenant")) {
                        // If user type is not "sadmin" or "tenant", proceed to Dashboard
                        progressBar.setVisibility(View.GONE);
                        Log.d("SHAREDPREFERENCE", "\nsessiontoken: " + loginResponse.getToken() + "\nconcessionaireId: " +
                                loginResponse.getUserConcessionaireId() + "\nfirstname: " + loginResponse.getFirstName() + "\nuserrole: " + loginResponse.getUserRole() + "\npropertyid: " + propertyaccess);

                        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    } else {
                        // If user type is "sadmin" or "tenant", show a toast message
                        Toast.makeText(MainActivity.this, "You are not authorized to access the dashboard.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {

                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadSavedCredentials() {
        String savedUsername = sharedPref.getString("username", null);
        String savedPassword = sharedPref.getString("password", null);
        if (savedUsername != null && savedPassword != null) {
            usernameEditText.setText(savedUsername);
            passwordEditText.setText(savedPassword);
            rememberPasswordCheckBox.setChecked(true);
        }
    }
}