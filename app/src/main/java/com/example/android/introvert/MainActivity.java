package com.example.android.introvert;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    String TAG = "INTROWERT_MAIN:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i (TAG, "IN ONCREATE");


        //Set activity's layout
        setContentView(R.layout.activity_main);


        //Get saved data
        if (savedInstanceState != null) {
            //button.setText(savedInstanceState.getString("BUNDLED"));
        }


        //Setup tabs
        CategoryAdapter categoryAdapter = new CategoryAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(categoryAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // we have saved state, but need it after on create
        super.onRestoreInstanceState(savedInstanceState);
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.i (TAG, "IN ONSTART");
    }



    @Override
    protected void onResume() {
        super.onResume(); // Always call the superclass method first
        //init components after releasing it in onPause()
        Log.i (TAG, "IN RESUME");
    }



    @Override
    protected void onPause() {
        super.onPause();// Always call the superclass method first
        // free up resources here
        Log.i (TAG, "IN ONPAUSE");
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("BUNDLED", "FU");
        // call superclass to save any view hierarchy - always needed
        super.onSaveInstanceState(outState);
    }



    @Override
    protected void onStop() {
        super.onStop();// call the superclass method first
         //free up almost all resources - for UI, leak memo, etc
        // save the note's current draft, because the activity is stopping
        // and we want to be sure the current note progress isn't lost.
        Log.i (TAG, "IN ONSTOP");
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        //Just called after onStop if returning
        Log.i (TAG, "IN RESTART");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i (TAG, "IN ONDESTROY");
        //calling to free up unfree resources
    }


}
