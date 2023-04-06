package com.example.mediworld;

import com.google.android.gms.maps.model.LatLng;

public class Hospital {
        private String name;
        private LatLng location;
    public Hospital(String name, LatLng location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        return location;
    }

}
