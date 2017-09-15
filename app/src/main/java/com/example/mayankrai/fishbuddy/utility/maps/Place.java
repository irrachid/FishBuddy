package com.example.mayankrai.fishbuddy.utility.maps;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mayankrai.fishbuddy.data.model.Duration;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("opening_hours")
    private String openingHours;
    @SerializedName("price")
    private int price;
    @SerializedName("description")
    private String description;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    @SerializedName("duration") String duration;
    @SerializedName("distance") String distance;
    @SerializedName("imageUrl") String imageUrl;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(final String openingHours) {
        this.openingHours = openingHours;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(final double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(final double lng) {
        this.lng = lng;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String photo) {
        this.imageUrl = photo;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.openingHours);
        dest.writeInt(this.price);
        dest.writeString(this.description);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeString(this.duration);
        dest.writeString(this.distance);
        dest.writeString(this.imageUrl);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.openingHours = in.readString();
        this.price = in.readInt();
        this.description = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.duration = in.readString();
        this.distance = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
