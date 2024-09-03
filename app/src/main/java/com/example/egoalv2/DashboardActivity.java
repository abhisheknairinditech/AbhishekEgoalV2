package com.example.egoalv2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    private TextView usernameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        usernameTextView=findViewById(R.id.username);

        SharedPreferences sharedPref = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String firstname = sharedPref.getString("firstname", "User");

        usernameTextView.setText(firstname);

    }
}