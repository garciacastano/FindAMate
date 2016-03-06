package com.polimi.jgc.findamate.model;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class User extends BackendlessUser
{
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