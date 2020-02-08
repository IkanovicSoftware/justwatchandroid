package com.example.justwatch;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;


public class LobbyActivity extends AppCompatActivity {

    TextView tvUsername;
    Button bCreateGroup;
    Button bPrivateMessage;

    public int uid = -1;
    ArrayList<String> gids = new ArrayList<>();
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        tvUsername = findViewById(R.id.username);
        bCreateGroup = findViewById(R.id.button_creategroup);
        bPrivateMessage = findViewById(R.id.button_messages);

        /*
            Grab username and post it up dynamically onscreen
         */
        username = getIntent().getStringExtra("USERNAME");
        tvUsername.setText(username);

        /*
           Makes a request for UID and then collects all the groups the user is a part of
         */
        makeRequest(new JsonObjectListener() {
            @Override
            public void onDone(int change) {
                uid = change;
            }
        });


        bCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(LobbyActivity.this, CreateGroupActivity.class);
                startActivity(next);
                finish();
            }
        });

        bPrivateMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(LobbyActivity.this, PrivateMessageActivity.class);
                startActivity(next);
                finish();
            }
        });

    }

    public void makeRequest(final JsonObjectListener listener) {
        // Make a request for users UID
         JsonArrayRequest jsonArrayUID = new JsonArrayRequest
                (Request.Method.GET, URL_S.URL_GET_USER + username, null, new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            uid = response.getJSONObject(0).getInt("uid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listener.onDone(uid);

                        // Make a request for all groups (GID) the user is in
                        JsonArrayRequest jsonArrayGIDsOfUID = new JsonArrayRequest
                                (Request.Method.GET, URL_S.URL_GROUPS_USER_IN + uid, null, new Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        // Loop through json array of groups the user is part of and create buttons for each group
                                        for (int i = 0; i < response.length(); i++) {
                                            try {
                                                JSONObject obj = response.getJSONObject(i); //JSON object of the JSON array
                                                final int gidOfObj = obj.getInt("gid"); //"GID" of that object

                                                Log.d("user GID's ",gidOfObj + "");

                                                //gids.add(obj.getInt("gid") + "");

                                                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                                                        URL_S.URL_GET_GROUP + gidOfObj, null, new Response.Listener<JSONObject>() {

                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            String name = response.getString("name");
                                                            LinearLayout layout = findViewById(R.id.linearLayout);
                                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                            Button btn = new Button(LobbyActivity.this);
                                                            btn.setId(gidOfObj);
                                                            btn.setText("Group: " + name);
                                                            layout.addView(btn, params);

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.d("VOLLEY ERROR ", error.toString());
                                                    }
                                                });

                                                // Adding request to request queue
                                                AppController.getInstance().addToRequestQueue(jsonObjReq);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("VOLLEY ERROR ", error.toString());
                                    }
                                });

                        AppController.getInstance().addToRequestQueue(jsonArrayGIDsOfUID);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY ERROR ", error.toString());
                    }
                });

        AppController.getInstance().addToRequestQueue(jsonArrayUID);
    }
}


