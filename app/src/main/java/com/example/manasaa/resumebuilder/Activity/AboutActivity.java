package com.example.manasaa.resumebuilder.Activity;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manasaa.resumebuilder.Other.SessionManager;
import com.example.manasaa.resumebuilder.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = AboutActivity.class.getSimpleName();
    Button mLogoutButton;
    TextView mAndroidVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mLogoutButton = (Button) findViewById(R.id.logoutButton);
        mLogoutButton.setOnClickListener(this);

        int SDKversion=  android.os.Build.VERSION.SDK_INT;

        mAndroidVersion = (TextView) findViewById(R.id.androidVersionTxtView);
        mAndroidVersion.setText(SDKversion+"");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
            switch (id){
                case R.id.logoutButton :
                    SharedPreferences preferences =getSharedPreferences("LOGINPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    finish();
            }
    }
}
