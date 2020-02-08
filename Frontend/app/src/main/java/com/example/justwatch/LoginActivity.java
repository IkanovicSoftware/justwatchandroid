package com.example.justwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // Objects on screen
    Button bLogin, bRegister;
    EditText etUsername, etPassword;
    TextView etLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etLoginError = findViewById(R.id.login_error);

        bLogin = findViewById(R.id.login);
        bRegister = findViewById(R.id.register);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                /*
                    If regex test pass send information with volley request and check if username
                    and password are located in database
                 */
                if (isUsernameValid(username) && isPasswordValid(password)) {
                    StringRequest stringLoginRequest = new StringRequest(Request.Method.POST, URL_S.URL_LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("LOGIN?  ", response);

                            if (response.equals("success")) {
                                Intent next = new Intent(LoginActivity.this, LobbyActivity.class);
                                next.putExtra("USERNAME", username);
                                startActivity(next);
                                finish();
                            } else if (response.contains("invaliduser")) {
                                etLoginError.setText("User does not exist");
                            } else if (response.equals("failure")) {
                                etLoginError.setText("Invalid Password");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Volley Error: ", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("password", password);
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringLoginRequest);
                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    /*
        Method checks if the users input in etUsername is valid
     */
    public boolean isUsernameValid(String username) {
        String usernameRegex = "^[a-zA-Z0-9]+$"; //Only letters and numbers
        return username.matches(usernameRegex) && username.length() >= 3 && username.length() <= 15;
    }

    /*
        Method checks if the users input in etPassword is valid
     */
    public boolean isPasswordValid(String password) {
        String passwordRegex = "^[a-zA-Z0-9]+$"; // 6 to 15, every character works
        return password.matches(passwordRegex) && password.length() >= 6 && password.length() <= 15;
    }


}
