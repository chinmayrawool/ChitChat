package com.mad.chitchat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by Chinmay Rawool on 3/20/2017.
 */

public class ListAdapter extends ArrayAdapter<ChannelClass> {
    Context mContext;
    int mResource;
    List<ChannelClass> mData;
    ChannelClass object;
    ListView listView;
    ListAdapter listAdapter;
    public ListAdapter(Context context, int resource, List<ChannelClass> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource= resource;
        this.mData = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        object = mData.get(position);
        TextView tv_title = (TextView) convertView.findViewById(R.id.row_title);
        tv_title.setText(object.getChannelName()+"");
        final Button btn_view = (Button) convertView.findViewById(R.id.row_btn_view);
        if(object.isFlagButton()){
            btn_view.setText("Join");
        }else{
            btn_view.setText("View");
        }

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(object.isFlagButton()){
                    btn_view.setText("View");
                    object.setFlagButton(false);


                }else{
                    Intent intent = new Intent(mContext,ChatActivity.class);
                    intent.putExtra("Channel id",object.getChannelId());
                    intent.putExtra("Email",object.getEmail());
                    mContext.startActivity(intent);
                }
            }
        });
        convertView.setClickable(true);
        convertView.setLongClickable(true);
        return convertView;

    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}
