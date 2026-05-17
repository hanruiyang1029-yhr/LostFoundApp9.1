package com.example.lostfoundapp;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;

    DBHelper dbHelper;

    ArrayList<Item> itemList;

    EditText etRadius;

    Button btnSearchRadius;

    LatLng currentLocation =
            new LatLng(-37.8136, 144.9631);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        etRadius =
                findViewById(R.id.etRadius);

        btnSearchRadius =
                findViewById(R.id.btnSearchRadius);

        dbHelper = new DBHelper(this);

        itemList = dbHelper.getAllItems();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnSearchRadius.setOnClickListener(v -> {

            if (mMap == null) {
                return;
            }

            mMap.clear();

            String radiusText =
                    etRadius.getText().toString();

            double radiusKm;

            if (radiusText.isEmpty()) {

                radiusKm = 99999;

            } else {

                radiusKm =
                        Double.parseDouble(radiusText);
            }

            Geocoder geocoder =
                    new Geocoder(this,
                            Locale.getDefault());

            for (Item item : itemList) {

                try {

                    List<Address> addresses =
                            geocoder.getFromLocationName(
                                    item.getLocation(),
                                    1
                            );

                    if (addresses != null
                            && !addresses.isEmpty()) {

                        double latitude =
                                addresses.get(0).getLatitude();

                        double longitude =
                                addresses.get(0).getLongitude();

                        float[] results =
                                new float[1];

                        Location.distanceBetween(
                                currentLocation.latitude,
                                currentLocation.longitude,
                                latitude,
                                longitude,
                                results
                        );

                        double distanceKm =
                                results[0] / 1000;

                        if (distanceKm <= radiusKm) {

                            LatLng position =
                                    new LatLng(latitude,
                                            longitude);

                            mMap.addMarker(
                                    new MarkerOptions()
                                            .position(position)
                                            .title(item.getName())
                                            .snippet(
                                                    item.getDescription()
                                            )
                            );
                        }
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            mMap.moveCamera(
                    CameraUpdateFactory
                            .newLatLngZoom(
                                    currentLocation,
                                    10
                            )
            );
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(
                                currentLocation,
                                10
                        )
        );
    }
}