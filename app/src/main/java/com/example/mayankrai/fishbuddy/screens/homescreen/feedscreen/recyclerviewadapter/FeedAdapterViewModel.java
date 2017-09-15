package com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen.recyclerviewadapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.navigator.Navigator;
import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.BaseViewModel;
import com.example.mayankrai.fishbuddy.data.model.Feed;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerViewHolder;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;



@PerViewHolder
public class FeedAdapterViewModel extends BaseViewModel<FeedAdapterMvvmView> {


    public Picasso picasso;
    private final Context context;
    private Feed feed;
    private boolean isLast;

    @Inject
    public FeedAdapterViewModel(@ActivityContext Context context, Navigator navigator, Picasso picasso) {
        this.picasso = picasso;
        this.context = context;
    }


    public void update(Feed feed, boolean isLast) {
        this.isLast = isLast;
        this.feed = feed;

        notifyChange();
    }


    public String getTitle(){
        return feed.getFeed_title();
    }

    public String getDiscription(){
        return feed.getFeed_discription();
    }

    public String getLikeCount(){
        return feed.getFeed_like_count() + " Likes";
    }

    public String getImageUrl(){
        return feed.getFeed_url();
    }

    @BindingAdapter({"bind:imageUrl", "bind:picasso"})
    public static void bindPicassoData(ImageView view, String imageUrl, Picasso picasso) {
        if(imageUrl.isEmpty()){
            picasso.load("http://vignette3.wikia.nocookie.net/thefaultinourstars/images/4/48/Fault-In-Our-Stars-Fan-Art-the-fault-in-our-stars-34488662-500-749.png/revision/latest?cb=20131204141616").fit().centerCrop()
                    .placeholder(R.drawable.picasso)
                    .error(R.drawable.picasso)
                    .into(view);
        }else{
            picasso.load(imageUrl).fit().centerCrop()
                    .placeholder(R.drawable.picasso)
                    .error(R.drawable.picasso)
                    .into(view);
        }

    }

}
