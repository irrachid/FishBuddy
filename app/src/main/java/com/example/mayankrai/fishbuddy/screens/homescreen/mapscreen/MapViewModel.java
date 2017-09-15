package com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen;


import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;

import com.example.mayankrai.fishbuddy.R;
import com.example.mayankrai.fishbuddy.base.uibase.viewmodel.BaseViewModel;
import com.example.mayankrai.fishbuddy.data.model.Bounds;
import com.example.mayankrai.fishbuddy.data.model.DirectionResults;
import com.example.mayankrai.fishbuddy.data.model.Duration;
import com.example.mayankrai.fishbuddy.data.model.NearByApiResponse;
import com.example.mayankrai.fishbuddy.data.model.Route;
import com.example.mayankrai.fishbuddy.data.remote.MapRetrofitApi;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.AppContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerFragment;
import com.example.mayankrai.fishbuddy.location.LocationProvider;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager.MapPagerAdapter;
import com.example.mayankrai.fishbuddy.screens.homescreen.mapscreen.viewpager.MapPlaceFragment;
import com.example.mayankrai.fishbuddy.utility.maps.MapsUtil;
import com.example.mayankrai.fishbuddy.utility.maps.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayankrai on 8/26/17.
 */
@PerFragment
public class MapViewModel extends BaseViewModel<MapView> implements IMapViewModel, OnMapReadyCallback, LocationProvider.LocationCallback {

    @Inject public MapPagerAdapter mapPagerAdapter;
    @Inject @AppContext Context context;
    @Inject MapRetrofitApi mapRetrofitApi;

    @Inject LocationProvider locationProvider;

    private Location location;
    private int PROXIMITY_RADIUS = 3000;
    private GoogleMap googleMap;

    private ArrayList<Place> places = new ArrayList<>();


    @Inject
    public MapViewModel(){}

    public void setMapFragment(SupportMapFragment mapFragment) {
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        getView().setMapStyle(googleMap);
        getView().setMapOverlayLayout(googleMap);
    }

    public void startLocationListening(){
        locationProvider.setCallback(this);
        locationProvider.connect();
    }

    @Override
    public void handleNewLocation(Location location) {
        this.location = location;
        findPlaces("aquarium");
    }

    public void laodData(ArrayList<Place> placesList) {
        mapPagerAdapter.setPlaces(placesList);
        getView().setViewPager(mapPagerAdapter);
    }

    public void drawRoute(final int position,final boolean isZoom) {
        final LatLng end = new LatLng(places.get(position).getLat(), places.get(position).getLng());
        String origin = location.getLatitude() + "," + location.getLongitude();
        String dest = end.latitude + "," + end.longitude;
        mapRetrofitApi.getJson(origin, dest, context.getString(R.string.map_server_key)).enqueue(new Callback<DirectionResults>() {
            @Override
            public void onResponse(Call<DirectionResults> call, Response<DirectionResults> response) {
                try{
                    System.out.println("MapViewModel.onResponse");
                    Route routeA = response.body().getRoutes().get(0);
                    Duration duration = response.body().getRoutes().get(0).getLegs().get(0).getSteps().get(0).getDuration();
                    providePolylineToDraw(routeA.getOverviewPolyLine().getPoints(), routeA.getBounds());
                    if (isZoom){
                        updateMapZoomAndRegion(routeA.getBounds());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DirectionResults> call, Throwable t) {
                Log.d("CallBack", " Throwable is " +t);
            }
        });

    }

    private void updateMapZoomAndRegion(final Bounds bounds) {
        bounds.getSouthwest().setLat(MapsUtil.increaseLatitude(bounds));
        getView().updateMapZoomAndRegion(bounds.getNortheastLatLng(), bounds.getSouthwestLatLng());
    }

    private void providePolylineToDraw(final String points, Bounds bounds) {
        getView().drawPolylinesOnMap(new ArrayList<>(PolyUtil.decode(points)), bounds);
    }

    private void providePolylineToDraw(final String points) {
        getView().drawPolylinesOnMap(new ArrayList<>(PolyUtil.decode(points)));
    }

    public LatLngBounds provideLatLngBoundsForAllPlaces(ArrayList<Place> places) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Place place : places) {
            builder.include(new LatLng(place.getLat(), place.getLng()));
        }
        builder.include(new LatLng(location.getLatitude(),location.getLongitude()));
        return builder.build();
    }

    public ArrayList<Place> getPlacesList() {
        return places;
    }

    public void showTransition(int position) {
        getView().setDetailScene(position);
        getView().hideUnselectedMarkers(position);


        drawRoute(position, true);

    }

    public View getAdapterView(int position) {
        MapPlaceFragment fragment = (MapPlaceFragment) mapPagerAdapter.getFragmentForPosition(position);
        return fragment.getCardView();
    }

    public void getRoute(final LatLng start, final LatLng end) {

    }

    public void findPlaces(String placeType){
        Call<NearByApiResponse> call = mapRetrofitApi.getNearbyPlaces(context.getString(R.string.map_server_key), placeType, location.getLatitude() + "," + location.getLongitude(), PROXIMITY_RADIUS);

        call.enqueue(new Callback<NearByApiResponse>() {
            @Override
            public void onResponse(Call<NearByApiResponse> call, Response<NearByApiResponse> response) {
                System.out.println("MapViewModel.onResponse response="+response);
                try {
                    googleMap.clear();
                    places.clear();
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                        Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                        String placeName = response.body().getResults().get(i).getName();
                        String vicinity = response.body().getResults().get(i).getVicinity();

                        boolean openNow = false;
                        try{
                            openNow = response.body().getResults().get(i).getOpeningHours().getOpenNow();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }

                        Place place= new Place();
                        place.setId(i);
                        place.setName(response.body().getResults().get(i).getName());
                        if(openNow){
                            place.setOpeningHours("Open");
                        }else{
                            place.setOpeningHours("Close");
                        }
                        place.setPrice(4000);
                        place.setDescription(response.body().getResults().get(i).getVicinity());
                        place.setLat(lat);
                        place.setLng(lng);
                        place.setImageUrl(response.body().getResults().get(i).getIcon());

                        //add to list
                        places.add(place);
                    }
                    //set marker to map
                    getView().setMapAndAddMaker(provideLatLngBoundsForAllPlaces(places),places);
                    //set view pager
                    laodData(places);
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NearByApiResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
                t.printStackTrace();
                PROXIMITY_RADIUS += 10000;
            }
        });
    }


    @Override
    public void detach() {
        locationProvider.disconnect();
        super.detach();
    }

    public LatLngBounds getLatLang() {
        return provideLatLngBoundsForAllPlaces(places);
    }

    public void notifyPager(int childPosition) {
        mapPagerAdapter.notifyDataSetChanged();
    }
}
