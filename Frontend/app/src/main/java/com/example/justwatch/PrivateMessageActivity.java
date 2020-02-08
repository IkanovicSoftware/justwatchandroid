package com.example.justwatch;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;


/* not using at the moment
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
*/

import java.net.URI;
import java.net.URL;




import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URISyntaxException;

//import tech.gusavila92.websocketclient.WebSocketClient;


public class PrivateMessageActivity extends AppCompatActivity {

    private WebSocketClient cc;
    TextView personName, t1;
    Button pmToLobby, connectB;
    ImageButton messageSend;
    EditText messageField;
    String name;


    String message;

    /**
     * sets up buttons and on click listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_message);

        personName = findViewById(R.id.Message_Recipient);
        pmToLobby = findViewById(R.id.from_pm_to_lobby_button);
        messageSend = findViewById(R.id.pm_send_button);
        messageField = findViewById(R.id.message_field);
        t1 = findViewById(R.id.message_below_here);
        connectB =  (Button) findViewById(R.id.conectionButton);





        pmToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(PrivateMessageActivity.this, LobbyActivity.class);
                startActivity(next);
                finish();
            }
        });

        connectB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Draft[] drafts = {new Draft_6455()};


                /*
                You must change the ip address to either the server, or if you're running it locally you must
                use the ip address of the host computer, not localhost
                */

                String address = "ws://10.64.182.210:8080/messaging/" + personName.getText().toString();

                try {
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(address), drafts[0]) {
                        @Override
                        public void onOpen(ServerHandshake serverHandshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }

                        @Override
                        public void onMessage(String s) {
                            Log.d("", "run() returned: " + s);
                            String message = t1.getText().toString();
                            t1.setText( message + '@' +  s + '\n');

                        }

                        @Override
                        public void onClose(int i, String s, boolean b) {
                            Log.d("CLOSE", "onClose() returned: " + s);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                }
                catch(URISyntaxException e){
                    Log.d("Exception:", e.getMessage());
                    e.printStackTrace();
                }
                cc.connect();

            }
        });

        messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try{
                    cc.send(messageField.getText().toString());
                    messageField.setText(name);
                }
                catch(Exception e){
                    Log.d("ExceptionSendMessage:", e.getMessage());
                }
            }
        });
    }

}
