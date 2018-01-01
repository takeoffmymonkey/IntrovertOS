package com.example.android.introvert.Fragments;

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

public class ToDoFragment extends Fragment {


    String TAG = "INTROWERT_TODO:";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Return inflated layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false);

    }
}
