package com.example.mayankrai.fishbuddy.data.remote;

import com.example.mayankrai.fishbuddy.data.model.FeedModel;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface FBuddyRetrofitApi
{
    @GET("59a4f255100000c70bb2ac13")
    Single<FeedModel> getAllFeeds();
}
