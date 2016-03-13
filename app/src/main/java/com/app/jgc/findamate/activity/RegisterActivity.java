package com.app.jgc.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.app.jgc.findamate.model.User;
import com.app.jgc.findamate.util.DefaultCallback;
import com.app.jgc.findamate.R;

import java.util.ArrayList;


public class RegisterActivity extends Activity {
  private EditText emailField;
  private Spinner genderField;
  private EditText loginField;
  private EditText nameField;
  private EditText passwordField;
  private EditText repeatPasswordField;
  private Button registerButton;
  private String email;
  private String gender;
  private String name;
  private String password;
  private User user;

  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView(R.layout.register);

    initUI();
  }

  private void initUI() {
    emailField = (EditText) findViewById(R.id.emailField);
    genderField = (Spinner) findViewById(R.id.genderField);
    ArrayList<String> gender = new ArrayList<>();
    gender.add("Male");
    gender.add("Female");
    genderField.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_item, gender));
    loginField = (EditText) findViewById(R.id.loginField);
    nameField = (EditText) findViewById(R.id.nameField);
    passwordField = (EditText) findViewById(R.id.passwordField);
    repeatPasswordField = (EditText) findViewById(R.id.repeatPasswordField);
    registerButton = (Button) findViewById(R.id.registerButton);

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onRegisterButtonClicked();
      }
    });
  }

  private void onRegisterButtonClicked(){
    String emailText = emailField.getText().toString().trim();
    String genderText = genderField.getSelectedItem().toString().trim();
    String nameText = nameField.getText().toString().trim();
    String passwordText = passwordField.getText().toString().trim();
    String repeatPasswordText = repeatPasswordField.getText().toString().trim();

    if ( emailText.isEmpty() || nameText.isEmpty() || passwordText.isEmpty() || repeatPasswordText.isEmpty() || genderText.isEmpty()){
      showToast( "There is at least an empty field." );
      return;
    }

    if ( !passwordText.equals(repeatPasswordText) ){
      showToast( "Passwords do not match" );
      return;
    }

    if( !emailText.isEmpty() ){
      email = emailText;
    }

    if( !genderText.isEmpty() ){
      gender = genderText;
    }

    if( !nameText.isEmpty() ){
      name = nameText;
    }

    if( !passwordText.isEmpty() ){
      password = passwordText;
    }

    user = new User();

    if( email != null ){
      user.setEmail( email );
    }

    if( gender != null ){
      user.setGender( gender );
    }

    if( name != null ){
      user.setName( name );
    }

    if( password != null ){
      user.setPassword( password );
    }
    user.setInterests("'SOCCER'");

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