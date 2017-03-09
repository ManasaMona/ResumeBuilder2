package com.example.manasaa.resumebuilder.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.Other.CircleTransform;
import com.example.manasaa.resumebuilder.R;

import java.io.IOException;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class HeaderActivity extends AppCompatActivity  implements  View.OnClickListener{
    String TAG = HeaderActivity.class.getSimpleName();
    TextView mNameTxtView, mEmailTxtView , mExperienceTxtView;
    EditText mExperinceEditTxt;
    ImageView mProfileImage;
    FloatingActionButton mFab;
    Button mSaveButton;
    private  static int USERID;
    DatabaseHelper mDatabase;
    private int PICK_IMAGE_REQUEST = 1;
    private String profileURL;
    UserDetails userObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        USERID = getIntent().getIntExtra("USER_ID",0);
        Log.d(TAG,USERID + " user id called ");


        mNameTxtView = (TextView) findViewById(R.id.nameTxtView);
        mEmailTxtView = (TextView) findViewById(R.id.emailTxtView);
        mExperienceTxtView = (TextView) findViewById(R.id.experienceTxtView);
        mExperinceEditTxt = (EditText) findViewById(R.id.experienceEditTxt);
        mProfileImage = (ImageView) findViewById(R.id.profileImage);
        mFab = (FloatingActionButton) findViewById(R.id.editFab);
        mSaveButton = (Button) findViewById(R.id.expSaveButton);

        mSaveButton.setVisibility(INVISIBLE);
        mExperinceEditTxt.setVisibility(INVISIBLE);

        mFab.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);

    }

    private void displayUserData() {
        mNameTxtView.setText(userObj.getUserName());
        mEmailTxtView.setText(userObj.getUserEmail());
        if(userObj.getUserExperience()!=0) {
            mExperienceTxtView.setText(userObj.getUserExperience()+" years");
        }else{
            Toast.makeText(this," Experince == 0 ", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG,userObj.getUserProfileLink() +" called link");
        Glide.with(this).load(userObj.getUserProfileLink()).crossFade().thumbnail(0.2f).bitmapTransform(new CircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mProfileImage);

    }

    private void getDataFromDatabase() {
        mDatabase = new DatabaseHelper(this);
        userObj = mDatabase.getUserDetailsByID(USERID);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.editFab:
                mExperienceTxtView.setVisibility(INVISIBLE);
                mExperinceEditTxt.setVisibility(VISIBLE);
                mSaveButton.setVisibility(VISIBLE);
                break;
            case R.id.expSaveButton:
                int experience = Integer.parseInt(mExperinceEditTxt.getText().toString());
                Log.d(TAG,experience+ "called experince");
                if(mExperinceEditTxt.getText().toString().length()==0){
                    Toast.makeText(this," Experince is empty ", Toast.LENGTH_LONG).show();

                }else{
                    updateToDatabase(experience, USERID);
                    mExperienceTxtView.setVisibility(VISIBLE);
                    mExperienceTxtView.setText(experience+"");
                    mExperinceEditTxt.setVisibility(INVISIBLE);
                    mSaveButton.setVisibility(INVISIBLE);
                }
                break;
            case R.id.profileImage :
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            profileURL = uri+"";
            updateToDatabase(profileURL,USERID);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateToDatabase(int experience, int userid) {        Log.d(TAG,"called updateToDatabase(int experience, int userid)" );
        mDatabase.updateExperinceByUserId(experience, userid);
    }
    private void updateToDatabase(String profileURL, int userid) {   Log.d(TAG,"called updateToDatabase(int experience, int userid)" );
        mDatabase.updateProfileURLByUserId(profileURL, userid);
    }

    @Override
    protected void onResume() { Log.d(TAG,"called on Resume");
        getDataFromDatabase();
        displayUserData();
        super.onResume();
    }
}
