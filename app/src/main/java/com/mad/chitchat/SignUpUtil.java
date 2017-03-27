package com.mad.chitchat;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neha5 on 27-03-2017.
 */

public class SignUpUtil {
    static public class SignUpJSONParser {
        static SignupObject parseHours(String in) {
            SignupObject signupObject = new SignupObject();
            try {
                JSONObject root = new JSONObject(in);
                signupObject = SignupObject.createSignupObject(root);
                Log.d("each hour", signupObject.toString());

            } catch (JSONException e) {
                signupObject = null;
                e.printStackTrace();
            }


            return signupObject;
        }

    }
}

