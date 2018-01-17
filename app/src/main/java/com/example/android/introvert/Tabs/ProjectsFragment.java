package com.example.android.introvert.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.introvert.R;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class ProjectsFragment extends Fragment {


    private final String TAG = "INTROWERT_PROJECTS";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Return inflated layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false);

    }
}
