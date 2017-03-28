package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle(R.string.chit_chat);
        setSupportActionBar(toolbar);

        final String[] tokenR = {null};

        final EditText emailSignup = (EditText) findViewById(R.id.signup_email);
        final EditText passwordSignup = (EditText) findViewById(R.id.signup_password);
        final EditText fnameSignup = (EditText) findViewById(R.id.signup_fname);
        final EditText lnameSignup = (EditText) findViewById(R.id.signup_lname);

        final EditText emailLogin = (EditText) findViewById(R.id.login_email);
        final EditText passwordLogin = (EditText) findViewById(R.id.login_password);

        SharedPreference sp = new SharedPreference();
        String token = sp.loadToken(this);
        if (token != null) {
            if (!token.equals("notoken")) {
                Intent i = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(i);
            }
        }

                findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("email", emailSignup.getText().toString())
                                .add("password", passwordSignup.getText().toString())
                                .add("fname", fnameSignup.getText().toString())
                                .add("lname", lnameSignup.getText().toString())
                                .build();


                        Request request = new Request.Builder()
                                .url("http://52.90.79.130:8080/Groups/api/signUp")
                                .post(formBody)
                                .build();


                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                                    }});
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                StringBuilder sb = null;
                                // Log.d("demo", response.body().string());

                                String jsonString = response.body().string();
                                try {
                                    JSONObject root = new JSONObject(jsonString);
                                    String status = root.getString("status");


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SignupObject signup = SignUpUtil.SignUpJSONParser.parseHours(jsonString);
                                if (signup.getStatus().equals("0")) {
                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Handle UI here
                                            Toast.makeText(MainActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }else{
                                    tokenR[0] = signup.getToken();
                                    SharedPreference sp = new SharedPreference();
                                    sp.addToken(MainActivity.this, tokenR[0]);
                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Handle UI here
                                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(MainActivity.this, SubscribeActivity.class);
                                            startActivity(i);
                                            finish();

                                        }
                                    });
                                }
                            }
                        });
                    }
                });


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
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }});
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        // Log.d("demo", response.body().string());
                        String jsonString = response.body().string();
                        SignupObject signup = SignUpUtil.SignUpJSONParser.parseHours(jsonString);
                        if (signup.getStatus().equals("0")) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Handle UI here
                                    Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }else{
                            tokenR[0] = signup.getToken();
                            SharedPreference sp = new SharedPreference();
                            sp.addToken(MainActivity.this, tokenR[0]);


                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Handle UI here
                                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, SubscribeActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                            });
                        }
                    }
                });
            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
