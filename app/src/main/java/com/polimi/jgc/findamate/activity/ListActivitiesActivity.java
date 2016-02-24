package com.polimi.jgc.findamate.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.exceptions.BackendlessFault;
import com.polimi.jgc.findamate.model.ActivityItem;
import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.util.DefaultCallback;
import com.polimi.jgc.findamate.util.UserSessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class ListActivitiesActivity extends AppCompatActivity implements ActivityItemFragment.OnListFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Context context;
    UserSessionManager session;

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

        if(!session.checkLogin()){
            finish();
        }

        //Obtain data from session
        /**HashMap<String, String> user = session.getUserDetails();
        String name = user.get(Defaults.KEY_NAME);
        String email = user.get(Defaults.KEY_NAME);**/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(context, NewActivity.class), Defaults.ADD_ACTIVITY);
            }
        });

        Snackbar.make(mViewPager, "Welcome "+session.getUserDetails().get(Defaults.KEY_NAME), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!session.checkLogin()){
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Defaults.ADD_ACTIVITY) {
            Bundle bundle = data.getExtras();
            ActivityItem activityItem = new ActivityItem();
            activityItem.setCategory(bundle.getString(Defaults.DETAILS_CATEGORY));
            activityItem.setDescription(bundle.getString(Defaults.DETAILS_DESCRIPTION));
            activityItem.setTitle(bundle.getString(Defaults.DETAILS_TITLE));
            Date d;
            try{
                d = Defaults.SIMPLE_DATE_FORMAT.parse(bundle.getString(Defaults.DETAILS_DATE));
                activityItem.setDate_(d);
            }catch (ParseException e) {
                Log.d("PARSE EXCEPTION", "PARSE EXCEPTION");
            }

            activityItem.setParticipants(bundle.getInt(Defaults.DETAILS_PARTICIPANTS));
            activityItem.setAssistants(bundle.getInt(Defaults.DETAILS_ASSISTANTS));
            activityItem.setCategory(bundle.getString(Defaults.DETAILS_CATEGORY));
            activityItem.setOwnerId(session.getUserDetails().get(Defaults.KEY_EMAIL));
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
        if (id == R.id.action_settings) {
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
        intent.putExtra(Defaults.DETAILS_TITLE, item.getTitle());
        intent.putExtra(Defaults.DETAILS_CATEGORY, item.getCategory());
        intent.putExtra(Defaults.DETAILS_DATE, item.getDateToString(Defaults.DETAILS_DATE));
        intent.putExtra(Defaults.DETAILS_PARTICIPANTS, item.getParticipants());
        intent.putExtra(Defaults.DETAILS_OWNERID, item.getOwnerId());
        intent.putExtra(Defaults.DETAILS_LATITUDE, item.getLatitude());
        intent.putExtra(Defaults.DETAILS_LONGITUDE, item.getLongitude());
        intent.putExtra(Defaults.DETAILS_CREATED, item.getDateToString(Defaults.DETAILS_CREATED));
        intent.putExtra(Defaults.DETAILS_UPDATED, item.getDateToString(Defaults.DETAILS_UPDATED));
        intent.putExtra(Defaults.DETAILS_DESCRIPTION, item.getDescription());
        startActivity(intent);
    }

    private void refreshActivities(){
        mSectionsPagerAdapter.notifyDataSetChanged();
        Snackbar.make(mViewPager, "Activities updated", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return ActivityItemFragment.newInstance(Defaults.ARG_YOUR_INTERESTS);
                case 1:
                    return ActivityItemFragment.newInstance(Defaults.ARG_YOUR_ACTIVITIES);
            }
            return ActivityItemFragment.newInstance(Defaults.ARG_YOUR_INTERESTS);
        }

        @Override
        public int getCount() {
            // Show  total pages.
            return 2;
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
                    return "YOUR ACTIVITIES";
            }
            return null;
        }
    }

}