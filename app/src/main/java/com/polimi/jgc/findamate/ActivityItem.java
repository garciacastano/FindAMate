package com.polimi.jgc.findamate;

import java.util.Calendar;

/**
 * Created by JGC on 17/02/2016.
 */
public class ActivityItem {
    private String title;
    private String description;
    private Categories category;
    private Calendar date;
    private int participants;
    private int assistants;

    public ActivityItem(String title, String description, Categories category, Calendar date, int participants, int assistants) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.date = date;
        this.participants = participants;
        this.assistants = assistants;
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

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Calendar getDate() {
        return date;
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
}

