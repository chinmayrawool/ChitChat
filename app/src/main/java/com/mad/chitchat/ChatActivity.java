package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    ArrayList<Message> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messages = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.chit_chat);


        for(int i=0; i<messages.size();i++){
            if(messages.get(i).getUserEmail()==userEmail){
                LinearLayout linear = new LinearLayout(this);
                TextView tv = new TextView(this);
                tv.setText(messages.get(i).getUserFname()+" "+messages.get(i).getUserLname());
                TextView tv1 = new TextView(this);
                tv1.setText(messages.get(i).getText());
                Long time = System.currentTimeMillis();
                String timeFormat = new PrettyTime(new Locale("")).format(new Date(time)))
                TextView tv2 = new TextView(this);
                tv2.setText();
            }
        }


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
}
