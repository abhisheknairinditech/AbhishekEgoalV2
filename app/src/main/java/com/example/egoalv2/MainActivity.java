package com.example.egoalv2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.util.Base64;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.example.egoalv2.interfaces.APIService;
import com.example.egoalv2.model.LoginRequest;
import com.example.egoalv2.model.LoginResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;


    // Encryption key and salt (make sure to store them securely)
    private static final String SECRET_KEY = "S7h^k9Lz*R!8rL1uNb#X@zPQ5uT3gB";
    private static final String SALT = "a1b2c3d4e5f6g7h8"; // Ensure this is securely managed and unique

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        usernameEditText=findViewById(R.id.username);
        passwordEditText=findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_bar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Call the API to authenticate the user
//                    new LoginTask().execute(username, encryptPassword(password));
                    new LoginTask().execute(username, password);
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String encryptPassword(String password) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show the ProgressBar
            //progressBar.setVisibility(View.VISIBLE);
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait while checking your credentials...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            try {
                // Prepare the JSON payload
                JSONObject payload = new JSONObject();
                payload.put("username", username);
                payload.put("password", password); // Send plain text if encryption is server-side

                // Create URL connection
                URL url = new URL("http://13.235.215.57:8081/api//auth/login//");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Write the payload to the output stream
                OutputStream os = connection.getOutputStream();
                os.write(payload.toString().getBytes("UTF-8"));
                os.close();

                // Check response code
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response
                    StringBuilder response = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse JSON response
                    JSONObject responseObject = new JSONObject(response.toString());
                    String sessiontoken = responseObject.getString("token");
                    String concessionaireId = responseObject.getString("user_concessionaire_id");
                    String usertype = responseObject.getString("user_role");
                    String first_name = responseObject.getString("first_name");
                    String loginId = responseObject.getString("user_id");
                    String usertypeId = responseObject.getString("user_type_id");
                    String loginName = responseObject.getString("user_name");
                    String concessionaireName = responseObject.getString("user_concessionaire_name");
                    String propertyAccess = responseObject.getString("property_id");


                    // Store details in SharedPreferences
                    SharedPreferences sharedPref = getSharedPreferences("UserDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("sessiontoken", sessiontoken);
                    editor.putString("concessionaireId", concessionaireId);
                    editor.putString("firstname",first_name);
                    editor.apply();

                    return true;
                } else {
                    // Failed login
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // Login successful, start DashboardActivity
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                // Login failed
                Toast.makeText(MainActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}