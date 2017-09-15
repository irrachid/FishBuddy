package com.example.mayankrai.fishbuddy;

import android.app.Application;

import com.example.mayankrai.fishbuddy.dependencyinjection.component.AppComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.component.DaggerAppComponent;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.AppModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by mayankrai on 8/26/17.
 */

public class FishBuddyApplication extends Application
{
    private static  FishBuddyApplication appInstance;

    private static AppComponent sAppComponent = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if(LeakCanary.isInAnalyzerProcess(this)) return;

        appInstance = this;

        Timber.plant(new Timber.DebugTree());

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        sAppComponent.inject(appInstance);

    }

    public static FishBuddyApplication getInstance() { return appInstance; }

    public static AppComponent getAppComponent() { return sAppComponent; }
}
