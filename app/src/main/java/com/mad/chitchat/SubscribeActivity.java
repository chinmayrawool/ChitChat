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

import java.util.ArrayList;

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
        ListView listView = (ListView) findViewById(R.id.listViewSub);
        ListAdapter adapter = new ListAdapter(SubscribeActivity.this,R.layout.row_layout,list);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);


        findViewById(R.id.btn_add_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscribeActivity.this,SubscribeAllActivity.class);
                startActivity(intent);
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
