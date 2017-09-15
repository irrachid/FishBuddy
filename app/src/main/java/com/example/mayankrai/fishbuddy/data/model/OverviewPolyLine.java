package com.example.mayankrai.fishbuddy.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankrai on 9/11/17.
 */

public class OverviewPolyLine {
    @SerializedName("points")
    public String points;

    public String getPoints() {
        return points;
    }
}
