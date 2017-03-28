package com.mad.chitchat;

import java.io.Serializable;

/**
 * Created by Chinmay Rawool on 3/27/2017.
 */

public class ChannelClass implements Serializable{
    int channelId;
    String channelName,email;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    boolean flagButton=false;//false= view and true=Join

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public boolean isFlagButton() {
        return flagButton;
    }

    public void setFlagButton(boolean flagButton) {
        this.flagButton = flagButton;
    }

    @Override
    public String toString() {
        return getChannelName()+" "+isFlagButton();
    }
}
