package com.example.notforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {

    private TextView Rules_button;

    private TextView play_button;
    private TextView play_friends_button;
    private TextView chatroom;

    private static final String url = "https://dry-island-87084.herokuapp.com/";
    private String username;

    private Socket mSocket;
    private EditText sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        intializedfields();

        UserDatabase db = new UserDatabase(MainActivity.this);
        username = db.getcurrentuser();

        if(TextUtils.isEmpty(username))
        {
            Intent intent =new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }
        else {

            Toast.makeText(MainActivity.this, "Welcome " + username, Toast.LENGTH_SHORT).show();
        }
        try {
            mSocket = IO.socket(url);
        }catch (URISyntaxException e){}

        Rules_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,rules_book.class);
                startActivity(i);
            }
        });

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        play_friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FindFriends.class);
                startActivity(i);
            }
        });
        chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ChatRoom.class);
                startActivity(i);
            }
        });

    }

    private void intializedfields()
    {
        Rules_button = (TextView) findViewById(R.id.Rules);
        play_button = (TextView) findViewById(R.id.Play_button);
        play_friends_button = (TextView) findViewById(R.id.Play_friends_button);
        chatroom = (TextView) findViewById(R.id.ChatRoom);
    }

}
