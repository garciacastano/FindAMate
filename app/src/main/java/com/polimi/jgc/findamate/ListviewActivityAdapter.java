package com.polimi.jgc.findamate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JGC on 17/02/2016.
 */
public class ListviewActivityAdapter extends BaseAdapter {

    private static ArrayList<ActivityItem> listActivity;
    private LayoutInflater mInflater;

    public ListviewActivityAdapter(Context photosFragment, ArrayList<ActivityItem> results) {
        listActivity = results;
        mInflater = LayoutInflater.from(photosFragment);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listActivity.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return listActivity.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_item, null);
            holder = new ViewHolder();
            holder.txtname = (TextView) convertView.findViewById(R.id.lv_activity_item_name);
            holder.txtphone = (TextView) convertView.findViewById(R.id.lv_activity_item_phone);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtname.setText(listActivity.get(position).GetName());
        holder.txtphone.setText(listActivity.get(position).GetPhone());

        return convertView;
    }

    static class ViewHolder {
        TextView txtname, txtphone;
    }

}
