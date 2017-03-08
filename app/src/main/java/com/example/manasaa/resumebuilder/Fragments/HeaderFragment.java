package com.example.manasaa.resumebuilder.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manasaa.resumebuilder.Database.DatabaseHelper;
import com.example.manasaa.resumebuilder.Model.UserDetails;
import com.example.manasaa.resumebuilder.R;

import static android.content.Context.MODE_PRIVATE;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_EMAIL;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_NAME;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_PROFILEURL;
import static com.example.manasaa.resumebuilder.Other.SessionManager.KEY_USERID;
import static com.example.manasaa.resumebuilder.Other.SessionManager.PREF_NAME;

/**
 * Created by manasa.a on 03-03-2017.
 */

public class HeaderFragment extends Fragment {
    String TAG = HeaderFragment.class.getSimpleName();
    TextView nameTxtView,emailTxtview;

    DatabaseHelper mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.resume_header, container, false);
        mDatabase = new DatabaseHelper(getActivity());
        SharedPreferences shared = getActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String user_emailId = shared.getString(KEY_EMAIL,"");

        UserDetails obj = mDatabase.getUserDetailsByEmail(user_emailId);

        nameTxtView = (TextView) v.findViewById(R.id.user_name_txtView);
        nameTxtView.setText(obj.getUserName());
        emailTxtview = (TextView) v.findViewById(R.id.user_emailID_txtView);
        emailTxtview.setText(obj.getUserEmail());

        // Inflate the layout for this fragment
        return v;
    }
}
