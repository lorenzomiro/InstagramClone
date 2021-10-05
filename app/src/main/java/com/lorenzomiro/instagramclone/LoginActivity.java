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
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private EditText etUsername;

    private EditText etPassword;

    private Button login_button;

    private Button sign_up_button;


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

        sign_up_button = findViewById(R.id.sign_up_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login btnCaptureImage");

                String username = etUsername.getText().toString();

                String password = etPassword.getText().toString();

                login_user(username, password);

            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick sign up button");

                String username = etUsername.getText().toString();

                String password = etPassword.getText().toString();

                create_user(username, password);

            }
        });
    }

    private void create_user(String username, String password){
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        //user.setEmail("email@example.com");
        // Set custom properties
        //user.put("phone", "650-253-0000");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    go_to_main_activity();
                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    // Hooray! Let them use the app now.
                } else {
                    Log.e(TAG, "Issue with sign up", e);
                    Toast.makeText(LoginActivity.this, "Issue with sign up.", Toast.LENGTH_SHORT).show();
                    return;
                }
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