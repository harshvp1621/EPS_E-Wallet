package com.journaldev.barcodevisionapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends Activity {

    Button btnLogin;

    EditText txtUsername, txtPassword;
    String authToken;

    // User Session Manager Class
    UserSessionManager session = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // User Session Manager

        session = new UserSessionManager(getApplicationContext());
        Intent activityIntent;

        if(session.isUserLoggedIn()){
            activityIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activityIntent);
            finish();
        }

        // get Email, Password input text
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();


        // User Login button
        btnLogin = findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String passwordHash = GenSHA256.genSHA256Hash(password);
                try{

                    authToken = GetAuthToken.getAuthTokenFromServer("http://10.2.77.214/api/login",username,passwordHash);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                // Validate if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){

                    if(!authToken.equals("INVALID_CREDENTIALS")){

                        // Creating login session
                        // Username and AuthToken
                        if(authToken.equals(null)){
                            Toast.makeText(getApplicationContext(),
                                    "NULL Token Received Try Again",
                                    Toast.LENGTH_LONG).show();
                        }else {
                            session.createUserLoginSession(username, authToken);

                            // Starting MainActivity
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                            finish();
                        }

                    }else{

                        // username / password doesn't match&
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();


                    }
                }else{

                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                            "Please enter username and password",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}