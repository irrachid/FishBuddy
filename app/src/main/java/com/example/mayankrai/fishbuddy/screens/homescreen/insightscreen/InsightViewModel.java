package com.example.mayankrai.fishbuddy.screens.homescreen.insightscreen;


import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.BaseViewModel;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.insightscreen.bettatypescreen.BettaTypeFragment;

import javax.inject.Inject;

/**
 * Created by mayankrai on 8/26/17.
 */
@PerFragment
public class InsightViewModel extends BaseViewModel<InsightView> implements IInsightViewModel {


    public InsightPagerAdapter insightPagerAdapter;

    @Inject
    public InsightViewModel(InsightPagerAdapter insightPagerAdapter){
        this.insightPagerAdapter = insightPagerAdapter;
    }


    @BindingAdapter({"bind:setInsightAdapter"})
    public static void bindInsightViewPagerAdapter(ViewPager view, InsightPagerAdapter insightPagerAdapter ) {
        insightPagerAdapter.addFragment(new BettaTypeFragment(),"HISTORY");
        insightPagerAdapter.addFragment(new BettaTypeFragment(),"CARE");
        insightPagerAdapter.addFragment(new BettaTypeFragment(),"DISEASE");
        insightPagerAdapter.addFragment(new BettaTypeFragment(),"TYPES");
        insightPagerAdapter.addFragment(new BettaTypeFragment(),"TRICKS");
        insightPagerAdapter.addFragment(new BettaTypeFragment(),"AQUARIAM");

        view.setAdapter(insightPagerAdapter);
        view.setOffscreenPageLimit(insightPagerAdapter.getCount());

    }


    @BindingAdapter("bind:setInsightPager")
    public static void setInsightViewPager(TabLayout view, ViewPager viewPagerInsight) {
        view.setupWithViewPager(viewPagerInsight, true);
    }
}
