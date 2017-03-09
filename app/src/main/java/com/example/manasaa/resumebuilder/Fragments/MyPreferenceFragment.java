package com.example.manasaa.resumebuilder.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.example.manasaa.resumebuilder.Activity.AboutActivity;
import com.example.manasaa.resumebuilder.Activity.MainActivity;
import com.example.manasaa.resumebuilder.R;

/**
 * Created by manasa.a on 09-03-2017.
 */

public  class MyPreferenceFragment extends PreferenceFragment
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference version = findPreference("version");
        int SDKversion=  android.os.Build.VERSION.SDK_INT;
        version.setSummary(String.format("Version: %s", SDKversion));
        Preference logout = findPreference("logout");
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                // TODO Auto-generated method
                SharedPreferences preferences =getActivity().getSharedPreferences("LOGINPREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intentToLoginScreen = new Intent(getActivity(), MainActivity.class);
                startActivity(intentToLoginScreen);
               getActivity(). finish();
                return false;
            }
        });
    }
}

