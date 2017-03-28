package com.mad.chitchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by neha5 on 27-03-2017.
 */

public class GetMessagesUtil {
    static public class MessageJSONParser {
        static ArrayList<Message> parseMessages(String in) {

            ArrayList<Message> list = new ArrayList<Message>();
            try {
                JSONObject root = new JSONObject(in);

                JSONArray array = root.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Message msg = new Message();
                    msg.setMessageId(Integer.parseInt(obj.getString("message_id")));
                    msg.setChannelId(Integer.parseInt(obj.getString("channel_id")));
                    msg.setTime(Long.parseLong(obj.getString("msg_time")));
                    msg.setText(obj.getString("messages_text"));
                    JSONObject innerObj = obj.getJSONObject("user");
                    msg.setUserEmail(innerObj.getString("email"));
                    msg.setUserFname(innerObj.getString("fname"));
                    msg.setUserLname(innerObj.getString("lname"));
                    list.add(msg);
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }


            return list;
        }
    }
}
