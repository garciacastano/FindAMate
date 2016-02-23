package com.polimi.jgc.findamate.model;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.Date;


public class ActivityItem{

    private Date updated;
    private Date date_;
    private Date created;
    private String category;
    private String objectId;
    private String ownerId;
    private double latitude;
    private Integer participants;
    private Integer assistants;
    private String title;
    private double longitude;
    private String description;


    //GETTERS AND SETTERS
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

    public void setCategory( String category ){
        this.category = category;
    }

    public String getObjectId(){
        return objectId;
    }

    public String getOwnerId(){
        return ownerId;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude( double latitude ){
        this.latitude = latitude;
    }

    public Integer getParticipants(){
        return participants;
    }

    public void setParticipants( Integer participants ){
        this.participants = participants;
    }

    public Integer getAssistants(){
        return assistants;
    }

    public void setAssistants( Integer assistants ){
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
        int month=date.getMonth()+1;
        int year=date.getYear()+1900;
        String minutes;
        if(date.getMinutes()<10){
            minutes="0"+date.getMinutes();
        }
        else{
            minutes="0"+date.getMinutes();
        }
        return date.getHours()+":"+minutes+" - "+date.getDate()+
                "/"+month+"/"+year;
    }

    //BACKENDLESS SAVING METHODS

    public ActivityItem save(){
        return Backendless.Data.of( ActivityItem.class ).save( this );
    }

    public void saveAsync( AsyncCallback<ActivityItem> callback )
    {
        Backendless.Data.of( ActivityItem.class ).save( this, callback );
    }

    //BACKENDLESS REMOVING METHODS

    public Long remove(){
        return Backendless.Data.of( ActivityItem.class ).remove( this );
    }

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


