package com.example.leebeomwoo.viewbody_final.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leebeomwoo.viewbody_final.Fragment.StretchingFragment;
import com.example.leebeomwoo.viewbody_final.Item.LicenseItem;
import com.example.leebeomwoo.viewbody_final.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lee on 2017-07-03.
 */

public class LicenseListAdapter extends ArrayAdapter {
    private final Activity activity;
    private final List list;

    public LicenseListAdapter(Activity context, @LayoutRes int resource, ArrayList<LicenseItem> objects) {
        super(context, resource,  objects);
        this.activity = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        LicenseViewHolder view;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.license, null);

            // Hold the view objects in an object, that way the don't need to be "re-  finded"
            view = new LicenseViewHolder();
            view.title= (TextView) rowView.findViewById(R.id.titleTxt);
            view.source= (TextView) rowView.findViewById(R.id.sourceTxt);

            rowView.setTag(view);
        } else {
            view = (LicenseViewHolder) rowView.getTag();
        }

        /** Set data to your Views. */
        LicenseItem item = (LicenseItem) list.get(position);
        view.title.setText(item.getTitle());
        view.source.setText(item.getSource());

        return rowView;
    }

    private static class LicenseViewHolder{
        TextView title;
        TextView source;
    }
}
