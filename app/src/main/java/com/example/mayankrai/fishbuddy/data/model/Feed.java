package com.example.mayankrai.fishbuddy.data.model;

/**
 * Created by mayankrai on 8/26/17.
 */

public class Feed {

    private String feed_url;

    private String feed_like_count;

    private String feed_isliked;

    private String feed_discription;

    private String feed_title;

    private String feed_type;

    public String getFeed_url ()
    {
        return feed_url;
    }

    public void setFeed_url (String feed_url)
    {
        this.feed_url = feed_url;
    }

    public String getFeed_like_count ()
    {
        return feed_like_count;
    }

    public void setFeed_like_count (String feed_like_count)
    {
        this.feed_like_count = feed_like_count;
    }

    public String getFeed_isliked ()
    {
        return feed_isliked;
    }

    public void setFeed_isliked (String feed_isliked)
    {
        this.feed_isliked = feed_isliked;
    }

    public String getFeed_discription ()
    {
        return feed_discription;
    }

    public void setFeed_discription (String feed_discription)
    {
        this.feed_discription = feed_discription;
    }

    public String getFeed_title ()
    {
        return feed_title;
    }

    public void setFeed_title (String feed_title)
    {
        this.feed_title = feed_title;
    }

    public String getFeed_type ()
    {
        return feed_type;
    }

    public void setFeed_type (String feed_type)
    {
        this.feed_type = feed_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [feed_url = "+feed_url+", feed_like_count = "+feed_like_count+", feed_isliked = "+feed_isliked+", feed_discription = "+feed_discription+", feed_title = "+feed_title+", feed_type = "+feed_type+"]";
    }

}


