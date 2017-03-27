package com.mad.chitchat;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neha5 on 27-03-2017.
 */
public class SignupObject {
    String token, status ;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    static public SignupObject createSignupObject(JSONObject js) throws JSONException {
        SignupObject signup = new SignupObject();
        signup.setStatus(js.getString("status"));
        if(signup.getStatus().equals("ok"))
        {        signup.setToken(js.getString("token"));}

        return signup;

    }

    @Override
    public String toString() {
        return "SignupObject{" +
                "token='" + token + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

