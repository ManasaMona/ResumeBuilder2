package com.example.manasaa.resumebuilder.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.R;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = SummaryActivity.class.getSimpleName();

    EditText mSummaryEditTxt;
    TextView mSummaryTextView;
    Button editButton;
    private static  int USERID;
    String mUserSummary;
    DatabaseHelper mDatabase;
    UserDetails mUserDetailsObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        USERID = getIntent().getIntExtra("USER_ID",0);
        Log.d(TAG,USERID + " user id called ");

        mDatabase =  new DatabaseHelper(this);

        mUserDetailsObj = mDatabase.getUserDetailsByID(USERID);
        mUserSummary = mUserDetailsObj.getUserSummary();

        mSummaryEditTxt = (EditText) findViewById(R.id.summaryEditTxt);
        mSummaryEditTxt.setVisibility(View.INVISIBLE);

        mSummaryTextView = (TextView) findViewById(R.id.summarytxtView);
        if(mUserSummary !=null ) {
            mSummaryTextView.setText(mUserDetailsObj.getUserSummary());
        }else{
            Toast.makeText(this,"Summary is not yet Entered", Toast.LENGTH_LONG).show();
        }

        editButton = (Button) findViewById(R.id.summarySaveButton);
        editButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.summarySaveButton:
                mSummaryEditTxt.setVisibility(View.VISIBLE);
                mSummaryTextView.setVisibility(View.INVISIBLE);

                String buttonName = editButton.getText().toString();

                if(buttonName.equals("EDIT")){
                    Log.d(TAG,"called summaruy Edit button ");
                    editButton.setText("SAVE");
                } else {

                    mSummaryTextView.setVisibility(View.VISIBLE);
                    String summary=mSummaryEditTxt.getText().toString();
                    Log.d(TAG,"called sumary save buton : "+ summary);
                    if(summary.length()!=0) {
                        mSummaryTextView.setText(summary);
                        editButton.setText("EDIT");
                        mSummaryEditTxt.setVisibility(View.INVISIBLE);
                        saveToDatabase(USERID,summary);
                    }else{
                        Toast.makeText(this,"Summary is empty", Toast.LENGTH_LONG).show();
                    }
                }
        }

    }

    private void saveToDatabase(int userid, String summary) {
        Log.d(TAG,"called  saveToDatabase(int userid, String summary)");
        mDatabase.updateSummaryByUserID(userid,summary);
    }


}
