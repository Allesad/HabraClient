package com.allesad.habraclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.allesad.habraclient.model.menu.MainMenuItem;

import java.util.List;

/**
 * Created by Allesad on 23.03.2014.
 */
public class MainMenuAdapter extends ArrayAdapter<MainMenuItem> {

    private LayoutInflater mInflater;
    private List<MainMenuItem> mItems;

    public MainMenuAdapter(Context context, List<MainMenuItem> items) {
        super(context, 0);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MainMenuItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainMenuItem item = getItem(position);

        if (convertView == null){
            convertView = mInflater.inflate(android.R.layout.simple_list_item_activated_1, null);
        }

        TextView title = (TextView) convertView.findViewById(android.R.id.text1);
        title.setText(item.getTitle());

        return convertView;
    }
}
