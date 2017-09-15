package com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager;


import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.BaseViewModel;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerFragment;
import com.example.mayankrai.fishbuddy.utility.maps.Place;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by mayankrai on 8/26/17.
 */
@PerFragment
public class MapPlaceViewModel extends BaseViewModel<MapPlaceView>{


    private Place place;

    @Inject public Picasso picasso;


    @Inject public MapPlaceViewModel(){}

    public void update(Place place){
        this.place = place;
        notifyChange();
    }

    public String getName(){
        return place.getName();
    }

    public String getId(){
        return String.valueOf(place.getId());
    }

    public String getDescription(){
        return place.getDescription();
    }

    public String getOpeningHours(){
        return place.getOpeningHours();
    }

    public double getLat(){
        return place.getLat();
    }

    public double getLng(){
        return place.getLng();
    }

    public String getPrice(){
        return String.valueOf(place.getPrice());
    }

    public String getImageUrl(){
        return place.getImageUrl();
    }

    @BindingAdapter({"bind:imagePagerPlaceUrl", "bind:pagerPicasso"})
    public static void bindMapPicassoData(ImageView view, String imageUrl, Picasso picasso) {
        if(imageUrl.isEmpty()){
            picasso.load("http://vignette3.wikia.nocookie.net/thefaultinourstars/images/4/48/Fault-In-Our-Stars-Fan-Art-the-fault-in-our-stars-34488662-500-749.png/revision/latest?cb=20131204141616").fit().centerCrop()
                    .placeholder(R.drawable.picasso)
                    .error(R.drawable.picasso)
                    .into(view);
        }else{
            picasso.load(imageUrl).fit().centerInside()
                    .placeholder(R.drawable.picasso)
                    .error(R.drawable.picasso)
                    .into(view);
        }

    }
}
