package com.polimi.jgc.findamate.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by JGC on 17/02/2016.
 */
public class ActivityItem {
    private String id;
    private String title;
    private String description;
    private String category;
    private Calendar date;
    private double latitude;
    private double longitude;
    private int participants;
    private int assistants;

    public ActivityItem() {}

    public ActivityItem(String id, String title, String description, String category, Calendar date,
                        int participants, int assistants, double latitude, double longitude) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.date = date;
        this.participants = participants;
        this.assistants = assistants;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId (){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDayMonthYear() {
        if(date.get(Calendar.AM_PM)==Calendar.PM){
            return date.get(Calendar.HOUR)+12+":"+date.get(Calendar.MINUTE)+" - "+date.get(Calendar.DAY_OF_MONTH)+
                    "/"+(int)date.get(Calendar.MONTH)+1+"/"+date.get(Calendar.YEAR);
        }

        return date.get(Calendar.HOUR)+":"+date.get(Calendar.MINUTE)+" - "+date.get(Calendar.DAY_OF_MONTH)+
                "/"+date.get(Calendar.MONTH)+1+"/"+date.get(Calendar.YEAR);
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getAssistants() {
        return assistants;
    }

    public void setAssistants(int assistants) {
        this.assistants = assistants;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

