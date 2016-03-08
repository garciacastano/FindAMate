package com.polimi.jgc.findamate.model;

import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;
import com.polimi.jgc.findamate.R;

import java.text.ParseException;
import java.util.Date;


public class ActivityItem{

    private Date updated;
    private Date date_;
    private Date created;
    private String category;
    private String objectId;
    private String ownerId;
    private double latitude;
    private int participants;
    private int assistants;
    private String title;
    private double longitude;
    private String description;
    private String assistantEmails;
    private int imageId;
    private GeoPoint location;


    //GETTERS AND SETTERS
    public GeoPoint getLocation(){
        return location;
    }

    public void setLocation (GeoPoint location){
        this.location=location;
    }

    public Date getUpdated(){
        return updated;
    }

    public Date getDate_(){
        return date_;
    }

    public void setDate_( Date date_ ){
        this.date_ = date_;
    }

    public String getCategory(){
        return category;
    }

    public int getImageId (){
        switch (category){
            case "SOCCER":
                return R.drawable.soccer;
            case "VOLLEYBALL":
                return R.drawable.volleyball;
            case "BASKETBALL":
                return R.drawable.basketball;
            case "BASEBALL":
                return R.drawable.baseball;
            case "HANDBALL":
                return R.drawable.handball;
            case "SKI":
                return R.drawable.ski;
            case "SNOWBOARD":
                return R.drawable.snowboard;
            case "RUGBY":
                return R.drawable.rugby;
            case "TENNIS":
                return R.drawable.tennis;
            case "TABLE TENNIS":
                return R.drawable.table_tennis;
            case "CLIMBING":
                return R.drawable.climbing;
            case "RUNNING":
                return R.drawable.running;
            case "FITNESS":
                return R.drawable.fitness;
            case "AMERICAN FOOTBALL":
                return R.drawable.american_football;
            default:
                return R.drawable.running;
        }
    }

    public void setCategory( String category ){
        this.category = category;
    }

    public String getObjectId(){
        return objectId;
    }

    public void setObjectId(String objectId){
        this.objectId=objectId;
    }

    public String getOwnerId(){
        return ownerId;
    }

    public void setOwnerId(String ownerId){
        this.ownerId=ownerId;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude( double latitude ){
        this.latitude = latitude;
    }

    public int getParticipants(){
        return participants;
    }

    public void setParticipants( int participants ){
        this.participants = participants;
    }

    public String getAssistantEmails(){
        return assistantEmails;
    }

    public void setAssistantEmails ( String assistantEmails ){
        this.assistantEmails = assistantEmails;
    }

    public int getAssistants(){
        return assistants;
    }

    public void setAssistants( int assistants ){
        this.assistants = assistants;
    }

    public Date getCreated(){
        return created;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public Double getLongitude(){
        return longitude;
    }

    public void setLongitude( Double longitude ){
        this.longitude = longitude;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription( String description ){
        this.description = description;
    }

    public String getDateToString(String mode){
        Date date;
        switch (mode){
            case Defaults.DETAILS_CREATED:
                date=created;
                break;
            case Defaults.DETAILS_UPDATED:
                if(updated == null){
                    date=created;
                }
                else{
                    date=updated;
                }
                break;
            case Defaults.DETAILS_DATE:
                date=date_;
                break;
            default:
                return "Error on method getDateToString";
        }
        /**int month=date.getMonth()+1;
        int year=date.getYear()+1900;
        String minutes;
        if(date.getMinutes()<10){
            minutes="0"+date.getMinutes();
        }
        else{
            minutes="0"+date.getMinutes();
        }**/
        return Defaults.SIMPLE_DATE_FORMAT.format(date);
    }

    public static ActivityItem obtainActivityItem (Bundle bundle){
        ActivityItem activityItem = new ActivityItem();
        activityItem.setObjectId(bundle.getString(Defaults.OBJECTID));
        activityItem.setCategory(bundle.getString(Defaults.DETAILS_CATEGORY));
        activityItem.setDescription(bundle.getString(Defaults.DETAILS_DESCRIPTION));
        activityItem.setTitle(bundle.getString(Defaults.DETAILS_TITLE));
        Date d;
        try{
            d = Defaults.SIMPLE_DATE_FORMAT.parse(bundle.getString(Defaults.DETAILS_DATE));
            Log.d("PARSE " + bundle.getString(Defaults.DETAILS_DATE), d.toString());
            activityItem.setDate_(d);
        }catch (ParseException e) {
            Log.d("PARSE EXCEPTION", "PARSE EXCEPTION");
        }
        activityItem.setParticipants(bundle.getInt(Defaults.DETAILS_PARTICIPANTS));
        activityItem.setAssistants(bundle.getInt(Defaults.DETAILS_ASSISTANTS));
        activityItem.setCategory(bundle.getString(Defaults.DETAILS_CATEGORY));
        activityItem.setLatitude(bundle.getDouble(Defaults.DETAILS_LATITUDE));
        activityItem.setLongitude(bundle.getDouble(Defaults.DETAILS_LONGITUDE));
        activityItem.setOwnerId(bundle.getString(Defaults.DETAILS_OWNERID));
        activityItem.setLocation(new GeoPoint(bundle.getDouble(Defaults.DETAILS_LATITUDE),bundle.getDouble(Defaults.DETAILS_LONGITUDE)));
        return activityItem;
    }

    //BACKENDLESS SAVING METHODS



    public void saveAsync( AsyncCallback<ActivityItem> callback ){
        Backendless.Data.of( ActivityItem.class ).save( this, callback );
    }

    //BACKENDLESS REMOVING METHODS



    public void removeAsync( AsyncCallback<Long> callback ){
        Backendless.Data.of( ActivityItem.class ).remove( this, callback );
    }

    //BACKENDLESS RETRIEVING METHODS

    public static ActivityItem findById( String id ){
        return Backendless.Data.of( ActivityItem.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<ActivityItem> callback ){
        Backendless.Data.of( ActivityItem.class ).findById( id, callback );
    }

    public static ActivityItem findFirst(){
        return Backendless.Data.of( ActivityItem.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<ActivityItem> callback ){
        Backendless.Data.of( ActivityItem.class ).findFirst( callback );
    }

    public static ActivityItem findLast(){
        return Backendless.Data.of( ActivityItem.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<ActivityItem> callback ){
        Backendless.Data.of( ActivityItem.class ).findLast(callback);
    }

    public static BackendlessCollection<ActivityItem> find( BackendlessDataQuery query ){
        return Backendless.Data.of( ActivityItem.class ).find(query);
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<ActivityItem>> callback ){
        Backendless.Data.of( ActivityItem.class ).find(query, callback);
    }

}


