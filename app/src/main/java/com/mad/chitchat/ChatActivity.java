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

        LinearLayout l1 = (LinearLayout) findViewById(R.id.linearChat);
        for(int i=0; i<messages.size();i++){
            if(messages.get(i).getUserEmail()==userEmail){
                LinearLayout linear = new LinearLayout(this);
                LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.RIGHT;
                linear.setLayoutParams(params);
                TextView tv = new TextView(this);
                tv.setText(messages.get(i).getUserFname()+" "+messages.get(i).getUserLname());
                TextView tv1 = new TextView(this);
                tv1.setText(messages.get(i).getText());
                Long time = messages.get(i).getTime();
                String timeFormat = new PrettyTime(new Locale("")).format(new Date(time));
                TextView tv2 = new TextView(this);
                tv2.setText("Posted on " + timeFormat);
                linear.addView(tv);
                linear.addView(tv1);
                linear.addView(tv2);
                l1.addView(linear);
            }else{
                LinearLayout linear = new LinearLayout(this);
                LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.LEFT;
                linear.setLayoutParams(params);
                TextView tv = new TextView(this);
                tv.setText(messages.get(i).getUserFname()+" "+messages.get(i).getUserLname());
                TextView tv1 = new TextView(this);
                tv1.setText(messages.get(i).getText());
                Long time = messages.get(i).getTime();
                String timeFormat = new PrettyTime(new Locale("")).format(new Date(time));
                TextView tv2 = new TextView(this);
                tv2.setText("Posted on " + timeFormat);
                linear.addView(tv);
                linear.addView(tv1);
                linear.addView(tv2);
                l1.addView(linear);
            }
        }

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.et_message);
                String message = et.getText().toString();
                Long time = System.currentTimeMillis();
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
}
