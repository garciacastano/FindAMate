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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.model.ActivityItem;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.util.DefaultCallback;

import java.util.Calendar;

/**
 * Created by JGC on 24/02/2016.
 */
public class NewActivity extends ActionBarActivity {

    //private Button add;
    private EditText title;
    private EditText category;
    private EditText currentPlayers;
    private EditText playersNeeded;
    private EditText date;
    private EditText description;
    private String objectId;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.newactivity_title);
        category = (EditText) findViewById(R.id.newactivity_category);
        currentPlayers = (EditText) findViewById(R.id.newactivity_currentplayers);
        playersNeeded = (EditText) findViewById(R.id.newactivity_playersneeded);
        description = (EditText) findViewById(R.id.newactivity_description);

        date = (EditText) findViewById(R.id.newactivity_date);
        date.setOnClickListener(new View.OnClickListener() {
            Calendar currentCalendar = Calendar.getInstance();
            Calendar dateCalendar = Calendar.getInstance();
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
            category.setText(bundle.get(Defaults.DETAILS_CATEGORY).toString());
            int assistants = Integer.parseInt(bundle.get(Defaults.DETAILS_ASSISTANTS).toString());
            //int participants = Integer.parseInt(bundle.get(Defaults.DETAILS_PARTICIPANTS).toString());
            int needed = Integer.parseInt(bundle.get(Defaults.DETAILS_PARTICIPANTS).toString()) - assistants;
            currentPlayers.setText(""+assistants);
            playersNeeded.setText(""+needed);
            description.setText(bundle.get(Defaults.DETAILS_DESCRIPTION).toString());
            date.setText(bundle.get(Defaults.DETAILS_DATE).toString());
            objectId=bundle.get(Defaults.OBJECTID).toString();
        }else{
            isEdit=false;
        }


        /**add = (Button) findViewById(R.id.newactivity_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current =Integer.parseInt(currentPlayers.getText().toString());
                int needed =Integer.parseInt(playersNeeded.getText().toString());
                if(title.getText()!=null &&
                    category.getText()!=null &&
                    description.getText()!=null &&
                    needed > 0 &&
                    current >0 &&
                    category.getText()!=null &&
                    date.getText() != null){
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(Defaults.DETAILS_TITLE,title.getText().toString());
                        returnIntent.putExtra(Defaults.DETAILS_CATEGORY,category.getText().toString());
                        returnIntent.putExtra(Defaults.DETAILS_DATE,date.getText().toString());
                        returnIntent.putExtra(Defaults.DETAILS_DESCRIPTION,description.getText().toString());
                        returnIntent.putExtra(Defaults.DETAILS_PARTICIPANTS,needed+current);
                        returnIntent.putExtra(Defaults.DETAILS_ASSISTANTS,current);
                        returnIntent.putExtra(Defaults.DETAILS_CATEGORY,category.getText().toString());
                        setResult(Defaults.ADD_ACTIVITY,returnIntent);
                        finish();
                }
                else{
                    Log.d("FALLO EN ADD ACTIVITY", "Se han rellenado mal los campos");
                }
            }
        });**/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_activity) {
            int current = Integer.parseInt(currentPlayers.getText().toString());
            int needed = Integer.parseInt(playersNeeded.getText().toString());
            if (title.getText() != null &&
                    category.getText() != null &&
                    description.getText() != null &&
                    needed > 0 &&
                    current > 0 &&
                    category.getText() != null &&
                    date.getText() != null) {
                Intent returnIntent = new Intent();

                returnIntent.putExtra(Defaults.DETAILS_TITLE, title.getText().toString());
                returnIntent.putExtra(Defaults.DETAILS_CATEGORY, category.getText().toString());
                returnIntent.putExtra(Defaults.DETAILS_DATE, date.getText().toString());
                returnIntent.putExtra(Defaults.DETAILS_DESCRIPTION, description.getText().toString());
                returnIntent.putExtra(Defaults.DETAILS_PARTICIPANTS, needed + current);
                returnIntent.putExtra(Defaults.DETAILS_ASSISTANTS, current);
                returnIntent.putExtra(Defaults.DETAILS_CATEGORY, category.getText().toString());
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
                Log.d("FALLO EN SAVE ACTIVITY", "Se han rellenado mal los campos");
            }

            return super.onOptionsItemSelected(item);
        }
        return true;
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
