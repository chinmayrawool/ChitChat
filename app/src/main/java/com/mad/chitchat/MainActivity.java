package com.mad.chitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
                Intent i = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(i);
            }
        }
        else
        {
            final EditText email = (EditText) findViewById(R.id.editTextEmailSignUp);
            final EditText password = (EditText) findViewById(R.id.editTextPasswordSignup);
        }
    }
}
