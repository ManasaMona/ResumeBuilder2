package com.example.manasaa.resumebuilder.Activity;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.Project;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.R;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderResumeProjects;
import com.google.android.gms.common.data.DataBufferUtils;

import java.util.ArrayList;

public class ProjectsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ProjectsActivity.class.getSimpleName();
    DatabaseHelper mDatabase;
    private static int USERID;
    private FloatingActionButton projects_fab;
    private static EditText projectName_editTxt,projectRole_editTxt,projectSummary_editTxt;
    private static Dialog dialog;
    ViewHolderResumeProjects adapter_projects;
    ListView  mProjectsListView;
    ArrayList<Project> mArrayOfprojects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called onCREATE()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        USERID = getIntent().getIntExtra("USER_ID",0);
        Log.d(TAG,USERID + " user id called ");

        projects_fab = (FloatingActionButton) findViewById(R.id.addFabProjects);
        projects_fab.setOnClickListener(this);

        mDatabase =  new DatabaseHelper(this);

        mArrayOfprojects = new ArrayList<Project>();
        int nuumPros = mDatabase.numberOfProfiles();
        Log.d(TAG, nuumPros+ " called coount pros");
        mArrayOfprojects = mDatabase.getProjectsByUserId(USERID);

        mProjectsListView = (ListView) findViewById(R.id.projects_listViewActivity);
        ViewHolderResumeProjects adapter_projects = new ViewHolderResumeProjects(this,mArrayOfprojects);
        mProjectsListView .setAdapter(adapter_projects);
    }

    @Override
    public void onClick(View v) {
        int id_view = v.getId();
        switch (id_view){
            case R.id.addFabProjects:
                                        showDailog();
                                        break;
            case R.id.pro_save_button:

                                        Log.d(TAG,"called project save button");
                                         String project_name,project_role,project_summary;
                                        project_name=projectName_editTxt.getText().toString();
                                        project_role = projectRole_editTxt.getText().toString();
                                        project_summary = projectSummary_editTxt.getText().toString();
                                        Log.d(TAG, project_name+project_role+project_summary+ " called from dialog");
                                        if(project_name.length()!=0 && project_role.length()!=0 && project_summary.length()!=0) {
                                            saveToDatabase(USERID, project_name, project_role, project_summary);
                                        }



                                        dialog.dismiss();
                                        break;
        }
    }

    private void saveToDatabase(int userid, String project_name, String project_role, String project_summary) {
        Log.d(TAG,"called save to db");
        Log.d(TAG, userid+"jj"+project_name+project_role+project_summary+ " called from dialog");
        Project pro = new Project(userid,project_name,project_role,project_summary);
        mDatabase.insertIntoUserProjectsTable(pro);
    }

    private void showDailog() {
        Button projectSave;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.project_input_dailog);
        projectName_editTxt = (EditText) dialog.findViewById( R.id.pro_title );
        projectRole_editTxt = (EditText) dialog.findViewById( R.id.pro_role );
        projectSummary_editTxt = (EditText) dialog.findViewById( R.id.pro_summary );
        projectSave = (Button) dialog.findViewById( R.id.pro_save_button );

        projectSave.setOnClickListener(this);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
