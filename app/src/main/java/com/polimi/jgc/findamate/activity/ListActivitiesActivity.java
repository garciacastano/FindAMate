package com.polimi.jgc.findamate.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.polimi.jgc.findamate.model.ActivityItem;
import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.model.User;
import com.polimi.jgc.findamate.util.Assistance;
import com.polimi.jgc.findamate.util.CategoryManager;
import com.polimi.jgc.findamate.util.DefaultCallback;
import com.polimi.jgc.findamate.util.UserSessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ListActivitiesActivity extends ActionBarActivity implements ActivityItemFragment.OnListFragmentInteractionListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Context context;
    public static UserSessionManager session;
    protected LocationManager mLocationManager;
    private GoogleApiClient mGoogleApiClient;
    private Double myLat;
    private Double myLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity_activity);

        context=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        session = new UserSessionManager(getApplicationContext());

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NewActivity.class);
                i.putExtra(Defaults.SESSION_EMAIL, session.getUserDetails().get(Defaults.KEY_EMAIL));
                startActivityForResult(i, Defaults.ADD_ACTIVITY);
            }
        });
    }
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(!session.checkLogin()){
            finish();
        }
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Add an activity
        if (requestCode == Defaults.ADD_ACTIVITY && resultCode == Defaults.ADD_ACTIVITY ) {
            ActivityItem activityItem = ActivityItem.obtainActivityItem(data.getExtras());
            activityItem.saveAsync(new DefaultCallback<ActivityItem>(this) {

                @Override
                public void handleResponse(ActivityItem response) {
                    super.handleResponse(response);
                    refreshActivities();
                    Snackbar.make(mViewPager, "The activity '"+response.getTitle()+"' was succesfully published", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        //Delete an activity
        if (requestCode == Defaults.SEE_DETAILS && resultCode == Defaults.DELETE_ACTIVITY ) {
            final ActivityItem activityItem = new ActivityItem();
            activityItem.setObjectId(data.getExtras().getString(Defaults.OBJECTID));
            activityItem.setTitle(data.getExtras().getString(Defaults.DETAILS_TITLE));
            activityItem.removeAsync(new DefaultCallback<Long>(this) {

                @Override
                public void handleResponse(Long response) {
                    super.handleResponse(response);
                    refreshActivities();
                    Snackbar.make(mViewPager, "The activity '" + activityItem.getTitle() + "' was succesfully deleted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        //Start intent to update activity
        if (requestCode == Defaults.SEE_DETAILS && resultCode == Defaults.EDIT_ACTIVITY ) {
            //TODO: crear nueva UI para editar actividades
            ActivityItem activityItem = ActivityItem.obtainActivityItem(data.getExtras());
            Intent i = new Intent(context, NewActivity.class);
            i.putExtra(Defaults.OBJECTID, activityItem.getObjectId());
            i.putExtra(Defaults.DETAILS_TITLE,activityItem.getTitle());
            i.putExtra(Defaults.DETAILS_CATEGORY,activityItem.getCategory());
            i.putExtra(Defaults.DETAILS_DATE,activityItem.getDateToString(Defaults.DETAILS_DATE));
            i.putExtra(Defaults.DETAILS_DESCRIPTION,activityItem.getDescription());
            i.putExtra(Defaults.DETAILS_PARTICIPANTS,activityItem.getParticipants());
            i.putExtra(Defaults.DETAILS_LONGITUDE,activityItem.getLongitude());
            i.putExtra(Defaults.DETAILS_LATITUDE,activityItem.getLatitude());
            i.putExtra(Defaults.DETAILS_ASSISTANTS, activityItem.getAssistants());
            i.putExtra(Defaults.DETAILS_CATEGORY, activityItem.getCategory());
            i.putExtra(Defaults.DETAILS_OWNERID, activityItem.getOwnerId());
            i.putExtra(Defaults.SESSION_EMAIL, session.getUserDetails().get(Defaults.KEY_EMAIL));
            startActivityForResult(i, Defaults.EDIT_ACTIVITY);
        }

        //Update an activity
        if (requestCode == Defaults.EDIT_ACTIVITY && resultCode == Defaults.EDIT_ACTIVITY ) {
            ActivityItem activityItem = ActivityItem.obtainActivityItem(data.getExtras());
            //TODO: Aqui ownerId es null
            activityItem.saveAsync(new DefaultCallback<ActivityItem>(this) {
                @Override
                public void handleResponse(ActivityItem response) {
                    super.handleResponse(response);
                    refreshActivities();
                    Snackbar.make(mViewPager, "The activity '" + response.getTitle() + "' was succesfully updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        //Change interests
        if (requestCode == Defaults.CHANGE_INTERESTS && resultCode == Defaults.CHANGE_INTERESTS ) {
            User.modifyInterests(data.getExtras().getString(Defaults.KEY_INTERESTS_FORMATED), context);
            refreshActivities();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh_activities) {
            refreshActivities();
            return true;
        }
        if (id == R.id.action_changeinterests) {
            Intent i = new Intent(this,InterestsActivity.class);
            i.putExtra(Defaults.KEY_INTERESTS_FORMATED, session.getUserDetails().get(Defaults.KEY_INTERESTS_FORMATED));
            startActivityForResult(i, Defaults.CHANGE_INTERESTS);
            return true;
        }
        if (id == R.id.logout) {
            session.logoutUser();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivitySelected(ActivityItem item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Defaults.OBJECTID, item.getObjectId());
        intent.putExtra(Defaults.DETAILS_TITLE, item.getTitle());
        intent.putExtra(Defaults.DETAILS_CATEGORY, item.getCategory());
        intent.putExtra(Defaults.DETAILS_DATE, item.getDateToString(Defaults.DETAILS_DATE));
        intent.putExtra(Defaults.DETAILS_PARTICIPANTS, item.getParticipants());
        intent.putExtra(Defaults.DETAILS_ASSISTANTS, item.getAssistants());
        intent.putExtra(Defaults.DETAILS_ASSISTANTSEMAILS, item.getAssistantEmails());
        intent.putExtra(Defaults.DETAILS_OWNERID, item.getOwnerId());
        intent.putExtra(Defaults.DETAILS_LATITUDE, item.getLatitude());
        intent.putExtra(Defaults.DETAILS_LONGITUDE, item.getLongitude());
        intent.putExtra(Defaults.DETAILS_CREATED, item.getDateToString(Defaults.DETAILS_CREATED));
        intent.putExtra(Defaults.DETAILS_UPDATED, item.getDateToString(Defaults.DETAILS_UPDATED));
        intent.putExtra(Defaults.SESSION_EMAIL, session.getUserDetails().get(Defaults.KEY_EMAIL));
        intent.putExtra(Defaults.DETAILS_DESCRIPTION, item.getDescription());
        startActivityForResult(intent, Defaults.SEE_DETAILS);
    }

    private boolean checkValidLocation(){
        if (myLat == null || myLon == null){
            Toast.makeText(ListActivitiesActivity.this,
                    "You are not located yet",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void refreshActivities(){
        mSectionsPagerAdapter.notifyDataSetChanged();
        Snackbar.make(mViewPager, "Activities updated", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra(Defaults.REQUEST_CODE, requestCode);
        super.startActivityForResult(intent, requestCode);
    }


    //LOCATION
    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest mLocationRequest = createLocationRequest();
        int permissionCheck = ContextCompat.checkSelfPermission(ListActivitiesActivity.this, Manifest.permission.WRITE_CALENDAR);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String message = String.format(
                        "Location refreshed \n Longitude: %1$s \n Latitude: %2$s",
                        location.getLongitude(), location.getLatitude()
                );
                Toast.makeText(ListActivitiesActivity.this, message, Toast.LENGTH_LONG).show();
                myLat = location.getLatitude();
                myLon = location.getLongitude();
                session.setLocation(myLat,myLon);
            }

            public void onStatusChanged(String s, int i, Bundle b) {
                Toast.makeText(ListActivitiesActivity.this, "Provider status changed",
                        Toast.LENGTH_LONG).show();
            }

            public void onProviderDisabled(String s) {
                Toast.makeText(ListActivitiesActivity.this,
                        "Provider disabled by the user. GPS turned off",
                        Toast.LENGTH_LONG).show();
            }

            public void onProviderEnabled(String s) {
                Toast.makeText(ListActivitiesActivity.this,
                        "Provider enabled by the user. GPS turned on",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onConnectionFailed(ConnectionResult connectionResult){

    }
    @Override
    public void onConnectionSuspended(int cause) {
        Log.d("GPS", "Connection to Google API suspended");
    }

    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000*30);
        mLocationRequest.setFastestInterval(1000 * 120);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }


    //FRAGMENT PAGER ADAPTER
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragmentTags = new HashMap<Integer, String>();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return ActivityItemFragment.newInstance(Defaults.ARG_YOUR_INTERESTS, session.getUserDetails());
                case 1:
                    return ActivityItemFragment.newInstance(Defaults.ARG_CREATED_ACTIVITIES, session.getUserDetails());
            }
            return ActivityItemFragment.newInstance(Defaults.ARG_YOUR_INTERESTS, session.getUserDetails());
        }

        @Override
        public int getCount() {
            // Show  total pages.
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                // record the fragment tag here.
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }

        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return mFragmentManager.findFragmentByTag(tag);
        }

        @Override
        public int getItemPosition(Object item){
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "YOUR INTERESTS";
                case 1:
                    return "CREATED";
            }
            return null;
        }
    }

}