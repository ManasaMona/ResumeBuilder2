package com.example.manasaa.resumebuilder.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.Education;
import com.example.manasaa.resumebuilder.Model.Interest;
import com.example.manasaa.resumebuilder.Model.Project;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.Other.CircleTransform;
import com.example.manasaa.resumebuilder.Other.SessionManager;
import com.example.manasaa.resumebuilder.R;
import com.example.manasaa.resumebuilder.Utility.ListUtils;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderInterests;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderResumeEducation;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderResumeHeader;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderResumeProjects;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderSummary;

import java.util.ArrayList;

import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_EMAIL;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_NAME;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_PROFILEURL;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_USERID;
import static com.example.manasaa.resumebuilder.Other.SessionManager.PREF_NAME;

public class ResumeMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    public static final String TAG = ResumeMainPage.class.getSimpleName();
    private static String user_name,user_profileImageUrl,user_emailId;
    private static int USER_ID;
    private ImageView mProjectsAdd, mEducationAdd, mSummaryAdd, mInterestsAdd;
    private Button mHeaderAdd;
    private LinearLayout mSummaryLinearLayout,mProjectsLinearLayout,mInterestsLinearLayout,mEducationLinearLayout;
    DatabaseHelper mDatabase;
    ListView projectsListView,headerListview,educationListView,InterestsListView,mSummaryListView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG," called OnCreate ");

        doesUserExist();//Checks userExsits or not

        setContentView(R.layout.activity_resume_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //SharedPreferences
        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String user_emailId = shared.getString(KEY_EMAIL,"");
        Log.d(TAG,"called uuser_emaild " + user_emailId);

        // DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        toggle.syncState();

        displayNavigationDrawerLayout();//Navigation view DrawerLayout

        Log.d(TAG,"called useridddd " + USER_ID);


    }

    private void displayNavigationDrawerLayout() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navigationHeader = navigationView.getHeaderView(0);//For header in navigationView
        TextView userName = (TextView) navigationHeader.findViewById(R.id.user_name);
        TextView userEmail = (TextView) navigationHeader.findViewById(R.id.user_emailID);
        ImageView user_profile_image = (ImageView) navigationHeader.findViewById(R.id.user_imageView);

        userName.setText(user_name);
        userEmail.setText(user_emailId);
        Glide.with(this).load(user_profileImageUrl).crossFade().thumbnail(0.2f).bitmapTransform(new CircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.ALL).into(user_profile_image);

    }

    private void displayResumeInterests() {
        mInterestsLinearLayout = (LinearLayout) findViewById(R.id.interestsLinearLayout);
        mInterestsLinearLayout.setOnClickListener(this);

        mInterestsAdd = (ImageView) findViewById(R.id.interests_addButton);
        mInterestsAdd.setOnClickListener(this);

        ArrayList<Interest> arrayOfInterestsDetails;//EducationData
        arrayOfInterestsDetails = mDatabase.getInterestsByUserId(USER_ID);

        InterestsListView = (ListView) findViewById( R.id.interestsListView);     //ListView for education
        ViewHolderInterests adapter_interests = new ViewHolderInterests(this,arrayOfInterestsDetails);
        InterestsListView.setAdapter(adapter_interests);
        ListUtils.setDynamicHeight(InterestsListView);
    }

    private void displayResumeSummary() {
        mSummaryLinearLayout = (LinearLayout) findViewById(R.id.summaryLinearLayout);
        mSummaryLinearLayout.setOnClickListener(this);

        mSummaryAdd = (ImageView) findViewById(R.id.summary_editButton);
        mSummaryAdd.setOnClickListener(this);

        ArrayList<UserDetails> arrayOfsummary =new ArrayList<UserDetails>();  ;//usesrData
        UserDetails obj = mDatabase.getUserDetailsByEmail(user_emailId);
        arrayOfsummary.add(obj);

        mSummaryListView = (ListView)findViewById(R.id.summary_listView);//For Listview for header section
        ViewHolderSummary adapter_summary = new ViewHolderSummary(this,  arrayOfsummary);// Create the adapter to convert the array to views// Attach the adapter to a ListView
        mSummaryListView.setAdapter(adapter_summary);
    }

    private void displayResumeEducation() {
        mEducationLinearLayout = (LinearLayout) findViewById(R.id.educationLinearLayout);
        mEducationLinearLayout.setOnClickListener(this);

        mEducationAdd = (ImageView) findViewById(R.id.education_addButton);
        mEducationAdd.setOnClickListener(this);

        ArrayList<Education> arrayOfEducationDetails;//EducationData
        arrayOfEducationDetails = mDatabase.getEducationDetailsByUserId(USER_ID);

        educationListView = (ListView) findViewById( R.id.educationListView);     //ListView for education
        ViewHolderResumeEducation adapter_education = new ViewHolderResumeEducation(this,arrayOfEducationDetails);
        educationListView.setAdapter(adapter_education);
        ListUtils.setDynamicHeight(educationListView);
    }

    private void displayResumeProjects() {
        mProjectsLinearLayout = (LinearLayout) findViewById(R.id.projectLinearLayout);
        mProjectsLinearLayout.setOnClickListener(this);

        mProjectsAdd = (ImageView) findViewById(R.id.projects_addButton);
        mProjectsAdd.setOnClickListener(this);

        ArrayList<Project> arrayOfProjects;//ProjectsData
        arrayOfProjects = mDatabase.getProjectsByUserId(USER_ID);

        projectsListView = (ListView) findViewById(R.id.ProjectslistView);//listview for projects
        ViewHolderResumeProjects adapter_projects = new ViewHolderResumeProjects(this,arrayOfProjects);
        projectsListView.setAdapter(adapter_projects);
        ListUtils.setDynamicHeight(projectsListView);
    }

    private void displayResumeHeader() {
        mHeaderAdd = (Button) findViewById(R.id.profileEditButton);
        mHeaderAdd.setOnClickListener(this);

        ArrayList<UserDetails> arrayOfUsers = new ArrayList<UserDetails>();//usesrData
        UserDetails obj = mDatabase.getUserDetailsByEmail(user_emailId);
        arrayOfUsers.add(obj);

        headerListview = (ListView)findViewById(R.id.profileHeaderListView);//For Listview for header section
        ViewHolderResumeHeader adapter_header = new ViewHolderResumeHeader(this, arrayOfUsers);// Create the adapter to convert the array to views
        ListView listView = (ListView) findViewById(R.id.profileHeaderListView);// Attach the adapter to a ListView
        listView.setAdapter(adapter_header);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.profileEditButton:
                Log.d(TAG," called userid in profile add button " + USER_ID);
                Intent intentToResumeHeader = new Intent(ResumeMainPage.this, HeaderActivity.class);
                intentToResumeHeader.putExtra("USER_ID", USER_ID);
                startActivity(intentToResumeHeader);
                break;
            case R.id.projectLinearLayout:
            case R.id.projects_addButton:
                Log.d(TAG," called userid in projects add button " + USER_ID);
                Intent intentToProjects=new Intent(ResumeMainPage.this, ProjectsActivity.class);
                intentToProjects.putExtra("USER_ID",USER_ID);
                startActivity(intentToProjects);
                break;
            case R.id.educationLinearLayout:
            case R.id.education_addButton :
                Intent intentToEducation = new Intent(ResumeMainPage.this, EducationActivity.class);
                intentToEducation.putExtra("USER_ID",USER_ID);
                startActivity(intentToEducation);
                break;
            case R.id.interestsLinearLayout:
            case R.id.interests_addButton :
                Intent intentToInterests = new Intent(ResumeMainPage.this, InterestsActivity.class);
                intentToInterests.putExtra("USER_ID",USER_ID);
                startActivity(intentToInterests);
                break;
            case R.id.summaryLinearLayout:
            case R.id.summary_editButton:
                Intent intentToSummary = new Intent(ResumeMainPage.this, SummaryActivity.class);
                intentToSummary.putExtra("USER_ID",USER_ID);
                startActivity(intentToSummary);
                break;
        }
    }

    private void doesUserExist() { Log.d(TAG,"called DoesUSerExcits{ ");
        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        user_name = shared.getString(KEY_NAME,"");
        user_emailId = shared.getString(KEY_EMAIL,"");
        user_profileImageUrl = shared.getString(KEY_PROFILEURL,"");
        Log.d(TAG,user_name + user_emailId+ user_profileImageUrl + "username email onStrt called ");
        int userID;
        DatabaseHelper database = new DatabaseHelper(this);
        int count= database.numberOfProfiles();
        Log.d(TAG,"called  coutn"+ count);
        if(count==0){
            Log.d(TAG,"called  if(cursor==null)");
            UserDetails userDetails = new UserDetails(user_name,user_emailId,user_profileImageUrl);
            database.insertIntoUserDetailsTable(userDetails);
             userID= database.getUserIdByEmail(user_emailId);
            Log.d(TAG,userID+" called UserID");
            shared.edit().putInt(KEY_USERID,userID);
            shared.edit().apply();
            shared.edit().commit();
            USER_ID =userID;
        }
        else{
            Log.d(TAG,"called  if(cursor!!!!=null)");
            userID = database.getUserIdByEmail(user_emailId);
            Log.d(TAG,userID+" called UserID");
            shared.edit().putInt(KEY_USERID,userID);
            shared.edit().apply();
            shared.edit().commit();
            USER_ID =userID;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d(TAG," called  onNavigationItemSelected(MenuItem item) ");
        int id = item.getItemId();
        if (id == R.id.nav_profile_main_page) {

        } else if (id == R.id.nav_about) {// Handle navigation view item clicks here.
            Log.d(TAG," called  R.id.nav_about) ");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_settings) {// Handle navigation view item clicks here.
            Log.d(TAG," called  R.id.nav_about) ");
            Intent intent = new Intent(this, AboutPreferenceActivity.class);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onPostCreate( Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState); // Sync the toggle state after onRestoreInstanceState has occurred.//        drawer.syncState();
    }
    @Override
    protected void onStart() {          Log.d(TAG,"called onStart()");
        mDatabase = new DatabaseHelper(getBaseContext());
        displayResumeHeader();

        displayResumeSummary();

        displayResumeProjects();

        displayResumeEducation();

        displayResumeInterests();
        mDatabase.close();
        super.onStart();
    }
    @Override
    public void onBackPressed()
    {
       drawerLayout.closeDrawers();
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onStop() {           Log.d(TAG,"called onStop");
        super.onStop();
    }
    @Override
    protected void onPause() {          Log.d(TAG,"called onPause()");
        super.onPause();
    }
}
