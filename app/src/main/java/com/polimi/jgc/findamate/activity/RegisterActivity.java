package com.polimi.jgc.findamate.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.model.User;
import com.polimi.jgc.findamate.util.DefaultCallback;
import com.polimi.jgc.findamate.R;


public class RegisterActivity extends Activity
{
  private EditText dateofbirthField;
  private EditText emailField;
  private EditText genderField;
  private EditText loginField;
  private EditText nameField;
  private EditText passwordField;

  private Button registerButton;

  private java.util.Date dateofbirth;
  private String email;
  private String gender;
  private String login;
  private String name;
  private String password;
  private User user;

  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView( R.layout.register );

    initUI();
  }

  private void initUI(){
    dateofbirthField = (EditText) findViewById( R.id.dateofbirthField );
    emailField = (EditText) findViewById( R.id.emailField );
    genderField = (EditText) findViewById( R.id.genderField );
    loginField = (EditText) findViewById( R.id.loginField );
    nameField = (EditText) findViewById( R.id.nameField );
    passwordField = (EditText) findViewById( R.id.passwordField );

    registerButton = (Button) findViewById( R.id.registerButton );

    final java.util.Calendar dateofbirthCalendar = java.util.Calendar.getInstance();
    dateofbirthField.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        new DatePickerDialog( RegisterActivity.this, new DatePickerDialog.OnDateSetListener()
        {
          @Override
          public void onDateSet( DatePicker datePicker, int year, int monthOfYear, int dayOfMonth )
          {
            dateofbirthCalendar.set( java.util.Calendar.YEAR, year );
            dateofbirthCalendar.set( java.util.Calendar.MONTH, monthOfYear );
            dateofbirthCalendar.set( java.util.Calendar.DAY_OF_MONTH, dayOfMonth );
            dateofbirthField.setText(Defaults.SIMPLE_DATE_FORMAT.format( dateofbirthCalendar.getTime() ) );
          }
        }, dateofbirthCalendar.get( java.util.Calendar.YEAR ), dateofbirthCalendar.get( java.util.Calendar.MONTH ), dateofbirthCalendar.get( java.util.Calendar.DAY_OF_MONTH ) ).show();
      }
    } );

    registerButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRegisterButtonClicked();
      }
    } );
  }

  private void onRegisterButtonClicked(){
    String dateofbirthText = dateofbirthField.getText().toString().trim();
    String emailText = emailField.getText().toString().trim();
    String genderText = genderField.getText().toString().trim();
    String loginText = loginField.getText().toString().trim();
    String nameText = nameField.getText().toString().trim();
    String passwordText = passwordField.getText().toString().trim();

    if ( emailText.isEmpty() ){
      showToast( "Field 'email' cannot be empty." );
      return;
    }

    if ( passwordText.isEmpty() ){
      showToast( "Field 'password' cannot be empty." );
      return;
    }

    if( !dateofbirthText.isEmpty() ){
      try{
        dateofbirth = Defaults.SIMPLE_DATE_FORMAT.parse( dateofbirthText );
      }
      catch( java.text.ParseException e ){
        showToast( e.getMessage() );
        return;
      }
    }

    if( !emailText.isEmpty() ){
      email = emailText;
    }

    if( !genderText.isEmpty() ){
      gender = genderText;
    }

    if( !loginText.isEmpty() ){
      login = loginText;
    }

    if( !nameText.isEmpty() ){
      name = nameText;
    }

    if( !passwordText.isEmpty() ){
      password = passwordText;
    }

    user = new User();

    if( dateofbirth != null ){
      user.setDateofbirth( dateofbirth );
    }

    if( email != null ){
      user.setEmail( email );
    }

    if( gender != null ){
      user.setGender( gender );
    }

    if( login != null ){
      user.setLogin( login );
    }

    if( name != null ){
      user.setName( name );
    }

    if( password != null ){
      user.setPassword( password );
    }

    Backendless.UserService.register( user, new DefaultCallback<BackendlessUser>( RegisterActivity.this )
    {
      @Override
      public void handleResponse( BackendlessUser response ){
        super.handleResponse( response );
        startActivity( new Intent( RegisterActivity.this, RegistrationSuccessActivity.class ) );
        finish();
      }
    } );
  }

  private void showToast( String msg )
  {
    Toast.makeText( this, msg, Toast.LENGTH_SHORT ).show();
  }
}