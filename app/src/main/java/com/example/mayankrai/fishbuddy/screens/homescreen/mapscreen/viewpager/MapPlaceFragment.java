package com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.fragment.BaseFragment;
import com.example.mayankrai.fishbuddy.databinding.MapPlaceFragmentBinding;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.MapFragment;
import com.example.mayankrai.fishbuddy.utility.maps.Place;

public class MapPlaceFragment extends BaseFragment<MapPlaceFragmentBinding,MapPlaceViewModel> implements MapPlaceView {

    private Place places;

    public MapPlaceFragment() {
    }

    @SuppressLint("ValidFragment")
    public MapPlaceFragment(Place places) {
        this.places = places;
    }

    @SuppressLint("NewApi")
    public static Fragment newInstance(Place place) {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("place", place);

        System.out.println("MapPlaceFragment.newInstance p="+place.toString());

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            places = bundle.getParcelable("place");
        }

        fragmentComponent().inject(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Place place = getArguments().getParcelable("place");

        System.out.println("MapPlaceFragment.onCreateView place="+places.toString());

        return setAndBindContentView(inflater, container, savedInstanceState, R.layout.item_bali_place);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public boolean onBackPressed() {
        System.out.println("MapPlaceFragment.onBackPressed");
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.update(places);

    }

    public CardView getCardView(){
        return binding.root;
    }
}
