package com.polimi.jgc.findamate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class DetailsActivity extends ActionBarActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView title;
    private TextView category;
    private TextView date;
    private TextView description;
    private TextView participants;
    private double lat;
    private double lon;
    private String activityTitle;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle=getIntent().getExtras();
        activityTitle=bundle.getString(Defaults.DETAILS_TITLE);
        objectId=bundle.getString(Defaults.OBJECTID);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cds = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(cds).title(activityTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cds));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_activity) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.delete_activity_alerttitle)
                    .setMessage(R.string.delete_activity_alertmessage)
                    .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra(Defaults.OBJECTID, objectId);
                            returnIntent.putExtra(Defaults.DETAILS_TITLE, title.getText().toString());
                            returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            setResult(Defaults.DELETE_ACTIVITY, returnIntent);
                            finish();
                        }

                    })
                    .setNegativeButton(R.string.no_capital, null)
                    .show();
            return true;
        }
        if (id == R.id.edit_activity) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Defaults.OBJECTID, objectId);
            returnIntent.putExtra(Defaults.DETAILS_TITLE, title.getText().toString());
            returnIntent.putExtra(Defaults.DETAILS_CATEGORY, category.getText().toString());
            returnIntent.putExtra(Defaults.DETAILS_DATE, date.getText().toString());
            returnIntent.putExtra(Defaults.DETAILS_DESCRIPTION, description.getText().toString());
            //TODO pasar los parametros de assistants y participants
            //tb updated y created
            returnIntent.putExtra(Defaults.DETAILS_PARTICIPANTS, 8);
            returnIntent.putExtra(Defaults.DETAILS_ASSISTANTS, 6);
            returnIntent.putExtra(Defaults.DETAILS_CATEGORY, category.getText().toString());
            returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            setResult(Defaults.EDIT_ACTIVITY, returnIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        setResult(Defaults.ABORT, returnIntent);
        finish();
    }

}
