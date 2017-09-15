package com.example.mayankrai.fishbuddy.location;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityContext;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

/**
 * Created by benjakuben on 12/17/14.
 */
public class LocationProvider implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public interface LocationCallback {
        void handleNewLocation(Location location);
    }

    private static final String TAG = LocationProvider.class.getSimpleName();

    private static int LOCATION_GPS_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    private static int LOCATION_GPS_INTERVAL_MILLIS = 60000;//60 seg
    private static int LOCATION_GPS_FASTEST_INTERVAL_MILLIS = 30000;//30seg
    private static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationCallback mLocationCallback;

    private Context mContext;

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    @Inject
    public LocationProvider(@ActivityContext Context context, RxPermissions rxPermissions) {

        mContext = context;

        try{
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }catch (Exception e){
            e.printStackTrace();
        }

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LOCATION_GPS_PRIORITY)
                .setInterval(LOCATION_GPS_INTERVAL_MILLIS)        // 10 seconds, in milliseconds
                .setFastestInterval(LOCATION_GPS_FASTEST_INTERVAL_MILLIS);


    }

    public void setCallback(LocationCallback callback){
        mLocationCallback = callback;
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        System.out.println("LocationProvider.onConnected location="+location);

        if (location != null) {
            mLocationCallback.handleNewLocation(location);
        }
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution() && mContext instanceof Activity) {
            try {
                Activity activity = (Activity)mContext;
                connectionResult.startResolutionForResult(activity, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        System.out.println("LocationProvider.onLocationChanged");
        mLocationCallback.handleNewLocation(location);
    }

    public GoogleApiClient getmGoogleApiClient(){
        return mGoogleApiClient;
    }
}
