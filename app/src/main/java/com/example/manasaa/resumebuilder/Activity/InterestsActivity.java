package com.example.manasaa.resumebuilder.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.Interest;
import com.example.manasaa.resumebuilder.R;
import com.example.manasaa.resumebuilder.ViewHolder.ViewHolderInterests;

import java.util.ArrayList;

public class InterestsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = InterestsActivity.class.getSimpleName();
    DatabaseHelper mDatabase;
    private static int USERID;
    private FloatingActionButton interests_fab;
    private static EditText interest_editTxt;
    private static Dialog dialog;
    ViewHolderInterests adapter_interests;
    ListView  mInterestsListView;
    ArrayList<Interest> mArrayOfInterests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "called onCREATE()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        USERID = getIntent().getIntExtra("USER_ID",0);
        Log.d(TAG,USERID + " user id called ");

        interests_fab = (FloatingActionButton) findViewById(R.id.addFabInterest);
        interests_fab.setOnClickListener(this);
    }

    private void setDataInAdapter() {
        mInterestsListView = (ListView) findViewById(R.id.interests_listViewActivity);
        ViewHolderInterests adapter_interests = new ViewHolderInterests(this,mArrayOfInterests);
        mInterestsListView .setAdapter(adapter_interests);
    }

    private void getDataFromDatabase() {

        mArrayOfInterests = new ArrayList<Interest>();
        int nuumPros = mDatabase.numberOfInterests();
        Log.d(TAG, nuumPros+ " called coount pros");
        mArrayOfInterests = mDatabase.getInterestsByUserId(USERID);
    }

    @Override
    public void onClick(View v) {
        int id_view = v.getId();
        switch (id_view){
            case R.id.addFabInterest:
                showDailog();
                break;
            case R.id.interest_save_button:
                Log.d(TAG,"called interst save button");
                String interest;
               interest=interest_editTxt.getText().toString();
                if(interest.length()!=0) {
                    Log.d(TAG, interest + " called from dialog");
                    saveToDatabase(USERID, interest);
                }
                else{
                    Toast.makeText(this, " Interest is empty", Toast.LENGTH_LONG).show();
                }
                onResume();
                dialog.dismiss();

                break;
        }
    }

    private void saveToDatabase(int userid, String interest) {
        Log.d(TAG,"called save to db");
        Log.d(TAG, userid+"jj"+interest+ " called from dialog");
        Interest pro = new Interest(userid,interest);
        mDatabase.insertIntoUserInterestsTable(pro);
    }

    private void showDailog() {
        Button intrstSave;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.interest_input_dailog);
        interest_editTxt = (EditText) dialog.findViewById( R.id.interest_name );
        intrstSave= (Button) dialog.findViewById( R.id.interest_save_button );
        intrstSave.setOnClickListener(this);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onResume() {
        mDatabase =  new DatabaseHelper(this);
        getDataFromDatabase();
        setDataInAdapter();
        mDatabase.close();
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG,"called on start");
        super.onStart();
    }
}
