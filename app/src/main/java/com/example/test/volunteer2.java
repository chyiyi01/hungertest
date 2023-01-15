package com.example.test;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class volunteer2 extends FragmentActivity implements OnMapReadyCallback{
    GoogleMap map;
    List<String> myList;
    //To store address that we will receive from donor
    String addForMap;
    //p1 object will store converted latitude and longitude values of donor's address
    LatLng p1;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteertwo);
        db = new DatabaseHelper(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        //calling database function to get the donation details
        myList = db.displayDforV();

        addForMap=myList.get(2).toLowerCase();
        Log.d("data", myList.toString());
        //Function that will return latitude and longitude values from address that is in string
        p1= getLocationFromAddress(addForMap);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(p1).title("Donor's Location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(p1));
        map.moveCamera(CameraUpdateFactory.newLatLng(p1));

        //CameraPosition is a class that aggregates all camera position parameters and build() to construct a camera position instance and animateCamera is used to modify map's camera
        CameraPosition cameraPosition = new CameraPosition.Builder().target(p1).zoom(15).build();
        //Zoom in and animate the camera.
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
    public void goToV1(View v17){
        Intent i6 = new Intent(this, volunteer1.class);
        startActivity(i6);
    }

    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }
}