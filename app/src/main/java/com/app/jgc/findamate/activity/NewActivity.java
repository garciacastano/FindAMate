package com.app.jgc.findamate.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.app.jgc.findamate.R;
import com.app.jgc.findamate.model.Defaults;
import com.app.jgc.findamate.util.CategoryManager;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by JGC on 24/02/2016.
 */
public class NewActivity extends ActionBarActivity {

    //private Button add;
    private EditText title;
    private EditText participants;
    private EditText assistants;
    private EditText date;
    private EditText description;
    private String objectId;
    private boolean isEdit;
    private Spinner category;
    private String session_email;
    private TextView location_info;
    private Button location_button;
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session_email=getIntent().getStringExtra(Defaults.SESSION_EMAIL);

        title = (EditText) findViewById(R.id.newactivity_title);
        category = (Spinner) findViewById(R.id.newactivity_category);
        category.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_item, CategoryManager.CATEGORIES));
        location_info = (TextView) findViewById(R.id.newactivity_location_info);
        assistants = (EditText) findViewById(R.id.newactivity_assistants);
        participants = (EditText) findViewById(R.id.newactivity_participants);
        description = (EditText) findViewById(R.id.newactivity_description);

        location_button = (Button) findViewById(R.id.newactivity_location_button);
        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutocompleteActivity();
            }
        });

        date = (EditText) findViewById(R.id.newactivity_date);
        final Calendar dateCalendar = Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            Calendar currentCalendar = Calendar.getInstance();
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        dateCalendar.set(Calendar.YEAR, year);
                        dateCalendar.set(Calendar.MONTH, monthOfYear);
                        dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        if(dateCalendar.after(currentCalendar)){
                            date.setText(Defaults.SIMPLE_DATE_FORMAT.format(dateCalendar.getTime()));
                        }
                        else{
                            date.setText(Defaults.SIMPLE_DATE_FORMAT.format(currentCalendar.getTime()));
                        }
                    }
                }, dateCalendar.get(Calendar.YEAR), dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //If is editing, the fill the fields with the previous values
        if(getIntent().getIntExtra(Defaults.REQUEST_CODE, Defaults.ABORT)== Defaults.EDIT_ACTIVITY){
            isEdit=true;
            Bundle bundle = getIntent().getExtras();
            title.setText(bundle.getString(Defaults.DETAILS_TITLE));
            category.setSelection(Arrays.asList(CategoryManager.CATEGORIES).indexOf(bundle.get(Defaults.DETAILS_CATEGORY)));
            assistants.setText("" + bundle.getInt(Defaults.DETAILS_ASSISTANTS));
            participants.setText(""+bundle.getInt(Defaults.DETAILS_PARTICIPANTS));
            description.setText(bundle.getString(Defaults.DETAILS_DESCRIPTION));
            date.setText(bundle.getString(Defaults.DETAILS_DATE));
            objectId=bundle.getString(Defaults.OBJECTID);
            lat=bundle.getDouble(Defaults.DETAILS_LATITUDE);
            lon=bundle.getDouble(Defaults.DETAILS_LONGITUDE);
        }else{
            isEdit=false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_activity) {
            if ( title.getText().toString().isEmpty() ||
                    participants.getText().toString().isEmpty() ||
                    assistants.getText().toString().isEmpty() ||
                    description.getText().toString().isEmpty() ||
                    date.getText().toString().isEmpty() ||
                    lat == 0.0 || lon ==0.0 ||
                    category.getSelectedItem().toString().isEmpty()){
                Toast.makeText(this, "There is at least an empty field or location is not set.", Toast.LENGTH_SHORT).show();
                return false;
            }
            int a = Integer.parseInt(assistants.getText().toString());
            int p = Integer.parseInt(participants.getText().toString());
            if (a > 0 && p >= a) {
                Intent returnIntent = new Intent();

                returnIntent.putExtra(Defaults.DETAILS_TITLE, title.getText().toString());
                returnIntent.putExtra(Defaults.DETAILS_ASSISTANTSEMAILS, "");
                returnIntent.putExtra(Defaults.DETAILS_CATEGORY, category.getSelectedItem().toString());
                returnIntent.putExtra(Defaults.DETAILS_DATE, date.getText().toString());
                returnIntent.putExtra(Defaults.DETAILS_DESCRIPTION, description.getText().toString());
                returnIntent.putExtra(Defaults.DETAILS_PARTICIPANTS, p);
                returnIntent.putExtra(Defaults.DETAILS_ASSISTANTS, a);
                returnIntent.putExtra(Defaults.DETAILS_LATITUDE, lat);
                returnIntent.putExtra(Defaults.DETAILS_OWNERID, session_email);
                returnIntent.putExtra(Defaults.DETAILS_LONGITUDE, lon);
                returnIntent.putExtra(Defaults.DETAILS_CATEGORY, category.getSelectedItem().toString());
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if(isEdit){
                    returnIntent.putExtra(Defaults.OBJECTID,objectId);
                    setResult(Defaults.EDIT_ACTIVITY, returnIntent);
                    finish();
                    return true;
                }
                setResult(Defaults.ADD_ACTIVITY, returnIntent);
                finish();
                return true;

            } else {
                Toast.makeText(this, "Player numbers must be higher than zero.", Toast.LENGTH_SHORT);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        setResult(Defaults.ABORT, returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, Defaults.REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            //Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == Defaults.REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                //Log.i(TAG, "Place Selected: " + place.getName());

                // Format the place's details and display them in the TextView.
                location_info.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress()));

                lat=place.getLatLng().latitude;
                lon=place.getLatLng().longitude;
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                //Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address) {
        //Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, id, name, address));

    }
}
