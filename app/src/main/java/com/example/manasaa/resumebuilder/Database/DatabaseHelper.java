package com.example.manasaa.resumebuilder.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.manasaa.resumebuilder.Model.Education;
import com.example.manasaa.resumebuilder.Model.Interest;
import com.example.manasaa.resumebuilder.Model.Project;
import com.example.manasaa.resumebuilder.Model.UserDetails;

import java.util.ArrayList;

/**
 * Created by manasa.a on 02-03-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG= "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;// Database Version
    private static final String DATABASE_NAME = "resumeManager";// Database Name
    private static final String TABLE_USER_DETAILS = "user_details";  // Table Names
    private static final String TABLE_USER_PROJECTS = "user_projects";
    private static final String TABLE_USER_EDUCATION = "user_education";
    private static final String TABLE_USER_INTERESTS = "user_interests";
//    private static final String TABLE_TODO_TAG = "todo_tags";

    ContentValues values;
    Context context;

    // Common column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_NAME = "user_name_column";
    private static final String COLUMN_USER_EMAILID = "user_email_column";
    private static final String COLUMN_USER_EXPERIENCE = "user_experience_column";
    private static final String COLUMN_USER_PROFILE_URL ="user_profile_url";
    private static final String COLUMN_USER_SUMMARY = "user_summary";
    //columnNames for projecttble
    private static final String COLUMN_PROJECT_ID = "id";
    private static final String COLUMN_USER_ID_PROJECT = "user_id";
    private static final String COLUMN_PROJECT_NAME = "project_name_column";
    private static final String COLUMN_PROJECT_ROLE= "project_role_column";
    private static final String COLUMN_PROJECT_SUMMARY= "project_summary_column";
    //columnNames for educationtble
    private static final String COLUMN_EDUCATION_ID = "edu_id";
    private static final String COLUMN_USER_ID_EDUCATION = "user_id";
    private static final String COLUMN_EDUCATION_INSTITUTE_NAME = "edu_name_column";
    private static final String COLUMN_EDUCATION_COURSE_NAME = "edu_course_column";
    private static final String COLUMN_EDUCATION_YEAR= "edu_year_column";
    //
    private static final String COLUMN_INTEREST_ID = "interest_id";
    private static final String COLUMN_USER_ID_INTEREST = "user_id";
    private static final String COLUMN_INTEREST_NAME = "interest_name_column";

    //Table create statement
    private static final String CREATE_TABLE_USER_DETAILS = "CREATE TABLE "
            + TABLE_USER_DETAILS +
            "(" + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_USER_NAME + " VARCHAR(25) ," +
            COLUMN_USER_EMAILID + " VARCHAR(45) UNIQUE," +
            COLUMN_USER_EXPERIENCE + " VARCHAR(50), " +
            COLUMN_USER_PROFILE_URL + " VARCHAR(100), " +
            COLUMN_USER_SUMMARY +" VARCHAR(300) " +
            ");";
    //TABLE CREATE STATEMENT FOR PROJECTS TABLE
    private static final String CREATE_TABLE_USER_PROJECTS = "CREATE TABLE "
            + TABLE_USER_PROJECTS +
            "(" + COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_PROJECT_NAME + " VARCHAR(30) ," +
            COLUMN_PROJECT_ROLE + " VARCHAR(25) ," +
            COLUMN_PROJECT_SUMMARY + " VARCHAR(100), " +
            COLUMN_USER_ID_PROJECT +" INTEGER ,"+
            "FOREIGN KEY(" + COLUMN_USER_ID_PROJECT +") REFERENCES "+TABLE_USER_DETAILS+"("+COLUMN_ID+")"+
            ");";
    //TABLE CREATE STATEMENT FOR EDUCATION TABLE
    private static final String CREATE_TABLE_USER_EDUCATION = "CREATE TABLE "
            + TABLE_USER_EDUCATION +
            "(" + COLUMN_EDUCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_EDUCATION_INSTITUTE_NAME + " VARCHAR(30) ," +
            COLUMN_EDUCATION_COURSE_NAME+ " VARCHAR(25) ," +
            COLUMN_EDUCATION_YEAR + " VARCHAR(100), " +
            COLUMN_USER_ID_EDUCATION +" INTEGER ,"+
            "FOREIGN KEY(" + COLUMN_USER_ID_EDUCATION +") REFERENCES "+TABLE_USER_DETAILS+"("+COLUMN_ID+")"+
            ");";

    //TABLE CREATE STATEMENT FOR EDUCATION TABLE
    private static final String CREATE_TABLE_USER_INTERESTS = "CREATE TABLE "
            + TABLE_USER_INTERESTS +
            "(" + COLUMN_INTEREST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_ID_INTEREST + " INTEGER ," +
            COLUMN_INTEREST_NAME+ " VARCHAR(25) ," +
            "FOREIGN KEY(" + COLUMN_USER_ID_INTEREST +") REFERENCES "+TABLE_USER_DETAILS+"("+COLUMN_ID+")"+
            ");";

    public DatabaseHelper(Context context) { //Constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        if (!db.isReadOnly()) {
//            // Enable foreign key constraints
//            db.execSQL("PRAGMA foreign_keys=ON;");
//        }
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {// creating required tables
        db.execSQL(CREATE_TABLE_USER_DETAILS );
        db.execSQL(CREATE_TABLE_USER_PROJECTS);
        db.execSQL(CREATE_TABLE_USER_EDUCATION);
        db.execSQL(CREATE_TABLE_USER_INTERESTS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_EDUCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INTERESTS);
        onCreate(db); // create new tables
    }

    public void insertIntoUserDetailsTable(UserDetails userDetails){ // Insert into UserDetails Table
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(COLUMN_USER_NAME, userDetails.getUserName()); // Contact Name
        values.put(COLUMN_USER_EMAILID, userDetails.getUserEmail()); // Contact Phone
        values.put(COLUMN_USER_PROFILE_URL, userDetails.getUserProfileLink());
        values.put(COLUMN_USER_EXPERIENCE,userDetails.getUserExperience());
        db.insert(TABLE_USER_DETAILS, null, values);// Inserting Row
        db.close(); // Closing database connection
    }
    public void insertIntoUserEducationTable(Education eduDetails){// Insert into Education Table
        Log.d(TAG,"called in insertIntoUserProjectsTable");
       // Log.d(TAG,eduDetails.project_id+" called id");
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(COLUMN_EDUCATION_INSTITUTE_NAME,eduDetails.institute_name);
        values.put(COLUMN_EDUCATION_COURSE_NAME,eduDetails.course_name);
        values.put(COLUMN_EDUCATION_YEAR,eduDetails.year);
        values.put(COLUMN_USER_ID_EDUCATION,eduDetails.education_id);
        db.insert(TABLE_USER_EDUCATION,null,values);
        db.close();
    }
    public void insertIntoUserProjectsTable(Project projectDetails){// Insert into ProjectsTable
        Log.d(TAG,"called in insertIntoUserProjectsTable");
        Log.d(TAG,projectDetails.project_id+" called id");
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(COLUMN_USER_ID_PROJECT,projectDetails.project_id);
        values.put(COLUMN_PROJECT_NAME,projectDetails.project_name);
        values.put(COLUMN_PROJECT_ROLE,projectDetails.project_role);
        values.put(COLUMN_PROJECT_SUMMARY,projectDetails.project_summary);
        db.insert(TABLE_USER_PROJECTS,null,values);
        db.close();
    }
    public void insertIntoUserInterestsTable(Interest interest){// Insert into ProjectsTable
        Log.d(TAG,"called in insertIntoUserProjectsTable");
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(COLUMN_USER_ID_INTEREST,interest.getUser_id());
        values.put(COLUMN_INTEREST_NAME,interest.getInterestName());
        db.insert(TABLE_USER_INTERESTS,null,values);
        db.close();
    }

    public int getUserIdByEmail(String emailId) { // get row by emailID
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_DETAILS + " where " + COLUMN_USER_EMAILID + "='" + emailId+ "'" , null);
        int userID=0;
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                Log.d(TAG, "called (cursor.moveToFirst())");
                userID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                Log.d(TAG,"called id " + userID);
            }
            cursor.close();
        }
        return userID;
    }
    public UserDetails getUserDetailsByID(int ID){
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_DETAILS + " where " + COLUMN_ID + "='" + ID+ "'" , null);
        UserDetails usrDetails = new UserDetails();
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                Log.d(TAG, "called (cursor.moveToFirst())");
                usrDetails.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                usrDetails.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAILID)));
                usrDetails.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                usrDetails.setUserExperience(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_EXPERIENCE)));
                usrDetails.setUserProfileLink(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PROFILE_URL)));
                usrDetails.setUserSummary(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SUMMARY)));
               // Log.d(TAG,"called id " + userID);
            }
            cursor.close();
        }
        return usrDetails;
    };
    public UserDetails getUserDetailsByEmail(String emailId){
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_DETAILS + " where " + COLUMN_USER_EMAILID + "='" + emailId+ "'" , null);
        UserDetails usrDetails = new UserDetails();
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                Log.d(TAG, "called (cursor.moveToFirst())");
                usrDetails.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                usrDetails.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAILID)));
                usrDetails.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                usrDetails.setUserProfileLink(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PROFILE_URL)));
                usrDetails.setUserExperience(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_EXPERIENCE)));
                usrDetails.setUserSummary(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SUMMARY)));
                // Log.d(TAG,"called id " + userID);
            }
            cursor.close();
        }
        return usrDetails;
    };
    public ArrayList<Project> getProjectsByUserId(int userId){
        ArrayList<Project> projectsList =  new ArrayList<Project>();
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_PROJECTS + " where " + COLUMN_USER_ID_PROJECT + "='" + userId+ "'" , null);
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                do {
                    Log.d(TAG, "called (cursor.moveToFirst())");
                    Project pro = new Project();
                    pro.project_id = cursor.getInt(cursor.getColumnIndex(COLUMN_PROJECT_ID));
                    pro.project_name = cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_NAME));
                    pro.project_role = cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_ROLE));
                    pro.project_summary = cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_SUMMARY));
                    projectsList.add(pro);
                }while (cursor.moveToNext());
                // Log.d(TAG,"called id " + userID);
            }
            cursor.close();
        }
       return projectsList;
    }
    public ArrayList<Education> getEducationDetailsByUserId(int userId){
        ArrayList<Education> educationList =  new ArrayList<Education>();
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_EDUCATION + " where " + COLUMN_USER_ID_EDUCATION + "='" + userId+ "'" , null);
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                do {
                    Log.d(TAG, "called (cursor.moveToFirst())");
                    Education edu = new Education();
                    edu.education_id = cursor.getInt(cursor.getColumnIndex(COLUMN_EDUCATION_ID));
                    edu.institute_name = cursor.getString(cursor.getColumnIndex(COLUMN_EDUCATION_INSTITUTE_NAME));
                    edu.course_name = cursor.getString(cursor.getColumnIndex(COLUMN_EDUCATION_COURSE_NAME));
                    edu.year = cursor.getString(cursor.getColumnIndex(COLUMN_EDUCATION_YEAR));
                    educationList.add(edu);
                    // Log.d(TAG,"called id " + userID);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return educationList;
    }
    public ArrayList<Interest> getInterestsByUserId(int userId){
        ArrayList<Interest> interestsList =  new ArrayList<Interest>();
        SQLiteDatabase sqliteQueryBuilder = this.getReadableDatabase();
        Cursor cursor =  sqliteQueryBuilder.rawQuery("select * from " + TABLE_USER_INTERESTS+ " where " + COLUMN_USER_ID_INTEREST + "='" + userId+ "'" , null);
        if (cursor != null) {
            Log.d(TAG,"called cursor!null");
            if (cursor.moveToFirst()) {
                do {
                    Log.d(TAG, "called (cursor.moveToFirst())");
                    Interest intrst = new Interest();
                    intrst.setInterestName(cursor.getString(cursor.getColumnIndex(COLUMN_INTEREST_NAME))) ;
                    intrst.setUser_id( cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_INTEREST)));
                    interestsList.add(intrst);
                    // Log.d(TAG,"called id " + userID);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return interestsList;
    }
    public int numberOfProfiles(){
        String countQuery = "SELECT  * FROM " + TABLE_USER_DETAILS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;

    };
    public int numberOfProjects(){
        String countQuery = "SELECT  * FROM " + TABLE_USER_PROJECTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public int numberOfInterests(){
        String countQuery = "SELECT  * FROM " + TABLE_USER_INTERESTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public int numberOfEducationDetails(){
        String countQuery = "SELECT  * FROM " + TABLE_USER_EDUCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    };

    public void updateSummaryByUserID(int userid, String summary) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+ TABLE_USER_DETAILS+" SET "+ COLUMN_USER_SUMMARY + " = " + "'"+summary+"'" +" WHERE " + COLUMN_ID +" = "+ "'"+userid+"'";
        db.execSQL(strSQL);
    }


    public void updateExperinceByUserId(int experience, int userid) {
        Log.d(TAG,"called updateExperinceByUserId");
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+ TABLE_USER_DETAILS+" SET "+COLUMN_USER_EXPERIENCE+ " = " +experience+" WHERE " + COLUMN_ID +" = "+"'"+ userid+"'";
        db.execSQL(strSQL);
    }

    public void updateProfileURLByUserId(String profileURL, int userid) {
        Log.d(TAG,"called updateProfileURLByUserId(");
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+ TABLE_USER_DETAILS+" SET "+COLUMN_USER_PROFILE_URL+ " = " +'"'+profileURL+'"'+" WHERE " + COLUMN_ID +" = "+"'"+ userid+"'";
        db.execSQL(strSQL);
    }
}
