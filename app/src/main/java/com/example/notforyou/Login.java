package com.example.notforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class Login extends AppCompatActivity {

    private EditText Username,password;
    private Button submitButton;
    private String username;
    private Socket mSocket;
    private String username1;
    private TextView AllreadyUsered;
    private String pass;
    {
        try {
            mSocket = IO.socket("https://dry-island-87084.herokuapp.com");
        } catch (URISyntaxException e) {}
    }

    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (EditText)findViewById(R.id.login_user_name);
        password = (EditText)findViewById(R.id.login_password);
        submitButton = (Button)findViewById(R.id.login_Button);
        AllreadyUsered=(TextView)findViewById(R.id.Already_have_user);

        AllreadyUsered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Regisintent = new Intent(Login.this,RegisterActivity.class);
                startActivity(Regisintent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username1 = Username.getText().toString();
                pass = password.getText().toString();
                //Toast.makeText(Login.this,pass+"h"+username1,Toast.LENGTH_SHORT).show();

                if(!TextUtils.isEmpty(username1) && !TextUtils.isEmpty(pass))
                {

                    progressDoalog = new ProgressDialog(Login.this);
                    progressDoalog.setMessage("verifiing");
                    progressDoalog.setTitle("User Login");
                    progressDoalog.setCanceledOnTouchOutside(true);
                    progressDoalog.show();

                    mSocket.connect();
                    JSONObject info =new JSONObject();
                    try{
                        info.put("username",""+username1);
                        info.put("password",""+pass);
                        mSocket.emit("connect user",info);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mSocket.on("failed",onFailed);

                    mSocket.on("connect_user",onConnect);

                    Toast.makeText(Login.this,""+username,Toast.LENGTH_SHORT).show();


                }
            }
        });

    }
    Emitter.Listener onFailed = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;
                    progressDoalog.dismiss();
                    if(length == 0){
                        return;
                    }
                    Toast.makeText(Login.this,"Error: "+args[0].toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    };


    Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if(length == 0){
                        return;
                    }

                    UserDatabase db =new UserDatabase(Login.this);
                    long id =db.addUser(username1,pass);
                    progressDoalog.dismiss();
                        //Toast.makeText(Login.this,""+username,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        intent.putExtra("username",username1);
                        startActivity(intent);
                    progressDoalog.setMessage("user logging");
                    username =args[0].toString();
                    try {
                        JSONObject object = new JSONObject(username);
                        username = object.getString("username");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    };

}
