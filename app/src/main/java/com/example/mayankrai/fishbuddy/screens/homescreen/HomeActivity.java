package com.example.mayankrai.fishbuddy.screens.homescreen;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.activity.BaseActivity;
import com.example.mayankrai.fishbuddy.base.fragment.BaseFragment;
import com.example.mayankrai.fishbuddy.databinding.HomeActivityBinding;
import com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen.FeedFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.insightscreen.InsightFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.MapFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

/**
 * Created by mayankrai on 8/22/17.
 */

public class HomeActivity extends BaseActivity<HomeActivityBinding, HomeViewModel> implements HomeMvvmView {

    @Inject
    public FirebaseAnalytics mFirebaseAnalytics;

    @Inject
    public HomePagerAdapter homePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);

        setAndBindContentView(savedInstanceState, R.layout.activity_home);

        setUpToolBar(binding.toolbar);

        setUpViewPager();
    }

    public void setUpToolBar(Toolbar view) {
        setSupportActionBar(view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Home");
    }

    private void setUpViewPager() {
        homePagerAdapter.addFragment(FeedFragment.newInstance(this),"FEED");
        homePagerAdapter.addFragment(new InsightFragment(),"INSIGHTS");
        homePagerAdapter.addFragment(MapFragment.newInstance(this),"MAPS");

        binding.viewPager.setAdapter(homePagerAdapter);
        binding.viewPager.setOffscreenPageLimit(homePagerAdapter.getCount());
        binding.viewPager.setPagingEnabled(false);
        binding.tabLayout.setupWithViewPager(binding.viewPager, true);
    }

    @Override
    public void onBackPressed() {
        if(binding.viewPager.getCurrentItem() != 0) {
            if(((BaseFragment) homePagerAdapter.getItem(binding.viewPager.getCurrentItem())).onBackPressed()){
                // do some work in fragment
            }else{
               binding.viewPager.setCurrentItem(0);
            }
            //triggerFragmentBackPress(getSupportFragmentManager().getBackStackEntryCount());
        } else {
            super.onBackPressed();
        }
    }

    private void triggerFragmentBackPress(final int count) {
        ((BaseFragment)getSupportFragmentManager().findFragmentByTag(getSupportFragmentManager().getBackStackEntryAt(count).getName())).onBackPressed();
    }


}
