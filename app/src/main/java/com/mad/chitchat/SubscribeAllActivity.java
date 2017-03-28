package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SubscribeAllActivity extends AppCompatActivity {
    ArrayList<ChannelClass> Sublist;//Subscribed channel
    ArrayList<ChannelClass> SubAlllist;//All Subscribed channel
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSub);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.chit_chat);

        ListView listView = (ListView) findViewById(R.id.listViewSub);
        ListAdapter adapter = new ListAdapter(SubscribeAllActivity.this,R.layout.row_layout,SubAlllist);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);


        findViewById(R.id.btn_Done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscribeAllActivity.this,SubscribeAllActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
