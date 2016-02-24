package com.polimi.jgc.findamate.model;

public class Defaults {
  public static final String APPLICATION_ID = "E26DA9BB-7E36-1AC7-FF12-6D7227BD4700";
  public static final String SECRET_KEY = "A668187A-4CD0-4F92-FF03-A42E36455000";
  public static final String VERSION = "v1";
  public static final String SERVER_URL = "https://api.backendless.com";
  public static final String ARG_YOUR_INTERESTS = "your_interests";
  public static final String ARG_YOUR_ACTIVITIES ="your_activities";
  public static final String DETAILS_TITLE ="details_title";
  public static final String DETAILS_CATEGORY="details_category";
  public static final String DETAILS_DATE ="details_date";
  public static final String DETAILS_DESCRIPTION ="details_description";
  public static final String DETAILS_LATITUDE ="details_latitude";
  public static final String DETAILS_LONGITUDE ="details_longitude";
  public static final String ARG_ACTIVITY_MODE = "activity_mode";
  public static final String DETAILS_PARTICIPANTS = "details_participannts";
  public static final String DETAILS_ASSISTANTS = "details_participannts";
  public static final String DETAILS_OWNERID = "details_ownerid";
  public static final String DETAILS_CREATED = "details_created";
  public static final String DETAILS_UPDATED = "details_updates";
  public static final String DETAILS_CURRENTPLAYERS = "details_currentplayers";
  public static final String DETAILS_PLAYERSNEEDED = "details_playersneeded";

  //UserSessionManager final constants
  public static int PRIVATE_MODE = 0;
  public static final String PREFER_NAME = "UserSession";
  public static final String IS_USER_LOGIN = "IsUserLoggedIn";
  public static final String KEY_NAME = "name";
  public static final String KEY_EMAIL = "email";

  //NewActivity final constants
  public static final int INSERT_OK = 1;
  public static final int INSERT_FAILED = 0;
  public static final int ADD_ACTIVITY = 2;

  public final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat( "yyyy/MM/dd" );
}