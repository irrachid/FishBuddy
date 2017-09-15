package com.example.mayankrai.fishbuddy.data.remote;

import com.example.mayankrai.fishbuddy.data.model.DirectionResults;
import com.example.mayankrai.fishbuddy.data.model.NearByApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapRetrofitApi
{
    @GET("/maps/api/directions/json")
    Call<DirectionResults> getJson(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

    @GET("/maps/api/place/nearbysearch/json?sensor=true")
    Call<NearByApiResponse> getNearbyPlaces(@Query("key") String key ,@Query("type") String type, @Query("location") String location, @Query("radius") int radius);
}
