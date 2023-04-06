package com.example.mediworld;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class UserNearbyHospitals extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private MarkerOptions userMarkerOptions;

    private List<Hospital> hospitals = new ArrayList<>();
    private static final int MAX_DISTANCE = 15000; // in meters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_nearby_hospitals, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hospitals.add(new Hospital("Mayo Hospital", new LatLng(31.5391, 74.3232)));
        hospitals.add(new Hospital("Jinnah Hospital", new LatLng(31.4788, 74.3057)));
        hospitals.add(new Hospital("Shaukat Khanum Memorial Cancer Hospital", new LatLng(31.5831, 74.3576)));

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check location permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
            return;
        }

        // Get user's current location
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

        // Create custom marker icon for user's current location
        BitmapDescriptor userMarkerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        userMarkerOptions = new MarkerOptions().icon(userMarkerIcon).title("Your location");
    }

    @Override
    public void onLocationChanged(Location location) {
        // Remove previous markers
        mMap.clear();

        // Add a marker for user's current location
        LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(userMarkerOptions.position(currentLoc));

        // Add markers for nearby hospitals
        // Add markers for nearby hospitals
        for (Hospital hospital : hospitals) {
            float[] results = new float[3];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                    hospital.getLocation().latitude, hospital.getLocation().longitude, results);
            float distance = results[0];
            if (distance <= MAX_DISTANCE) {
                LatLng hospitalLoc = new LatLng(hospital.getLocation().latitude, hospital.getLocation().longitude);
                mMap.addMarker(new MarkerOptions().position(hospitalLoc).title(hospital.getName()));
            }
        }

        // Move camera to user's current location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 12.0f));
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Do nothing
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Do nothing
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Do nothing
    }
}

