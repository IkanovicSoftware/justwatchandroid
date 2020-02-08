package com.example.justwatch;

import android.app.Activity;

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
public class GroupActivity extends Activity {

    //objects on screen
    Button bVideoScreen, bGroupChatScreen, bLobbyScreen;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bVideoScreen = findViewById(R.id.watch_video);
        bGroupChatScreen = findViewById(R.id.group_chat);
        bLobbyScreen = findViewById(R.id.back_to_lobby_button);
/*
        bVideoScreen.setOnClickListener(new View.onClickListener(){
            public void onClick(View view){
                Intent videoIntent = new Intent(view.getContext(), this_screen_hasnt_been_made_yet);
                startActivityForResult(videoIntent, 0);
            }
        });

 */

     /*
        bGroupChatScreen.setOnClickListener(new View.onClickListener(){
            public void onClick(View view){
                Intent groupChatIntent = new Intent(view.getContext(), this_screen_hasnt_been_made_yet);
                startActivityForResult(groupChatIntent, 0);
            }
        });

 */
//        bLobbyScreen.setOnClickListener(new View.onClickListener(){
//            public void onClick(View view){
//                Intent lobbyIntent = new Intent(view.getContext(), LobbyActivity.class);
//                startActivityForResult(lobbyIntent, 0);
//            }
//        });

    }
}
