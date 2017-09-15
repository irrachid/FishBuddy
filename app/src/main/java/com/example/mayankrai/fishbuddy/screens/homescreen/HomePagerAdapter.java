package com.example.mayankrai.fishbuddy.screens.homescreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityFragmentManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mayankrai on 8/26/17.
 */

public class HomePagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    @Inject
    public HomePagerAdapter(@ActivityFragmentManager FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
