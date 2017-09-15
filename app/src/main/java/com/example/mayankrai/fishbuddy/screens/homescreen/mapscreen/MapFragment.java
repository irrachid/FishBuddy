package com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.Scene;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.fragment.BaseFragment;
import com.example.mayankrai.fishbuddy.data.model.Bounds;
import com.example.mayankrai.fishbuddy.databinding.MapFragmentBinding;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager.MapPagerAdapter;
import com.example.mayankrai.fishbuddy.transition.ScaleDownImageTransition;
import com.example.mayankrai.fishbuddy.transition.TransitionUtils;
import com.example.mayankrai.fishbuddy.utility.customview.ClickableViewPager;
import com.example.mayankrai.fishbuddy.utility.maps.DetailsLayout;
import com.example.mayankrai.fishbuddy.utility.maps.MapBitmapCache;
import com.example.mayankrai.fishbuddy.utility.maps.MapOverlayLayout;
import com.example.mayankrai.fishbuddy.utility.maps.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.ArrayList;

public class MapFragment extends BaseFragment<MapFragmentBinding,MapViewModel> implements MapView {

    private Scene detailsScene;
    private String currentTransitionName;

    boolean isLoaded = false, isVisibleToUser = false, isCardClicked = false;

    @SuppressLint("NewApi")
    public static Fragment newInstance(final Context ctx) {
        MapFragment fragment = new MapFragment();
        ScaleDownImageTransition transition = new ScaleDownImageTransition(ctx, MapBitmapCache.instance().getBitmap());
        transition.addTarget(ctx.getString(R.string.mapPlaceholderTransition));
        transition.setDuration(600);
        fragment.setEnterTransition(transition);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isAdded()) {
            viewModel.startLocationListening();
            isLoaded = true;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setAndBindContentView(inflater, container, savedInstanceState, R.layout.fragment_map);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapFragment));
        viewModel.setMapFragment(mapFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isVisibleToUser && (!isLoaded)){
            viewModel.startLocationListening();
            isLoaded=true;
        }
        //viewModel.startLocationListening();
    }

    // Map View Overridden methods

    @SuppressLint("MissingPermission")
    @Override
    public void setMapStyle(GoogleMap googleMap) {
        try {
            googleMap.setMyLocationEnabled(true);
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_json));
            if (!success) {
                // Handle map style load failure
            }
        } catch (Resources.NotFoundException e) {
            // Oops, looks like the map style resource couldn't be found!
        }
    }

    @Override
    public void setMapOverlayLayout(GoogleMap googleMap) {
        binding.mapOverlayLayout.setupMap(googleMap);
    }

    @Override
    public void setMapAndAddMaker(final LatLngBounds latLngBounds, final ArrayList<Place> placeArrayList) {
        binding.mapOverlayLayout.moveCamera(latLngBounds);
        binding.mapOverlayLayout.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                for (int i = 0; i < placeArrayList.size(); i++) {
                    binding.mapOverlayLayout.createAndShowMarker(i, placeArrayList.get(i).getLatLng());
                }
                binding.mapOverlayLayout.setOnCameraIdleListener(null);
            }
        });

        binding.mapOverlayLayout.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                binding.mapOverlayLayout.refresh();
            }
        });
        binding.mapOverlayLayout.setOnMarkerClickListener(new MapOverlayLayout.onMarkerClickListener() {
            @Override
            public void onMarkerClick(int position) {
                System.out.println("MapFragment.onMarkerClick position="+position);
                binding.mapViewPager.setCurrentItem(position);
                binding.mapOverlayLayout.removeCurrentPolyline();
                viewModel.drawRoute(position, false);
            }
        });
    }

    @Override
    public void setViewPager(MapPagerAdapter adapter) {
        System.out.println("MapFragment.setViewPager adapter="+adapter.getCount());
        adapter.setViewPagerId(binding.mapViewPager.getId());
        binding.mapViewPager.setAdapter(adapter);
        binding.mapViewPager.setOffscreenPageLimit(adapter.getCount());
        binding.mapViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.mapOverlayLayout.showMarker(position);
                binding.mapOverlayLayout.removeCurrentPolyline();
                viewModel.drawRoute(position, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.mapViewPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                System.out.println("MapFragment.onItemClick position="+position);
                viewModel.showTransition(position);
                isCardClicked = true;
            }
        });

        binding.mapViewPager.setCurrentItem(1);
        viewModel.drawRoute(1, true);
        binding.mapViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedposition  + 0.5f);
                page.setScaleY(normalizedposition  + 0.5f);
            }
        });
    }

    @Override
    public void drawRoute(final int position) {
        viewModel.drawRoute(position, false);
    }

    @Override
    public void hideAllMarkers() {
        binding.mapOverlayLayout.setOnCameraIdleListener(null);
        binding.mapOverlayLayout.hideAllMarkers();
    }

    @Override
    public void hideUnselectedMarkers(int position) {
        binding.mapOverlayLayout.setOnCameraIdleListener(null);
        binding.mapOverlayLayout.hideUnselectedMarker(position);
    }

    @Override
    public void setDetailScene(int position) {
        currentTransitionName = TransitionUtils.getRecyclerViewTransitionName(position);
        detailsScene = DetailsLayout.showScene(getActivity(), binding.container, viewModel.getAdapterView(position), TransitionUtils.getRecyclerViewTransitionName(position), viewModel.getPlacesList().get(position));
    }



    @Override
    public void drawPolylinesOnMap(final ArrayList<LatLng> polylines, final Bounds bounds) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.mapOverlayLayout.removeCurrentPolyline();
                binding.mapOverlayLayout.addPolyline(polylines);
                binding.mapOverlayLayout.animateCamera(new LatLngBounds( bounds.getSouthwestLatLng(), bounds.getNortheastLatLng()));
            }
        });
    }

    @Override
    public void drawPolylinesOnMap(final ArrayList<LatLng> polylines) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.mapOverlayLayout.removeCurrentPolyline();
                binding.mapOverlayLayout.addPolyline(polylines);
            }
        });
    }

    @Override
    public void updateMapZoomAndRegion(final LatLng northeastLatLng, final LatLng southwestLatLng) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.mapOverlayLayout.animateCamera(new LatLngBounds(southwestLatLng, northeastLatLng));
                binding.mapOverlayLayout.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        //binding.mapOverlayLayout.drawStartAndFinishMarker();
                    }
                });
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        System.out.println("MapFragment.onBackPressed");
        if(isCardClicked){
            int childPosition = TransitionUtils.getItemPositionFromTransition(currentTransitionName);
            DetailsLayout.hideScene(getActivity(), binding.container, viewModel.getAdapterView(childPosition), currentTransitionName);
            notifyLayoutAfterBackPress(childPosition);
            binding.mapOverlayLayout.onBackPressed(viewModel.getLatLang());
            detailsScene = null;
            isCardClicked = !isCardClicked;
            return true;
        }else {
            return false;
        }
    }

    private void notifyLayoutAfterBackPress(final int childPosition) {
        binding.container.removeAllViews();
        binding.container.addView(binding.mapViewPager);
        binding.mapViewPager.requestLayout();
        viewModel.notifyPager(childPosition);
    }
}
