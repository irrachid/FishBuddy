package com.example.mayankrai.fishbuddy.dependencyinjection.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.AppContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerApplication;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application mApp;

    public AppModule(Application app) {
        mApp = app;
    }

    @Provides
    @PerApplication
    @AppContext
    Context provideAppContext() {
        return mApp;
    }

    @Provides
    @PerApplication
    ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) mApp.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @PerApplication
    NetworkInfo provideNetworkInfo(ConnectivityManager connectivityManager) {
        return connectivityManager.getActiveNetworkInfo();
    }

    @Provides
    @PerApplication
    Resources provideResources() {
        return mApp.getResources();
    }

    @Provides
    @PerApplication
    RefWatcher provideRefWatcher() {
        return LeakCanary.install(mApp);
    }
}
