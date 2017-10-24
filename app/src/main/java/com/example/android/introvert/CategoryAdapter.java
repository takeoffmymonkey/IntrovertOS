package com.example.android.introvert;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by takeoff on 024 24 Oct 17.
 * For larger sets of pages, consider FragmentStatePagerAdapter.
 */

class CategoryAdapter extends FragmentPagerAdapter {

    CategoryAdapter(FragmentManager fm) {
        super(fm);
    }


    //Pages' mapping
    @Override
    public Fragment getItem(int position) {
        if (position == 0) return new TimelineFragment();
        else return new ToDoFragment();
    }


    //Number of pages
    @Override
    public int getCount() {
        return 2;
    }


    //Pages' titles
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Timeline";
        else return "To Do";

    }

}
