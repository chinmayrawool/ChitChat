package com.mad.chitchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SubscribeAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSub);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.chit_chat);


    }
}
