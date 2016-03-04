package com.polimi.jgc.findamate.model;

import android.graphics.drawable.Drawable;

/**
 * Created by JGC on 18/02/2016.
 */
public class CategoryItem{
    private String category;
    private String categorySql;


    public CategoryItem (String category, String categorySql){
        this.category=category;
        this.categorySql=categorySql;
    }

    public String getCategory() {
        return category;
    }

    public String getCategorySql() {
        return categorySql;
    }



    public void setCategory(String category) {
        this.category = category;
    }

}
