package com.example.mayankrai.fishbuddy.dependencyinjection.component;


import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;

import com.example.mayankrai.fishbuddy.base.navigator.Navigator;
import com.example.mayankrai.fishbuddy.data.remote.FBuddyRetrofitApi;
import com.example.mayankrai.fishbuddy.data.remote.MapRetrofitApi;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.ActivityModule;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityFragmentManager;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.AppContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerActivity;
import com.example.mayankrai.fishbuddy.screens.homescreen.HomeActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Component;
import io.realm.Realm;

@PerActivity
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class})
public interface ActivityComponent {

  @ActivityContext
  Context activityContext();

  @ActivityFragmentManager
  FragmentManager defaultFragmentManager();

  Navigator navigator();

  RxPermissions rxPermissions();

  @AppContext
  Context appContext();

  Resources resources();

  RefWatcher refWatcher();

  Realm realm();

  NetworkInfo networkInfo();

  FBuddyRetrofitApi fBuddyRetrofitApi();

  MapRetrofitApi mapRetrofitApi();

  Picasso picasso();

  FirebaseAnalytics mFirebaseAnalytics();

  void inject(HomeActivity activity);
}
