package com.example.mayankrai.fishbuddy.screens.homescreen;


import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.BaseViewModel;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class HomeViewModel extends BaseViewModel<HomeMvvmView>{

    @Inject
    public HomeViewModel(){}

}
