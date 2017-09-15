package com.example.mayankrai.fishbuddy.data.model;

import java.util.List;

/**
 * Created by mayankrai on 8/28/17.
 */

public class FeedModel {
    private List<Feed> feed;


    public List<Feed> getFeedList() {
        return feed;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feed = feedList;
    }
}
