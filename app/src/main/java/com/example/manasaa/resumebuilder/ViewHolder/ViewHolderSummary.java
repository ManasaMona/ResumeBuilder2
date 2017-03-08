package com.example.manasaa.resumebuilder.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.Other.CircleTransform;
import com.example.manasaa.resumebuilder.R;

import java.util.ArrayList;

/**
 * Created by manasa on 05-03-2017.
 */

public class ViewHolderSummary extends ArrayAdapter<UserDetails> {
    private static String TAG = ViewHolderSummary.class.getSimpleName();
    public TextView summaryTxtView;


    public  ViewHolderSummary(Context context, ArrayList<UserDetails> usersDetails) {
        super(context, 0, usersDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserDetails user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.resume_summary, parent, false);
        }
        // Lookup view for data population
        summaryTxtView =(TextView) convertView.findViewById(R.id.summarytxtView);

        // Populate the data into the template view using the data object
        if(user.getUserSummary()!=null) {
            summaryTxtView.setText(user.getUserSummary());
        }
        // Return the completed view to render on screen
        return convertView;
    }



}
