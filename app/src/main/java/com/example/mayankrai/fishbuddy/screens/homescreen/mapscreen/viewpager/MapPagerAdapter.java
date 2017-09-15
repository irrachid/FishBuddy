package com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ChildFragmentManager;
import com.example.mayankrai.fishbuddy.utility.maps.Place;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by mayankrai on 8/26/17.
 */

public class MapPagerAdapter extends FragmentPagerAdapter {
    private final FragmentManager manager;
    private ArrayList<Place> places;
    private int viewPagerId;

    @Inject
    public MapPagerAdapter(@ChildFragmentManager FragmentManager manager) {
        super(manager);
        this.manager = manager;
    }


    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public Fragment getItem(int position) {
        return new MapPlaceFragment(places.get(position));
        //return MapPlaceFragment.newInstance(places.get(position));
    }

    /**
     * @return may return null if the fragment has not been instantiated yet for that position - this depends on if the fragment has been viewed
     * yet OR is a sibling covered by {@link android.support.v4.view.ViewPager#setOffscreenPageLimit(int)}. Can use this to call methods on
     * the current positions fragment.
     */
    public @Nullable
    Fragment getFragmentForPosition(int position) {
        String tag = makeFragmentName(viewPagerId, getItemId(position));
        Fragment fragment = manager.findFragmentByTag(tag);
        return fragment;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void setViewPagerId(int viewPagerId) {
        this.viewPagerId = viewPagerId;
    }
}
