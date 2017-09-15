package com.example.mayankrai.fishbuddy.dependencyinjection.module;

import android.content.Context;

import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.AppContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerApplication;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module(includes = AppModule.class)
public class RealmModule {
    @Provides
    @PerApplication
    RealmConfiguration provideRealmConfiguration(@AppContext Context context) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        return realmConfiguration;
    }

    @Provides
    @PerApplication
    Realm provideRealm(RealmConfiguration realmConfiguration) {
        return Realm.getInstance(realmConfiguration);
    }

}
