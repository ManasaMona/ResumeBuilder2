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
 * Created by manasa.a on 02-03-2017.
 */

public class ViewHolderResumeHeader extends ArrayAdapter<UserDetails> {
    private static String TAG = ViewHolderResumeHeader.class.getSimpleName();
    public TextView userNameTxtView,userEmailTxtView;
    public ImageView userProfileURLImageView;
    public TextView userExperienceEditTxt;

    public ViewHolderResumeHeader(Context context, ArrayList<UserDetails> usersDetails) {
            super(context, 0, usersDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserDetails user = getItem(position);// Get the data item for this position
        if (convertView == null) {  // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.resume_header, parent, false);
        }
        userEmailTxtView =(TextView) convertView.findViewById(R.id.user_emailID_txtView); // Lookup view for data population
        userNameTxtView =(TextView) convertView.findViewById(R.id.user_name_txtView);
        userExperienceEditTxt = (TextView) convertView.findViewById(R.id.experience_editText);
        userProfileURLImageView = (ImageView) convertView.findViewById(R.id.userImage_imageView);
        // Populate the data into the template view using the data object
        userNameTxtView.setText(user.getUserName());
        userEmailTxtView.setText(user.getUserEmail());
        if(user.getUserExperience()!=0) {
            userExperienceEditTxt.setText(user.getUserExperience() + " years");
        }
        Glide.with(getContext()).
                load(user.getUserProfileLink())
                .crossFade().thumbnail(0.2f)
                .bitmapTransform(new CircleTransform(getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(userProfileURLImageView);

        // Return the completed view to render on screen
        return convertView;
    }



}
