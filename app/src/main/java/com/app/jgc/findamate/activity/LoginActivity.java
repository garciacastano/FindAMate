package com.app.jgc.findamate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.jgc.findamate.model.Defaults;
import com.app.jgc.findamate.util.DefaultCallback;
import com.app.jgc.findamate.util.SocialCallback;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.app.jgc.findamate.R;
import com.app.jgc.findamate.util.UserSessionManager;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
//import com.facebook.CallbackManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends Activity
{
  private TextView registerLink, restoreLink;
  private EditText identityField, passwordField;
  private Button loginButton;
  private Button facebookButton;
  //private Button googleButton;
  UserSessionManager session;
  //private CallbackManager fb_callbackManager;

  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    session = new UserSessionManager(getApplicationContext());
    initUI();

    Backendless.setUrl(Defaults.SERVER_URL);
    Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);
    //fb_callbackManager = CallbackManager.Factory.create();

    /**Backendless.UserService.isValidLogin( new DefaultCallback<Boolean>( this ){
      @Override
      public void handleResponse( Boolean isValidLogin ){
        if( isValidLogin && Backendless.UserService.CurrentUser() == null && session.getUserDetails().get(Defaults.KEY_USERID) != null){
          String currentUserId = Backendless.UserService.loggedInUser();
          if( !currentUserId.equals( "" ) ){
            Backendless.UserService.findById( currentUserId, new DefaultCallback<BackendlessUser>( LoginActivity.this, "Logging in..." ){
              @Override
              public void handleResponse( BackendlessUser currentUser )
              {
                super.handleResponse(currentUser);
                Backendless.UserService.setCurrentUser(currentUser);
                String currentUserId = Backendless.UserService.loggedInUser();
                if(currentUser.getProperty("interests").equals("null")){
                  session.createUserLoginSession(currentUser.getProperty("name").toString(),currentUser.getProperty("email").toString(),"'SOCCER'",currentUserId);
                }
                else{
                  session.createUserLoginSession(currentUser.getProperty("name").toString(),
                        currentUser.getProperty("email").toString(),currentUser.getProperty("interests").toString(),currentUserId);
                }
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
    });**/
  }

  private void initUI() {
    registerLink = (TextView) findViewById(R.id.registerLink);
    restoreLink = (TextView) findViewById(R.id.restoreLink);
    identityField = (EditText) findViewById(R.id.identityField);
    passwordField = (EditText) findViewById(R.id.passwordField);
    loginButton = (Button) findViewById(R.id.loginButton);
    facebookButton = (Button) findViewById(R.id.loginFacebookButton);
    //googleButton = (Button) findViewById(R.id.loginGoogleButton);

    String tempString = getResources().getString(R.string.register_text);
    SpannableString underlinedContent = new SpannableString(tempString);
    underlinedContent.setSpan(new UnderlineSpan(), 0, tempString.length(), 0);
    registerLink.setText(underlinedContent);
    tempString = getResources().getString(R.string.restore_link);
    underlinedContent = new SpannableString(tempString);
    underlinedContent.setSpan(new UnderlineSpan(), 0, tempString.length(), 0);
    restoreLink.setText(underlinedContent);

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onLoginButtonClicked();
      }
    });

    registerLink.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onRegisterLinkClicked();
      }
    });

    restoreLink.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onRestoreLinkClicked();
      }
    });

    facebookButton.setOnClickListener( new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //onLoginWithFacebookButtonClicked();
      }
    });
  }


  /**googleButton.setOnClickListener( new View.OnClickListener()    {
      @Override
      public void onClick( View view ){
        onLoginWithGoogleButtonClicked();
      }
    });
                                
  }**/

  public void onLoginButtonClicked(){
    final String identity = identityField.getText().toString();
    String password = passwordField.getText().toString();
    boolean rememberLogin = true;

    if(session.getUserDetails().get(Defaults.KEY_USERID) != null){
      Toast.makeText(LoginActivity.this,"id ya existe= "+session.getUserDetails().get(Defaults.KEY_USERID), Toast.LENGTH_SHORT).show();
      return;
    }

    Backendless.UserService.login(identity, password, new DefaultCallback<BackendlessUser>(LoginActivity.this) {
      @Override
      public void handleResponse(BackendlessUser backendlessUser) {
        super.handleResponse(backendlessUser);
        String currentUserId = Backendless.UserService.loggedInUser();
        //String currentUserId = UserIdStorageFactory.instance().getStorage().get();
        //Toast.makeText(LoginActivity.this,"id = "+currentUserId, Toast.LENGTH_SHORT).show();
        if (backendlessUser.getProperty("interests") == null || backendlessUser.getProperty("interests").equals("null")) {
          session.createUserLoginSession(backendlessUser.getProperty("first_name").toString(), backendlessUser.getProperty("email").toString(), "'SOCCER'",currentUserId);
        } else {
          session.createUserLoginSession(backendlessUser.getProperty("first_name").toString(),
                  backendlessUser.getProperty("email").toString(), backendlessUser.getProperty("interests").toString(),currentUserId);
        }
        Intent i = new Intent(LoginActivity.this, ListActivitiesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

      }
    }, rememberLogin);
  }

  public void onRegisterLinkClicked(){
    startActivity( new Intent( this, RegisterActivity.class ) );
    finish();
  }

  public void onRestoreLinkClicked(){
    startActivity(new Intent(this, RestorePasswordActivity.class));
    finish();
  }

  //TODO: login con FB
  /**public void onLoginWithFacebookButtonClicked(){

    Map<String, String> facebookFieldMappings = new HashMap<String, String>();
    facebookFieldMappings.put("email", "email");
    facebookFieldMappings.put("first_name", "first_name");
    facebookFieldMappings.put("last_name", "last_name");
    facebookFieldMappings.put("gender", "gender");

    List<String> permissions = new ArrayList<String>();
    permissions.add( "email" );

    Backendless.UserService.loginWithFacebookSdk( this, facebookFieldMappings, permissions, fb_callbackManager, new AsyncCallback<BackendlessUser>()
    {
      @Override
      public void handleResponse( BackendlessUser backendlessUser )
      {
        if (backendlessUser.getProperty("interests").equals("null")) {
          session.createUserLoginSession(backendlessUser.getProperty("name").toString(), backendlessUser.getProperty("email").toString(), "'SOCCER'", backendlessUser.getUserId());
        } else {
          session.createUserLoginSession(backendlessUser.getProperty("name").toString(),
                  backendlessUser.getProperty("email").toString(), backendlessUser.getProperty("interests").toString(), backendlessUser.getUserId());
        }
        Intent i = new Intent(getBaseContext(), ListActivitiesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
      }

      @Override
      public void handleFault( BackendlessFault fault ){
        Log.i("LOGIN", "FACEBOOK LLOGIN FAILED");
      }
    });

    /**Map<String, String> facebookFieldMappings = new HashMap<String, String>();
    facebookFieldMappings.put("email", "email");
    facebookFieldMappings.put("first_name", "name");
    facebookFieldMappings.put("gender", "gender");

    List<String> permissions = new ArrayList<String>();
    permissions.add("email");
    permissions.add("public_profile");
    Backendless.UserService.loginWithFacebook(LoginActivity.this, null, facebookFieldMappings, permissions, new SocialCallback<BackendlessUser>(LoginActivity.this) {
      @Override
      public void handleResponse(BackendlessUser backendlessUser) {
        if (backendlessUser.getProperty("interests").equals("null")) {
          session.createUserLoginSession(backendlessUser.getProperty("name").toString(), backendlessUser.getProperty("email").toString(), "'SOCCER'",currentUserId);
        } else {
          session.createUserLoginSession(backendlessUser.getProperty("name").toString(),
                  backendlessUser.getProperty("email").toString(), backendlessUser.getProperty("interests").toString(),currentUserId);
        }
        Intent i = new Intent(getBaseContext(), ListActivitiesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
      }
    });
  }**/

  /**@Override
  public void onActivityResult( int requestCode, int resultCode, Intent data )
  {
    super.onActivityResult( requestCode, resultCode, data );
    fb_callbackManager.onActivityResult( requestCode, resultCode, data );
  }
**/
                                
}