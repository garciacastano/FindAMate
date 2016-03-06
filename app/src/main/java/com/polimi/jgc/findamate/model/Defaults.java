package com.polimi.jgc.findamate.model;

public class Defaults {
  //Backendless constants
  public static final String APPLICATION_ID = "E26DA9BB-7E36-1AC7-FF12-6D7227BD4700";
  public static final String SECRET_KEY = "A668187A-4CD0-4F92-FF03-A42E36455000";
  public static final String VERSION = "v1";
  public static final String SERVER_URL = "https://api.backendless.com";

  //Activities display constants
  public static final String ARG_YOUR_INTERESTS = "your_interests";
  public static final String ARG_YOUR_ACTIVITIES ="your_activities";
  public static final String ARG_ACTIVITY_MODE = "activity_mode";

  //Details activity constants
  public static final String DETAILS_TITLE ="details_title";
  public static final String DETAILS_CATEGORY="details_category";
  public static final String DETAILS_DATE ="details_date";
  public static final String DETAILS_DESCRIPTION ="details_description";
  public static final String DETAILS_LATITUDE ="details_latitude";
  public static final String DETAILS_LONGITUDE ="details_longitude";
  public static final String DETAILS_PARTICIPANTS = "details_participants";
  public static final String DETAILS_ASSISTANTS = "details_assistants";
  public static final String DETAILS_ASSISTANTSEMAILS ="details_assistantsemails";
  public static final String DETAILS_OWNERID = "details_ownerid";
  public static final String DETAILS_CREATED = "details_created";
  public static final String DETAILS_UPDATED = "details_updates";
  public static final String DETAILS_CURRENTPLAYERS = "details_currentplayers";
  public static final String DETAILS_PLAYERSNEEDED = "details_playersneeded";
  public static final String OBJECTID = "object_id";
  public static final String REQUEST_CODE = "requestCode";

  //UserSessionManager final constants
  public static int PRIVATE_MODE = 0;
  public static final String PREFER_NAME = "UserSession";
  public static final String IS_USER_LOGIN = "IsUserLoggedIn";
  public static final String IS_FIRST_LOGIN = "IsFirstLogin";
  public static final String KEY_NAME = "name";
  public static final String KEY_EMAIL = "email";
  public static final String KEY_INTERESTS_FORMATED="interests_formated";
  public static final String SESSION_EMAIL = "session_email";

  //Manage activities constants
  public static final int ABORT = 0;
  public static final int ADD_ACTIVITY = 1;
  public static final int SEE_DETAILS = 2;
  public static final int EDIT_ACTIVITY = 3;
  public static final int DELETE_ACTIVITY = 4;
  public static final int JOIN_ACTIVITY = 6;

  //Date constants
  public final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss" );

  //Interests activity
  public final static int CHANGE_INTERESTS = 5;

  public static final int REQUEST_CODE_AUTOCOMPLETE = 7;
}