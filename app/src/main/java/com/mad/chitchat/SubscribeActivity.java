package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubscribeActivity extends AppCompatActivity {
    ArrayList<ChannelClass> list;//Subscribed channel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSub);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.chit_chat);
        /*ArrayList<ChannelClass> list2 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).isFlagButton()){

            }
        }*/

        SharedPreference sp = new SharedPreference();
        String token = sp.loadToken(this);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api/get/subscriptions")
                .addHeader("Authorization", "BEARER "+token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SubscribeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SubscribeActivity.this, "failed", Toast.LENGTH_SHORT).show();
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
                        SubscribeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                Toast.makeText(SubscribeActivity.this, "Error retrieving data for user", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }else{
                        list = SubscribeAllUtil.SubscribeJSONParser.parseChannels(jsonString);

                        SubscribeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Handle UI here
                                ListView listView = (ListView) findViewById(R.id.listViewSub);
                                ListAdapter adapter = new ListAdapter(SubscribeActivity.this,R.layout.row_layout,list);
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






        findViewById(R.id.btn_add_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscribeActivity.this,SubscribeAllActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu_1, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {

        }
        return true;
    }
}
