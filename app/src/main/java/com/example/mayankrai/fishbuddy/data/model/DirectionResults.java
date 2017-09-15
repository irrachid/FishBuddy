package com.example.mayankrai.fishbuddy.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankrai on 9/11/17.
 */

public class DirectionResults {
    @SerializedName("routes")
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }
}