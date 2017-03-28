package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
    ArrayList<Message> messages;
    String token, id, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final EditText et = (EditText) findViewById(R.id.et1);
        messages = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.chit_chat);

        id = String.valueOf(getIntent().getExtras().getString("Channel id"));
        email = getIntent().getExtras().getString("Email");

        SharedPreference sp = new SharedPreference();
        token = sp.loadToken(this);
        //call function
        getMessages();


        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = et.getText().toString();
                Long time = System.currentTimeMillis();

                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("msg_text", message.trim())
                        .add("msg_time", String.valueOf(time))
                        .add("channel_id", id)
                        .build();

                Request request = new Request.Builder()
                        .url("http://52.90.79.130:8080/Groups/api/post/message")
                        .addHeader("Authorization", "BEARER "+token)
                        .header("Content-Type","application/x-www-form-urlencoded")
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ChatActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChatActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }});
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        // Log.d("demo", response.body().string());
                        String jsonString = response.body().string();
                        try {
                            JSONObject root = new JSONObject(jsonString);
                            String status = root.getString("status");
                            if (status.equals("0")) {
                                ChatActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Handle UI here
                                        Toast.makeText(ChatActivity.this, "User not subscribed to channel", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }else{
                                getMessages();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


                //get token from sp
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu_chat, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {

        }else if(item.getItemId() == R.id.action_refresh){
            finish();
            Intent intent = new Intent(ChatActivity.this,ChatActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void getMessages(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api/get/messages?channel_id="+id)
                .addHeader("Authorization", "BEARER "+token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatActivity.this, "failed", Toast.LENGTH_SHORT).show();
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
                    if (status.equals("0")) {
                        ChatActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                Toast.makeText(ChatActivity.this, "Error retrieving data for user", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }else{
                        messages = null;
                        messages = GetMessagesUtil.MessageJSONParser.parseMessages(jsonString);

                        ChatActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                LinearLayout l1 = (LinearLayout) findViewById(R.id.linearChat);
                                l1.removeAllViews();
                                for(int i=0; i<messages.size();i++){
                                    if(messages.get(i).getUserEmail().equals(email)){
                                        LinearLayout linear = new LinearLayout(ChatActivity.this);
                                        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        params.gravity = Gravity.RIGHT;
                                        linear.setLayoutParams(params);
                                        TextView tv = new TextView(ChatActivity.this);
                                        tv.setText(messages.get(i).getUserFname()+" "+messages.get(i).getUserLname());
                                        TextView tv1 = new TextView(ChatActivity.this);
                                        tv1.setText(messages.get(i).getText());
                                        Long time = messages.get(i).getTime();
                                        String timeFormat = new PrettyTime(new Locale("")).format(new Date(time));
                                        TextView tv2 = new TextView(ChatActivity.this);
                                        tv2.setText("Posted on " + timeFormat);
                                        linear.addView(tv);
                                        linear.addView(tv1);
                                        linear.addView(tv2);
                                        l1.addView(linear);
                                    }else{
                                        LinearLayout linear = new LinearLayout(ChatActivity.this);
                                        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        params.gravity = Gravity.LEFT;
                                        linear.setLayoutParams(params);
                                        TextView tv = new TextView(ChatActivity.this);
                                        tv.setText(messages.get(i).getUserFname()+" "+messages.get(i).getUserLname());
                                        TextView tv1 = new TextView(ChatActivity.this);
                                        tv1.setText(messages.get(i).getText());
                                        Long time = messages.get(i).getTime();
                                        String timeFormat = new PrettyTime(new Locale("")).format(new Date(time));
                                        TextView tv2 = new TextView(ChatActivity.this);
                                        tv2.setText("Posted on " + timeFormat);
                                        linear.addView(tv);
                                        linear.addView(tv1);
                                        linear.addView(tv2);
                                        l1.addView(linear);
                                    }
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
