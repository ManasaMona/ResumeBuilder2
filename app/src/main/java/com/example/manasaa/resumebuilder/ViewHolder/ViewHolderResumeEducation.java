package com.example.manasaa.resumebuilder.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.manasaa.resumebuilder.Model.Education;
import com.example.manasaa.resumebuilder.R;

import java.util.ArrayList;

/**
 * Created by manasa on 05-03-2017.
 */

public class ViewHolderResumeEducation extends ArrayAdapter<Education> {
    TextView institute_nameTxtView, course_nameTxView , year_txtView;
    public ViewHolderResumeEducation(Context context, ArrayList<Education> educations) {
        super(context, 0, educations);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Education education = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.resume_education, parent, false);
        }
        // Lookup view for data population
        institute_nameTxtView =(TextView) convertView.findViewById(R.id.education_course_name);
        course_nameTxView =(TextView) convertView.findViewById(R.id.education_institute_name);
        year_txtView = (TextView) convertView.findViewById(R.id.education_years);
        // Populate the data into the template view using the data object
        institute_nameTxtView.setText("Institute : " +education.institute_name);
        course_nameTxView.setText("Course : "+education.course_name);
        year_txtView.setText("Year : "+education.year);
      // Return the completed view to render on screen
        return convertView;

    }

}
