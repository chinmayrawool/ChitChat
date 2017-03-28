package com.mad.chitchat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by neha5 on 27-03-2017.
 */

public class SubscribeAllUtil {

    static public class SubscribeJSONParser {
        static ArrayList<ChannelClass> parseChannels(String in) {
            ArrayList<ChannelClass> list = new ArrayList<ChannelClass>();
            try {
                JSONObject root = new JSONObject(in);
                //signupObject = SignupObject.createSignupObject(root);
                //Log.d("each hour", signupObject.toString());

                JSONArray array = root.getJSONArray("data");
                for(int i =0;i<array.length();i++){
                    ChannelClass channelClass = new ChannelClass();
                    JSONObject obj = array.getJSONObject(i);
                    channelClass.setEmail(obj.getString("email"));
                    JSONObject innerObj = obj.getJSONObject("channel");
                    channelClass.setChannelId(Integer.parseInt(innerObj.getString("channel_id")));
                    channelClass.setChannelName(innerObj.getString("channel_name"));
                    channelClass.setFlagButton(false);
                    list.add(channelClass);
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }


            return list;
        }


        static public SignupObject createSignupObject(JSONObject js) throws JSONException {
            SignupObject signup = new SignupObject();
            signup.setStatus(js.getString("status"));
            if(signup.getStatus().equals("1"))
            {        signup.setToken(js.getString("data"));
            }

            return signup;

        }

    }
}
