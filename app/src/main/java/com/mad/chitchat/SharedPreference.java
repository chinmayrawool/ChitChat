package com.mad.chitchat;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SharedPreference {
    public static final String PREFS_NAME = "CHIT_CHAT_APP";
    public static final String TOKEN = "Token";
    public SharedPreference() {
        super();
    }
    public void storeToken(Context context, String token) {
    // used for store arrayList in json format
        SharedPreferences settings;
        Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(TOKEN,token);
        editor.commit();
    }
    public String loadToken(Context context) {
    // used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        String token;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(TOKEN)) {
             token = settings.getString(TOKEN, null);
        } else
            return null;
        return  token;
    }
    public void addToken(Context context,String token ) {
       storeToken(context,token);
    }
    public void removeToken(Context context,  String token) {
      storeToken(context,"notoken");
    }
}