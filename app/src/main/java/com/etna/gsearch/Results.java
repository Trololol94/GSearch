package com.etna.gsearch;

/**
 * Created by Bastien Chevallier on 4/28/2016.
 */
public class Results {
    private String name;
    private String address;
    private String icon;
    private double rating;
    private String placeId;

    public Results(String name, String address, String icon, double rating, String placeId) {
        super();
        this.name = name;
        this.address = address;
        this.icon = icon;
        this.rating = rating;
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {
        return rating;
    }

    public String getPlaceId() {
        return placeId;
    }
}
