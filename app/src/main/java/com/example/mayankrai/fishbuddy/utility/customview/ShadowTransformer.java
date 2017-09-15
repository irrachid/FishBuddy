package com.example.mayankrai.fishbuddy.utility.customview;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager.MapPagerAdapter;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager.MapPlaceFragment;
import com.example.mayankrai.fishbuddy.utility.maps.PulseOverlayLayout;


public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    int MAX_ELEVATION_FACTOR = 8;
    private ViewPager mViewPager;
    private MapPagerAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;
    PulseOverlayLayout pulseOverlayLayout;

    public ShadowTransformer(ViewPager viewPager, MapPagerAdapter adapter, PulseOverlayLayout mapOverlayLayout) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        mAdapter = adapter;

        pulseOverlayLayout = mapOverlayLayout;

        MapPlaceFragment fragment = (MapPlaceFragment) mAdapter.getItem(mViewPager.getCurrentItem());
        CardView currentCard = fragment.getCardView();

        if (currentCard != null) {
            currentCard.animate().scaleY(1.1f);
            currentCard.animate().scaleX(1.1f);
        }
    }



    @Override
    public void transformPage(View page, float position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float baseElevation = MAX_ELEVATION_FACTOR;
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1
                || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        MapPlaceFragment fragment = (MapPlaceFragment) mAdapter.getItem(mViewPager.getCurrentItem());
        CardView currentCard = fragment.getCardView();

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation
                    * (MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        MapPlaceFragment fragmentNext = (MapPlaceFragment) mAdapter.getItem(nextPosition);
        CardView nextCard = fragmentNext.getCardView();


        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation
                    * (MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        pulseOverlayLayout.showMarker(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
