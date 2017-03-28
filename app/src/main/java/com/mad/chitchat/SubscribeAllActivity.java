package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SubscribeAllActivity extends AppCompatActivity {
    ArrayList<ChannelClass> Sublist;//Subscribed channel
    ArrayList<ChannelClass> SubAlllist;//All Subscribed channel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSub);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(R.string.chit_chat);
        final boolean subscribeAll =true;
        Sublist = new ArrayList<ChannelClass>();

        if(getIntent().getExtras()!=null){
            Sublist = (ArrayList<ChannelClass>) getIntent().getExtras().getSerializable("Subscribed list");
        }
        SharedPreference sp = new SharedPreference();
        String token = sp.loadToken(this);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api/get/channels")
                .addHeader("Authorization", "BEARER "+token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SubscribeAllActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SubscribeAllActivity.this, "failed", Toast.LENGTH_SHORT).show();
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
                        SubscribeAllActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                Toast.makeText(SubscribeAllActivity.this, "Error retrieving data for user", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }else{

                        SubAlllist = new ArrayList<ChannelClass>();
                        SubAlllist = SubscribeAllUtil.SubscribeJSONParser.parseChannels(jsonString,subscribeAll);

                        for(int i=0;i<Sublist.size();i++){
                            ChannelClass channel = Sublist.get(i);
                            SubAlllist.set(channel.channelId - 1, channel);
                        }
                        SubscribeAllActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                ListView listView = (ListView) findViewById(R.id.listViewSubAll);
                                ListAdapter adapter = new ListAdapter(SubscribeAllActivity.this,R.layout.row_layout,SubAlllist);
                                listView.setAdapter(adapter);
                                adapter.setNotifyOnChange(true);

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




        findViewById(R.id.btn_Done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscribeAllActivity.this,SubscribeActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
