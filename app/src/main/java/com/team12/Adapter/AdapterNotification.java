package com.team12.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class AdapterNotification extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ArrayList<String> fragmentTitle = new ArrayList<>();
    Context context;


    public AdapterNotification(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //-----for show the title of the tab---------
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    //-----for add fragment-----------
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitle.add(title);
    }
}
