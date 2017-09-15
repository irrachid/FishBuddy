package com.example.mayankrai.fishbuddy.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankrai on 9/11/17.
 */

public class Route {
    @SerializedName("overview_polyline")
    private OverviewPolyLine overviewPolyLine;
    @SerializedName("bounds") Bounds bounds;

    private List<Legs> legs;

    public OverviewPolyLine getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public Bounds getBounds() {
        return bounds;
    }
}
