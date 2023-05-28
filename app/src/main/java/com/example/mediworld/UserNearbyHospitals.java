package com.example.mediworld;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mediworld.databinding.FragmentUserNearbyHospitalsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;


public class UserNearbyHospitals extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 181;

    private FragmentUserNearbyHospitalsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private GoogleMap mMap;

    private double lat, lng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserNearbyHospitalsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Initialize Places
        Places.initialize(requireContext(), getString(R.string.google_maps_key));

        // Create a new Places client instance
        placesClient = Places.createClient(requireContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(6000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null || locationResult.getLocations().isEmpty()) {
                    Toast.makeText(getContext(), "Location result is not available", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        moveCameraToLocation(lat, lng);
                        fetchNearbyHospitals();
                        fusedLocationProviderClient.removeLocationUpdates(this);
                        break;
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void moveCameraToLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void fetchNearbyHospitals() {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            placesClient.findCurrentPlace(request).addOnSuccessListener((response) -> {
                for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                    if (placeLikelihood.getPlace() != null && placeLikelihood.getPlace().getLatLng() != null) {
                        mMap.addMarker(new MarkerOptions()
                                .position(placeLikelihood.getPlace().getLatLng())
                                .title(placeLikelihood.getPlace().getName()));
                    }
                }
            }).addOnFailureListener((exception) -> {
                Toast.makeText(getContext(), "Failed to fetch nearby hospitals", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}