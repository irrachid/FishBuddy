package com.example.mayankrai.fishbuddy.dependencyinjection.component;

import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkInfo;

import com.example.mayankrai.fishbuddy.FishBuddyApplication;
import com.example.mayankrai.fishbuddy.data.remote.FBuddyRetrofitApi;
import com.example.mayankrai.fishbuddy.data.remote.MapRetrofitApi;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.AppModule;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.PicassoModule;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.RealmModule;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.RetrofitApiServiceModule;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.AppContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerApplication;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by mayankrai on 8/22/17.
 */
@PerApplication
@Component(modules={AppModule.class, RetrofitApiServiceModule.class, RealmModule.class, PicassoModule.class})
public interface AppComponent {
    @AppContext
    Context appContext();

    Resources resources();

    RefWatcher refWatcher();

    Realm realm();

    NetworkInfo networkInfo();

    FBuddyRetrofitApi fBuddyRetrofitApi();

    MapRetrofitApi mapRetrofitApi();

    Picasso picasso();

    void inject(FishBuddyApplication app);

}
