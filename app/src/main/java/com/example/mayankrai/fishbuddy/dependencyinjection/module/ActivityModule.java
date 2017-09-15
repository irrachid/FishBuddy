package com.example.mayankrai.fishbuddy.dependencyinjection.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mayankrai.fishbuddy.base.navigator.ActivityNavigator;
import com.example.mayankrai.fishbuddy.base.navigator.Navigator;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityFragmentManager;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final AppCompatActivity mActivity;

  public ActivityModule(AppCompatActivity activity) {
    mActivity = activity;
  }

  @Provides
  @PerActivity
  @ActivityContext
  AppCompatActivity provideAppCompactActivity() { return mActivity; }

  @Provides
  @PerActivity
  @ActivityContext
  Context provideActivityContext() { return mActivity; }

  @Provides
  @PerActivity
  @ActivityFragmentManager
  FragmentManager provideFragmentManager() { return mActivity.getSupportFragmentManager(); }

  @Provides
  @PerActivity
  Navigator provideNavigator() { return new ActivityNavigator(mActivity); }

  @Provides
  @PerActivity
  RxPermissions provideRxPermissions() {
    return new RxPermissions(mActivity);
  }

  @Provides
  @PerActivity
  FirebaseAnalytics provideFirebaseAnalytics() {
    return FirebaseAnalytics.getInstance(mActivity);
  }
}
