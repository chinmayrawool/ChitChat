package com.mad.chitchat;

/**
 * Created by Chinmay Rawool on 3/27/2017.
 */

public class Message {
    int messageId, channelId;
    String userEmail, userFname,userLname,text;
    long time;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", channelId=" + channelId +
                ", userEmail='" + userEmail + '\'' +
                ", userFname='" + userFname + '\'' +
                ", userLname='" + userLname + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
