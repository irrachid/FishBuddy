package com.example.mayankrai.fishbuddy.dependencyinjection.component;

import com.example.mayankrai.fishbuddy.dependencyinjection.module.FragmentModule;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen.FeedFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.insightscreen.InsightFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.insightscreen.bettatypescreen.BettaTypeFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.MapFragment;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager.MapPlaceFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Component;

@PerFragment
@Component(dependencies = ActivityComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent {
    RxPermissions rxPermissions();

    void inject(FeedFragment fragment);
    void inject(InsightFragment fragment);
    void inject(MapFragment fragment);
    void inject(BettaTypeFragment fragment);
    void inject(MapPlaceFragment fragment);
}
