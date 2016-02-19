package com.polimi.jgc.findamate.model;

/**
 * Created by JGC on 18/02/2016.
 */
public class CategoryItem{
    private String category;
    private boolean isInterested;

    public CategoryItem (String category, boolean isInterested){
        this.category=category;
        this.isInterested=isInterested;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
