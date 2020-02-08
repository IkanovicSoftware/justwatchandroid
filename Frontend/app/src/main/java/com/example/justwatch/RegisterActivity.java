package com.example.justwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // Objects on screen
    Button bRegister, bBack;
    EditText etUsername, etPassword, etDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etDOB = findViewById(R.id.DOB);

        bRegister = findViewById(R.id.register);
        bBack = findViewById(R.id.back);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String DOB = etDOB.getText().toString();


                /*
                    Tests to see if user input meets regex requirements. If it passes then it sends a STRINGPOST
                    to server requesting to make new user with the given input. If it passes user is moved to the
                    lobby screen. If new user is not created - explain why
                 */
                if (isUsernameValid(username) && isPasswordValid(password) && isDOBValid(DOB)) {
                    StringRequest stringRegisterRequest = new StringRequest(Request.Method.POST, URL_S.URL_NEW_USER,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Response ", response);

                                    // If new user is added - got to lobby screen with new user
                                    // If information not valid - post text box explaining why
                                    if (response.contains("Added")) {
                                        Intent next = new Intent(RegisterActivity.this, LobbyActivity.class);
                                        next.putExtra("USERNAME", username);
                                        startActivity(next);
                                        finish();
                                    } else if (response.contains("username taken")) {
                                        new AlertDialog.Builder(RegisterActivity.this)
                                                .setTitle("Username Taken")
                                                .setMessage("Username is already taken")
                                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.cancel();
                                                    }
                                                })
                                                .show();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("VolleyError ", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", username);
                            params.put("password", password);
                            params.put("DOB", DOB);
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRegisterRequest);
                } else if (!isUsernameValid(username)) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Username Information")
                            .setMessage("Username must be at least 3 characters. Letters and numbers only.")
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                } else if (!isPasswordValid(password)) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Password Information")
                            .setMessage("Password must be at least 6 characters. Letters and numbers only.")
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                } else if (!isDOBValid(DOB)) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Date of Birth Information")
                            .setMessage("Date of birth format must be: mm-dd-yyyy")
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


    /*
       Method checks if the users input in etUsername is valid
    */
    public boolean isUsernameValid(String username) {
        String usernameRegex = "^[a-zA-Z0-9]+$";
        return username.matches(usernameRegex) && username.length() >= 3 && username.length() <= 15;
    }

    /*
        Method checks if the users input in etPassword is valid
    */
    public boolean isPasswordValid(String password) {
        String passwordRegex = "^[a-zA-Z0-9]+$";
        return password.matches(passwordRegex) && password.length() >= 6 && password.length() <= 15;
    }

    /*
        Method checks if the users input in etDOB is valid
    */
    public boolean isDOBValid(String DOB) {
        String DOBRegex = "^((0|1)\\d{1})-((0|1|2|3)\\d{1})-((19|20)\\d{2})";
        return DOB.matches(DOBRegex);
    }

}

