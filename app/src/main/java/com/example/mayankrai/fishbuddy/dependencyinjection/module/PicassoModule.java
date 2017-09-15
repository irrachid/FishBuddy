package com.example.mayankrai.fishbuddy.dependencyinjection.module;

import android.content.Context;

import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.AppContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerApplication;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;


@Module(includes = {AppModule.class, NetworkModule.class})
public class PicassoModule {
    @Provides
    @PerApplication
    Picasso providePicasso(@AppContext Context context,OkHttp3Downloader okHttp3Downloader ) {
        return new Picasso.Builder(context).downloader(okHttp3Downloader).build();
    }

    @Provides
    @PerApplication
    OkHttp3Downloader provideOkHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }

}
