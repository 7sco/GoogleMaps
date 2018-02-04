package com.example.franciscoandrade.mapspractice;

import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    double a, b;
    LatLng newCoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        LatLng nyc = new LatLng(40.7128, -74.0060);
        mMap.addMarker(new MarkerOptions().position(nyc).title("Marker in NYC").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
        UiSettings uiSettings=  mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1020);
        } else {
            mMap.setMyLocationEnabled(true);



            LatLng p1 = null;

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {


                    Log.d("MAAP==", "onMapClick: "+latLng.toString());
                    mMap.clear();
                    a= latLng.latitude;
                    b= latLng.longitude;
                    newCoor = new LatLng(a, b);
                    Geocoder coder = new Geocoder(getApplicationContext());
                    List<Address> address;
                    try {
                        // May throw an IOException
                        address=coder.getFromLocation(a,b, 5);
                        //address = coder.getFromLocationName("3105 Astoria Blvd S, Astoria, NY 11102", 5);
                        if (address != null) {
                            //Address location = address.get(0);
                            //p1 = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(newCoor).title(address.get(0).getAddressLine(0)));
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }



                }
            });

//            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    Log.d("MAAP==", "onMapClick: "+latLng.toString());
//                    mMap.clear();
//                    a= latLng.latitude;
//                    b= latLng.longitude;
//                     newCoor = new LatLng(a, b);
//                    Geocoder coder = new Geocoder(getApplicationContext());
//                    List<Address> address;
//                    try {
//                        // May throw an IOException
//                        address=coder.getFromLocation(a,b, 5);
//                        //address = coder.getFromLocationName("3105 Astoria Blvd S, Astoria, NY 11102", 5);
//                        if (address != null) {
//                            //Address location = address.get(0);
//                            //p1 = new LatLng(location.getLatitude(), location.getLongitude());
//                            mMap.addMarker(new MarkerOptions().position(newCoor).title(address.get(0).getAddressLine(0)));
//                        }
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//
//                   // mMap.addMarker(new MarkerOptions().position(newCoor).title("New"));
//                    //Log.d("LOCATION=", "onMapClick: "+);
//
//                }
//            });


            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                double lat = location.getLatitude();
                                double lng = location.getLongitude();
                                mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker in NYC"));
                            }
                        }
                    });
        }



    }


}
