package com.example.manasaa.resumebuilder.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.manasaa.resumebuilder.Model.Education;
import com.example.manasaa.resumebuilder.Model.Interest;
import com.example.manasaa.resumebuilder.R;

import java.util.ArrayList;

/**
 * Created by manasa on 05-03-2017.
 */

public class ViewHolderInterests extends ArrayAdapter<Interest> {
    TextView interest_nameTxtView;

    public ViewHolderInterests(Context context, ArrayList<Interest> interests) {
        super(context, 0, interests);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Interest intrst = getItem(position);  // Get the data item for this position
        if (convertView == null) {   // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.resume_interest_list, parent, false);
        }
        interest_nameTxtView =(TextView) convertView.findViewById(R.id.interestTextView); // Lookup view for data population
        String interstName = "• "+intrst.getInterestName();
        interest_nameTxtView .setText(interstName);  // Populate the data into the template view using the data object

        return convertView;// Return the completed view to render on screen

    }

}
