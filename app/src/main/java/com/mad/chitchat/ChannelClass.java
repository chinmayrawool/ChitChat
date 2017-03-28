package com.mad.chitchat;

/**
 * Created by Chinmay Rawool on 3/27/2017.
 */

public class ChannelClass {
    int channelId;
    String channelName;
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
