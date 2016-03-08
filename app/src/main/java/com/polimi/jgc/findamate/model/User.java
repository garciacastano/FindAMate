package com.polimi.jgc.findamate.model;

import android.content.Context;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.polimi.jgc.findamate.activity.ListActivitiesActivity;
import com.polimi.jgc.findamate.util.DefaultCallback;

public class User extends BackendlessUser {

  public static void modifyInterests (final String interests, final Context context){
      String userId = Backendless.UserService.loggedInUser();
      if(userId.equals("")) {
        Toast.makeText(context,
                "Backend cannot retrieve user",
                Toast.LENGTH_LONG).show();
        ListActivitiesActivity.session.logoutUser();
        return;
      }
      BackendlessUser user = Backendless.UserService.findById(userId);
      user.setProperty("interests", interests);
      Backendless.UserService.update(user, new DefaultCallback<BackendlessUser>(context) {
        @Override
        public void handleResponse(BackendlessUser backendlessUser) {
          super.handleResponse(backendlessUser);
          ListActivitiesActivity.session.setInterests(backendlessUser.getProperty("interests").toString());

          Toast.makeText(context,
                  "Your interests were succesfully updated",
                  Toast.LENGTH_LONG).show();
        }
      });
  }

  public String getEmail(){
    return super.getEmail();
  }

  public void setEmail( String email )
  {
    super.setEmail( email );
  }

  public String getPassword()
  {
    return super.getPassword();
  }

  public java.util.Date getDateofbirth(){
    return (java.util.Date) super.getProperty( "dateofbirth" );
  }

  public void setDateofbirth( java.util.Date dateofbirth )
  {
    super.setProperty( "dateofbirth", dateofbirth );
  }

  public String getGender()
  {
    return (String) super.getProperty( "gender" );
  }

  public void setGender( String gender )
  {
    super.setProperty( "gender", gender );
  }

  public String getLogin()
  {
    return (String) super.getProperty( "login" );
  }

  public void setInterests(String interests){
    super.setProperty( "interests", interests );
  }

  public void setLogin( String login )
  {
    super.setProperty( "login", login );
  }

  public String getName()
  {
    return (String) super.getProperty( "name" );
  }

  public void setName( String name )
  {
    super.setProperty( "name", name );
  }


}