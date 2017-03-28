package com.mad.chitchat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                    SubscribeAllActivity.SubAlllist.get(object.getChannelId()).setFlagButton(false);
                    SharedPreference sp = new SharedPreference();
                    String token = sp.loadToken(mContext);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("channel_id", String.valueOf(object.getChannelId()))
                            .build();

                    Request request = new Request.Builder()
                            .url("http://52.90.79.130:8080/Groups/api/subscribe/channel")
                            .addHeader("Authorization", "BEARER "+token)
                            .header("Content-Type","application/x-www-form-urlencoded")
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            // Log.d("demo", response.body().string());
                            String jsonString = response.body().string();
                            try {
                                JSONObject root = new JSONObject(jsonString);
                                String status = root.getString("status");
                                if (status.equals("0")) {
                                    //Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                                    Log.d("demo","Channel not subscribed");
                                }else{
                                    Log.d("demo","Channel subscribed");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });




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
