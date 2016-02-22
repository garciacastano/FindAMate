package com.polimi.jgc.findamate.model;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;

import java.util.Date;


public class ActivityItem{

    private Date updated;
    private Integer id;
    private Date date_;
    private String category;
    private String objectId;
    private String ownerId;
    private double latitude;
    private Integer participants;
    private Integer assistants;
    private Date created;
    private String title;
    private double longitude;
    private String description;


    //GETTERS AND SETTERS
    public Date getUpdated(){
        return updated;
    }

    public Integer getId(){
        return id;
    }

    public void setId( Integer id ){
        this.id = id;
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

    public String getDayMonthYear() {
        int month=date_.getMonth()+1;
        return date_.getHours()+":"+date_.getMinutes()+" - "+date_.getMonth()+
                                "/"+month+"/"+date_.getYear();
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
}


