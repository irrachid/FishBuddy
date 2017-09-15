package com.example.mayankrai.fishbuddy.utility.maps;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankrai.fishbuddy.DetailsLayoutBinding;
import com.example.mayankrai.fishbuddy.R;


public class DetailsLayout extends CoordinatorLayout {

    public  DetailsLayoutBinding dataBinding;

    public DetailsLayout(final Context context) {
        this(context, null);
    }

    public DetailsLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.dataBinding = DetailsLayoutBinding.bind(this);
    }

    private void setData(Place place) {
        dataBinding.title.setText(place.getName());
        dataBinding.description.setText(place.getDescription());

        //dataBinding.headerImage.

        dataBinding.takeMe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public static Scene showScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName, final Place data) {
        DetailsLayout detailsLayout = (DetailsLayout) activity.getLayoutInflater().inflate(R.layout.place_item, container, false);
        detailsLayout.setData(data);

        TransitionSet set = new ShowDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }

    public static Scene hideScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName) {
        DetailsLayout detailsLayout = (DetailsLayout) container.findViewById(R.id.bali_details_container);

        TransitionSet set = new HideDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }
}
