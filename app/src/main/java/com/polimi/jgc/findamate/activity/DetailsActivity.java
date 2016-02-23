package com.polimi.jgc.findamate.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.model.Defaults;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView title;
    private TextView category;
    private TextView date;
    private TextView description;
    private TextView participants;
    private double lat;
    private double lon;
    private String activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /**setSupportActionBar(toolbar); //TODO añadir toolbar con UP botton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);**/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle=getIntent().getExtras();
        activityTitle=bundle.getString(Defaults.DETAILS_TITLE);

        title = (TextView) findViewById(R.id.detail_title);
        title.setText(activityTitle);

        category = (TextView) findViewById(R.id.detail_category);
        category.setText(bundle.getString(Defaults.DETAILS_CATEGORY));

        date = (TextView) findViewById(R.id.detail_date);
        date.setText(bundle.getString(Defaults.DETAILS_DATE));

        participants = (TextView) findViewById(R.id.detail_participants);
        participants.setText(String.valueOf(bundle.getInt(Defaults.DETAILS_PARTICIPANTS)));

        /**
         * TODO implement more information in the layout
         *
         * assistants = (TextView) findViewById(R.id.detail_assistants);
         * assistants.setText(String.valueOf(bundle.getInt(Defaults.DETAILS_ASSITANTS)));
         *
         * updated = (TextView) findViewById(R.id.detail_updated);
         * updated.setText(bundle.getString(Defaults.DETAILS_UPDATED));
         *
         * created = (TextView) findViewById(R.id.detail_created);
         * created.setText(bundle.getString(Defaults.DETAILS_CREATED));
         *
        **/

        description = (TextView) findViewById(R.id.detail_description);
        description.setText(bundle.getString(Defaults.DETAILS_DESCRIPTION));

        lat=bundle.getDouble(Defaults.DETAILS_LATITUDE);
        lon=bundle.getDouble(Defaults.DETAILS_LONGITUDE);
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
        LatLng cds = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(cds).title(activityTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cds));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}

