package com.polimi.jgc.findamate.util;

import com.polimi.jgc.findamate.model.CategoryItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JGC on 18/02/2016.
 */
public class CategoryManager {

    private final String CATEGORIES[]= {"SOCCER", "VOLLEY", "RUGBY", "TENNIS", "TABLE_TENNIS", "BASKET"};

    public CategoryManager(){
    }

    public ArrayList<CategoryItem> getListCategories(){
        ArrayList<CategoryItem> categories=new ArrayList<>();

        for(int i=0; i<CATEGORIES.length; i++) {
            CategoryItem c = new CategoryItem(CATEGORIES[i], false);
            categories.add(c);
        }
        return categories;
    }


}
