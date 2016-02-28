package com.polimi.jgc.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.util.DefaultCallback;
import com.polimi.jgc.findamate.util.SocialCallback;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.util.UserSessionManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends Activity
{
  private TextView registerLink, restoreLink;
  private EditText identityField, passwordField;
  private Button loginButton;
  private CheckBox rememberLoginBox;
  private Button facebookButton;
  private Button twitterButton;
  private Button googleButton;
  UserSessionManager session;

  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView(R.layout.login);
    session = new UserSessionManager(getApplicationContext());
    initUI();

    Backendless.setUrl( Defaults.SERVER_URL );
    Backendless.initApp( this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION );

    Backendless.UserService.isValidLogin( new DefaultCallback<Boolean>( this )
    {
      @Override
      public void handleResponse( Boolean isValidLogin )
      {
        if( isValidLogin && Backendless.UserService.CurrentUser() == null )
        {
          String currentUserId = Backendless.UserService.loggedInUser();

          if( !currentUserId.equals( "" ) )
          {
            Backendless.UserService.findById( currentUserId, new DefaultCallback<BackendlessUser>( LoginActivity.this, "Logging in..." )
            {
              @Override
              public void handleResponse( BackendlessUser currentUser )
              {
                super.handleResponse(currentUser);
                Backendless.UserService.setCurrentUser(currentUser);
                session.createUserLoginSession(currentUser.getProperty("name").toString(),
                        currentUser.getProperty("email").toString(),"");
                Intent i = new Intent( getBaseContext(), ListActivitiesActivity.class );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

              }
            } );
          }
        }

        super.handleResponse( isValidLogin );
      }
    });
  }

  private void initUI(){
    registerLink = (TextView) findViewById( R.id.registerLink );
    restoreLink = (TextView) findViewById( R.id.restoreLink );
    identityField = (EditText) findViewById( R.id.identityField );
    passwordField = (EditText) findViewById( R.id.passwordField );
    loginButton = (Button) findViewById( R.id.loginButton );
    rememberLoginBox = (CheckBox) findViewById( R.id.rememberLoginBox );
    facebookButton = (Button) findViewById( R.id.loginFacebookButton );
    twitterButton = (Button) findViewById( R.id.loginTwitterButton );
    googleButton = (Button) findViewById(R.id.loginGoogleButton);

    String tempString = getResources().getString( R.string.register_text );
    SpannableString underlinedContent = new SpannableString( tempString );
    underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
    registerLink.setText( underlinedContent );
    tempString = getResources().getString( R.string.restore_link );
    underlinedContent = new SpannableString( tempString );
    underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
    restoreLink.setText( underlinedContent );

    loginButton.setOnClickListener( new View.OnClickListener(){
      @Override
      public void onClick( View view ){
        onLoginButtonClicked();
      }
     });

    registerLink.setOnClickListener( new View.OnClickListener(){
      @Override
      public void onClick( View view ){
        onRegisterLinkClicked();
      }
     });

    restoreLink.setOnClickListener( new View.OnClickListener(){
      @Override
      public void onClick( View view ){
        onRestoreLinkClicked();
      }
     });

    facebookButton.setOnClickListener( new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onLoginWithFacebookButtonClicked();
      }
    });

    twitterButton.setOnClickListener( new View.OnClickListener(){
      @Override
      public void onClick( View view ){
        onLoginWithTwitterButtonClicked();
      }
     });

    googleButton.setOnClickListener( new View.OnClickListener()    {
      @Override
      public void onClick( View view ){
        onLoginWithGoogleButtonClicked();
      }
    });
                                
  }

  public void onLoginButtonClicked(){
    final String identity = identityField.getText().toString();
    String password = passwordField.getText().toString();
    boolean rememberLogin = rememberLoginBox.isChecked();

    Backendless.UserService.login( identity, password, new DefaultCallback<BackendlessUser>( LoginActivity.this ){
      @Override
      public void handleResponse( BackendlessUser backendlessUser ){
        super.handleResponse(backendlessUser);
        session.createUserLoginSession(backendlessUser.getProperty("name").toString(),identity,"");
        Intent i = new Intent( LoginActivity.this, ListActivitiesActivity.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
      }
    }, rememberLogin );
  }

  public void onRegisterLinkClicked(){
    startActivity( new Intent( this, RegisterActivity.class ) );
    finish();
  }

  public void onRestoreLinkClicked(){
    startActivity( new Intent( this, RestorePasswordActivity.class ) );
    finish();
  }

  public void onLoginWithFacebookButtonClicked(){

    //TODO obtener nombre del usuario de fb, twitter y google+

    Map<String, String> facebookFieldMappings = new HashMap<String, String>();
    facebookFieldMappings.put( "email", "fb_email" );

    List<String> permissions = new ArrayList<String>();
    permissions.add( "email" );
    Backendless.UserService.loginWithFacebook( LoginActivity.this, new SocialCallback<BackendlessUser>( LoginActivity.this ){
      @Override
      public void handleResponse( BackendlessUser backendlessUser ){
        session.createUserLoginSession(backendlessUser.getProperty("name").toString(),
                backendlessUser.getProperty("email").toString(),"");
        Intent i = new Intent( getBaseContext(), ListActivitiesActivity.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
      }
    } );
  }

  public void onLoginWithTwitterButtonClicked(){
    Backendless.UserService.loginWithTwitter( LoginActivity.this, new SocialCallback<BackendlessUser>( LoginActivity.this ){
      @Override
      public void handleResponse( BackendlessUser backendlessUser ){
        session.createUserLoginSession(backendlessUser.getProperty("name").toString(),
                backendlessUser.getProperty("email").toString(),"");
        Intent i = new Intent( getBaseContext(), ListActivitiesActivity.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
      }
    } );
  }

  public void onLoginWithGoogleButtonClicked(){
    Backendless.UserService.loginWithGooglePlus( LoginActivity.this, new SocialCallback<BackendlessUser>( LoginActivity.this ){
      @Override
      public void handleResponse( BackendlessUser backendlessUser ){
        session.createUserLoginSession(backendlessUser.getProperty("name").toString(),
                backendlessUser.getProperty("email").toString(),"");
        Intent i = new Intent( getBaseContext(), ListActivitiesActivity.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
      }
    });
  }
                                
}