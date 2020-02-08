package com.example.justwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {

    Button bCreateGroup;
    TextView tvGroupName;
    TextView tvInviteMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        bCreateGroup = findViewById(R.id.button_creategroup);

        tvGroupName = findViewById(R.id.input_groupname);
        tvInviteMembers = findViewById(R.id.input_invite);

//        bCreateGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_S.URL_NEW_GROUP, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("CREATE?  ", response);
//
//                        if (response.equals("")) {
//                            Intent next = new Intent(LoginActivity.this, LobbyActivity.class);
//
//                        } else if (response.equals("failure")) {
//                            etLoginError.setText("Invalid Password");
//                        } else if (response.contains("invaliduser")) {
//                            etLoginError.setText("User does not exist");
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Volley Error: ", error.toString());
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("username", username);
//                        params.put("password", password);
//                        return params;
//                    }
//                };
//                AppController.getInstance().addToRequestQueue(stringLoginRequest);
//            }
    }
}
