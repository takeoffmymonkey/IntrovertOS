package com.example.android.introvert.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.introvert.R;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class ExtensionsFragment extends Fragment {


    String TAG = "INTROWERT_EXTENSIONS:";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i (TAG, "IN ONATTACH");
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i (TAG, "IN ONCREATE");
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i (TAG, "IN ONCREATEVIEW");

        //Return inflated layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i (TAG, "IN ONACTIVITYCREATED");
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.i (TAG, "IN ONSTART");
    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i (TAG, "IN ONRESUME");
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.i (TAG, "IN ONPAUSE");
    }



    @Override
    public void onStop() {
        super.onStop();
        Log.i (TAG, "IN ONSTOP");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i (TAG, "IN ONDESTROYVIEW");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i (TAG, "IN ONDESTROY");
    }



    @Override
    public void onDetach() {
        super.onDetach();
        Log.i (TAG, "IN ONDETACH");
    }
}
