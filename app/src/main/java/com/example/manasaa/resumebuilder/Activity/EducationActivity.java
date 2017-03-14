package com.example.manasaa.resumebuilder.Activity;

import android.app.Dialog;
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
import android.widget.Toast;
import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.Education;
import com.example.manasaa.resumebuilder.R;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderResumeEducation;
import java.util.ArrayList;

public class EducationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = EducationActivity.class.getSimpleName();
    private DatabaseHelper mDatabase;
    private static int USERID;
    private FloatingActionButton education_fab;
    private static EditText eduInstituteName_editTxt,eduCourseName_editTxt,eduYear_editTxt;
    private static Dialog dialog;
    ViewHolderResumeEducation adapter_education;
    ListView  mEducationDetailsListView;
    ArrayList<Education> mArrayOfEducationDetails = new ArrayList<Education>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called onCREATE()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        USERID = getIntent().getIntExtra("USER_ID",0);
        Log.d(TAG,USERID + " user id called ");

        education_fab = (FloatingActionButton) findViewById(R.id.addFabEducation);
        education_fab.setOnClickListener(this);


    }

    private void settingDataInAdapter() {            Log.d(TAG,"called settingDataInAdapter() ");
        mEducationDetailsListView = (ListView) findViewById(R.id.education_listViewActivity);
        adapter_education = new ViewHolderResumeEducation(this,mArrayOfEducationDetails);
        mEducationDetailsListView .setAdapter(adapter_education);
        adapter_education.notifyDataSetChanged();
    }

    private void getEducationListFromDatabase() {     Log.d(TAG,"getEducationListFromDatabase() ");

        int numEdu = mDatabase.numberOfEducationDetails();Log.d(TAG, numEdu+ " called coount pros");
        mArrayOfEducationDetails = mDatabase.getEducationDetailsByUserId(USERID);

    }

    @Override
    public void onClick(View v) {
        int id_view = v.getId();
        switch (id_view){
            case R.id.addFabEducation:
                showDailog();
                break;
            case R.id.edu_save_button:
                Log.d(TAG,"called project save button");
                String name,course,year;
                name=eduInstituteName_editTxt.getText().toString();
                course= eduCourseName_editTxt.getText().toString();
                year = eduYear_editTxt.getText().toString();
                Log.d(TAG, name+course+year+ " called from dialog");
                if(name.length()!=0 && course.length()!=0 && year.length()!=0) {
                    saveToDatabase(USERID, name, course, year);
                    dialog.dismiss();
                    onResume();
                }
                else{
                    Toast.makeText(this, "Some fields are empty ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void saveToDatabase(int userid, String name, String role, String year) {
        Log.d(TAG,"called save to db");
        Education edu = new Education(userid,name, role,year);
        mDatabase.insertIntoUserEducationTable(edu);
    }

    private void showDailog() {
        Button educationSave;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.education_input_dailog);
        eduInstituteName_editTxt = (EditText) dialog.findViewById( R.id.edu_institute );
        eduCourseName_editTxt = (EditText) dialog.findViewById( R.id.edu_course);
        eduYear_editTxt = (EditText) dialog.findViewById( R.id.edu_year );
        educationSave = (Button) dialog.findViewById( R.id.edu_save_button );
        educationSave.setOnClickListener(this);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onResume() { Log.d(TAG,"called onResume");
        mDatabase =  new DatabaseHelper(this);
        getEducationListFromDatabase();
        settingDataInAdapter();
        adapter_education.notifyDataSetChanged();
        mDatabase.close();
        super.onResume();
    }

    @Override
    protected void onPause() { Log.d(TAG,"called onPause() ");
        super.onPause();
    }

    @Override
    protected void onStart() {  Log.d(TAG,"called onStart() ");
        super.onStart();
    }
}
