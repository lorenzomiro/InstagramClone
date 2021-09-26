package com.lorenzomiro.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private EditText etUsername;

    private EditText etPassword;

    private Button login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {

            go_to_main_activity();

        }

        etUsername = findViewById(R.id.etUsername);

        etPassword = findViewById(R.id.etPassword);

        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login btnCaptureImage");

                String username = etUsername.getText().toString();

                String password = etPassword.getText().toString();

                login_user(username, password);

            }
        });
    }

    private void login_user(String username, String password) {

        Log.i(TAG, "Attempting to login user: " + username);

        //go to main activity if user signs in properly

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login:", e);

                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT);

                    return;
                }
                go_to_main_activity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void go_to_main_activity() {

        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);

    }
}