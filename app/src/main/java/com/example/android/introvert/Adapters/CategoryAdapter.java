package com.example.android.introvert.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.introvert.Tabs.ExtensionsFragment;
import com.example.android.introvert.Tabs.ProjectsFragment;
import com.example.android.introvert.Tabs.TimelineFragment;
import com.example.android.introvert.Tabs.ToDoFragment;

/**
 * Created by takeoff on 024 24 Oct 17.
 * For larger sets of pages, consider FragmentStatePagerAdapter.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private final String TAG = "INTROWERT_CATEGORY_ADAPTER";

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }


    //Pages' mapping
    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new TimelineFragment();
        else if (position == 1) return new ToDoFragment();
        else if (position == 2) return new ProjectsFragment();
        else return new ExtensionsFragment();
    }


    //Number of pages
    @Override
    public int getCount() {
        return 4;
    }


    //Pages' titles
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Timeline";
        else if (position == 1) return "To Do";
        else if (position == 2) return "Projects";
        else return "Extensions";
    }

}
