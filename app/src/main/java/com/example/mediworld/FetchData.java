package com.example.mediworld;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FetchData extends AsyncTask<String, Void, String> {

    private GoogleMap googleMap;

    public void setOnFetchDataListener(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.next());
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject getLocation = jsonObject1.getJSONObject("geometry").getJSONObject("location");
                    String lat = getLocation.getString("lat");
                    String lng = getLocation.getString("lng");

                    String name = jsonObject1.getString("name");
                    LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.title(name);
                    markerOptions.position(latLng);
                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}