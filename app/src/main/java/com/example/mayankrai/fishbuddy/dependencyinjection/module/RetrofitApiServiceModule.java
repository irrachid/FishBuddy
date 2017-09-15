package com.example.mayankrai.fishbuddy.dependencyinjection.module;

import com.example.mayankrai.fishbuddy.data.remote.FBuddyRetrofitApi;
import com.example.mayankrai.fishbuddy.data.remote.MapRetrofitApi;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.MapRetrofit;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerApplication;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class RetrofitApiServiceModule {
    @Provides
    @PerApplication
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @PerApplication
    public FBuddyRetrofitApi provideRetrofitApi(Retrofit retrofit) {
        return retrofit.create(FBuddyRetrofitApi.class);
    }

    @Provides
    @MapRetrofit
    @PerApplication
    public Retrofit provideMapRetrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @PerApplication
    public MapRetrofitApi provideMapRetrofitApi(@MapRetrofit Retrofit retrofit) {
        return retrofit.create(MapRetrofitApi.class);
    }
}
