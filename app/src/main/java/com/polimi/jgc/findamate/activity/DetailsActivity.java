package com.polimi.jgc.findamate.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.polimi.jgc.findamate.util.Assistance;
import com.polimi.jgc.findamate.util.CategoryManager;
import com.polimi.jgc.findamate.util.DefaultCallback;

import java.util.Calendar;

public class DetailsActivity extends ActionBarActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView title;
    private TextView category;
    private TextView date;
    private TextView description;
    private TextView participants;
    private TextView assistantsEmails;
    private TextView assistants;
    private double lat;
    private double lon;
    private String activityTitle;
    private String objectId;
    private Button join;
    private Bundle bundle;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context=this;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bundle=getIntent().getExtras();
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

        assistants = (TextView) findViewById(R.id.detail_assistants);
        assistants.setText(String.valueOf(bundle.getInt(Defaults.DETAILS_ASSISTANTS)));

        description = (TextView) findViewById(R.id.detail_description);
        description.setText(bundle.getString(Defaults.DETAILS_DESCRIPTION));

        assistantsEmails = (TextView) findViewById(R.id.detail_assistantsEmails);
        assistantsEmails.setText(bundle.getString(Defaults.DETAILS_ASSISTANTSEMAILS));

        lat=bundle.getDouble(Defaults.DETAILS_LATITUDE);
        lon=bundle.getDouble(Defaults.DETAILS_LONGITUDE);

        join = (Button) findViewById(R.id.details_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityItem activityItem = ActivityItem.obtainActivityItem(bundle);
                if(!join.getText().toString().equals("Leave")){
                    activityItem.setAssistantEmails(Assistance.addEmail(bundle.getString(Defaults.SESSION_EMAIL),(bundle.getString(Defaults.DETAILS_ASSISTANTSEMAILS))));
                    activityItem.setAssistants(activityItem.getAssistants() + 1);
                    activityItem.setLatitude(lat);
                    activityItem.setLongitude(lon);
                    activityItem.saveAsync(new DefaultCallback<ActivityItem>(context) {
                        @Override
                        public void handleResponse(ActivityItem response) {
                            super.handleResponse(response);
                            Snackbar.make(title, "You joined the activity", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                    });
                }
                else {
                    activityItem.setAssistantEmails(Assistance.removeEmail(bundle.getString(Defaults.SESSION_EMAIL),(bundle.getString(Defaults.DETAILS_ASSISTANTSEMAILS))));
                    activityItem.setAssistants(activityItem.getAssistants() - 1);
                    activityItem.setLatitude(lat);
                    activityItem.setLongitude(lon);
                    activityItem.saveAsync(new DefaultCallback<ActivityItem>(context) {
                        @Override
                        public void handleResponse(ActivityItem response) {
                            super.handleResponse(response);
                            Snackbar.make(title, "You left the activity", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                    });

                }
            }
        });

        if(Assistance.parseString(bundle.getString(Defaults.DETAILS_ASSISTANTSEMAILS)).contains(bundle.getString(Defaults.SESSION_EMAIL))){
            join.setText("Leave");
        }

        if(bundle.getString(Defaults.DETAILS_OWNERID).equals(bundle.get(Defaults.SESSION_EMAIL))){
            join.setVisibility(View.INVISIBLE);
        }
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
        if(bundle.get(Defaults.DETAILS_OWNERID).toString().equals(bundle.get(Defaults.SESSION_EMAIL).toString())){
            getMenuInflater().inflate(R.menu.menu_details, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

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
            returnIntent.putExtra(Defaults.DETAILS_PARTICIPANTS, Integer.parseInt(participants.getText().toString()));
            returnIntent.putExtra(Defaults.SESSION_EMAIL, bundle.getString(Defaults.SESSION_EMAIL));
            returnIntent.putExtra(Defaults.DETAILS_OWNERID, bundle.getString(Defaults.SESSION_EMAIL));
            returnIntent.putExtra(Defaults.DETAILS_ASSISTANTS, Integer.parseInt(assistants.getText().toString()));
            returnIntent.putExtra(Defaults.DETAILS_LATITUDE, lat);
            returnIntent.putExtra(Defaults.DETAILS_LONGITUDE, lon);
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
        Log.d("BACK","BACK");
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        setResult(Defaults.ABORT, returnIntent);
        finish();
    }

}
