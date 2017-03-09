package com.example.manasaa.resumebuilder.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.example.manasaa.resumebuilder.Fragments.MyPreferenceFragment;
import com.example.manasaa.resumebuilder.R;

/**
 * Created by manasa.a on 09-03-2017.
 */

public class AboutPreferenceActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }


}
