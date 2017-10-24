package com.example.android.introvert;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by takeoff on 024 24 Oct 17.
 */

public class TimelineFragment extends Fragment {

    String TAG = "INTROVERT_TIMELINE:";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.w("WARNING: ", "IN ONATTACH OF ITEMS FRAGMENT");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("WARNING: ", "IN ONCREATE OF ITEMS FRAGMENT");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.w("WARNING: ", "IN ONACTIVITYCREATE OF ITEMS FRAGMENT");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w("WARNING: ", "IN ONSTART OF ITEMS FRAGMENT");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w("WARNING: ", "IN ONRESUME OF ITEMS FRAGMENT");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w("WARNING: ", "IN ONPAUSE OF ITEMS FRAGMENT");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w("WARNING: ", "IN ONSTOP OF ITEMS FRAGMENT");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.w("WARNING: ", "IN ONDESTROYVIEW OF ITEMS FRAGMENT");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.w("WARNING: ", "IN ONDETACH OF ITEMS FRAGMENT");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w("WARNING: ", "IN ONDESTROY OF ITEMS FRAGMENT");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_timeline, container, false);

    }
}
