package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
* Login and Sign up activity
*
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] tokenR = {null};
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sign up code
            }
        });

        SharedPreference sp = new SharedPreference();
        String token = sp.loadToken(this);
        if (token != null) {
            if (!token.equals("notoken")) {
                //Intent i = new Intent(MainActivity.this, ChatActivity.class);
               // startActivity(i);
            }
        }
        else
        {
            final EditText emailLogin = (EditText) findViewById(R.id.login_email);
            final EditText passwordLogin = (EditText) findViewById(R.id.login_password);


            findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("email", emailLogin.getText().toString())
                            .add("password", passwordLogin.getText().toString())
                            .build();


                    Request request = new Request.Builder()
                            .url("http://52.90.79.130:8080/Groups/api/login")
                            .post(formBody)
                            .build();


                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(MainActivity.this, "Incorrect Email/Password ", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            StringBuilder sb = null;
                            // Log.d("demo", response.body().string());
                            String jsonString = response.body().string();
                            SignupObject signup = SignUpUtil.SignUpJSONParser.parseHours(jsonString);

                        }
                    });
                }
            });

        }
    }
}
