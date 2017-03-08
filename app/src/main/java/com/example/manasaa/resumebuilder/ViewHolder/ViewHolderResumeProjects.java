package com.example.manasaa.resumebuilder.ViewHolder;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.manasaa.resumebuilder.Model.Project;
import com.example.manasaa.resumebuilder.R;

import java.util.ArrayList;

/**
 * Created by manasa on 04-03-2017.
 */

public class ViewHolderResumeProjects extends ArrayAdapter<Project> {
    TextView project_name,project_role,project_summary;

    public ViewHolderResumeProjects(Context context, ArrayList<Project> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.resume_projects, parent, false);
        }
        // Lookup view for data population
        project_name =(TextView) convertView.findViewById(R.id.project_title);
        project_role =(TextView) convertView.findViewById(R.id.project_role);
        project_summary = (TextView) convertView.findViewById(R.id.project_summary);
        // Populate the data into the template view using the data object
        project_name.setText("Name : "+project.project_name);
        project_role.setText("Role : "+project.project_role);
        project_summary.setText("Summary :"+project.project_summary);
        // Return the completed view to render on screen
        return convertView;

    }
}
