package com.lorenzomiro.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private EditText etUsername;

    private EditText etPassword;

    private Button login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);

        etPassword = findViewById(R.id.etPassword);

        login_button = findViewById(R.id.login_button);

    }
}