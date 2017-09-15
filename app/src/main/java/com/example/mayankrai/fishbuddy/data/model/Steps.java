package com.example.mayankrai.fishbuddy.data.model;


/**
 * Created by mayankrai on 9/11/17.
 */

public class Steps {
    private Location start_location;
    private Location end_location;
    private OverviewPolyLine polyline;

    private Duration duration;

    private Duration distance;

    public Duration getDuration() {
        return duration;
    }

    public Duration getDistance() {
        return distance;
    }

    public Location getStart_location() {
        return start_location;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public OverviewPolyLine getPolyline() {
        return polyline;
    }
}
