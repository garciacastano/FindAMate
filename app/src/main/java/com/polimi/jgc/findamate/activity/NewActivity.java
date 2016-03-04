package com.polimi.jgc.findamate.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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

import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.model.ActivityItem;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.util.CategoryManager;
import com.polimi.jgc.findamate.util.DefaultCallback;

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session_email=getIntent().getStringExtra(Defaults.SESSION_EMAIL);

        title = (EditText) findViewById(R.id.newactivity_title);
        category = (Spinner) findViewById(R.id.newactivity_category);
        category.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_item, CategoryManager.CATEGORIES));

        assistants = (EditText) findViewById(R.id.newactivity_assistants);
        participants = (EditText) findViewById(R.id.newactivity_participants);
        description = (EditText) findViewById(R.id.newactivity_description);

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
            title.setText(bundle.get(Defaults.DETAILS_TITLE).toString());
            category.setSelection(Arrays.asList(CategoryManager.CATEGORIES).indexOf(bundle.get(Defaults.DETAILS_CATEGORY).toString()));
            assistants.setText(bundle.get(Defaults.DETAILS_ASSISTANTS).toString());
            participants.setText(bundle.get(Defaults.DETAILS_PARTICIPANTS).toString());
            description.setText(bundle.get(Defaults.DETAILS_DESCRIPTION).toString());
            date.setText(bundle.get(Defaults.DETAILS_DATE).toString());
            objectId=bundle.get(Defaults.OBJECTID).toString();
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
                    category.getSelectedItem().toString().isEmpty()){
                Toast.makeText(this, "There is at least an empty field.", Toast.LENGTH_SHORT).show();
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
                returnIntent.putExtra(Defaults.DETAILS_OWNERID, session_email);
                returnIntent.putExtra(Defaults.DETAILS_PARTICIPANTS, p);
                returnIntent.putExtra(Defaults.DETAILS_ASSISTANTS, a);
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
}
