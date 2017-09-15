package com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen;


import com.example.mayankrai.fishbuddy.base.uibase.view.MvvmView;
import com.example.mayankrai.fishbuddy.data.model.Bounds;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager.MapPagerAdapter;
import com.example.mayankrai.fishbuddy.utility.maps.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

/**
 * Created by mayankrai on 8/26/17.
 */

public interface MapView extends MvvmView {
    void setMapOverlayLayout(GoogleMap map);

    void setMapAndAddMaker(LatLngBounds latLngBounds, ArrayList<Place> placeArrayList);

    void setViewPager(MapPagerAdapter adapter);

    void setMapStyle(GoogleMap googleMap);

    void hideAllMarkers();

    void hideUnselectedMarkers(int position);

    void drawRoute(final int position);

    void setDetailScene(int position);

    void drawPolylinesOnMap(final ArrayList<LatLng> polylines, Bounds bound);

    void drawPolylinesOnMap(final ArrayList<LatLng> polylines);

    void updateMapZoomAndRegion(final LatLng northeastLatLng, final LatLng southwestLatLng);
}
