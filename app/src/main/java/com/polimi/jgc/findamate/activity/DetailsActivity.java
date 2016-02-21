package com.polimi.jgc.findamate.activity;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.model.ActivityItem;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.util.ActivityDownloader;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView title;
    private TextView category;
    private TextView date;
    private TextView description;
    private TextView participants;
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityDownloader activityDownloader=new ActivityDownloader();
        ActivityItem activityItem= activityDownloader.getActivityDetails(getIntent().getStringExtra(Defaults.ACTIVITY_ID));

        title = (TextView) findViewById(R.id.detail_title);
        title.setText(activityItem.getTitle());
        category = (TextView) findViewById(R.id.detail_category);
        category.setText(activityItem.getCategory());
        date = (TextView) findViewById(R.id.detail_date);
        date.setText(activityItem.getDayMonthYear());
        participants = (TextView) findViewById(R.id.detail_participants);
        participants.setText(String.valueOf(activityItem.getParticipants()));
        description = (TextView) findViewById(R.id.detail_description);
        description.setText(activityItem.getDescription());
        lat=activityItem.getLatitude();
        lon=activityItem.getLongitude();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng cds = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(cds).title("Your activity"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cds));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}
