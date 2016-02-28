package com.polimi.jgc.findamate.util;

import android.util.Log;

import com.polimi.jgc.findamate.model.CategoryItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static String formatInterests (ArrayList<CategoryItem> selectedInterests){
        String s="";
        for(int i=0; i<selectedInterests.size(); i++){
            if(i==0){
                s=s+selectedInterests.get(i).toString();
            }
            s=s+","+selectedInterests.get(i).toString();
        }
        return s;
    }

    public static ArrayList<String> parseInterests (String formatedInterests){
        ArrayList<String> parse=new ArrayList();
        for (String interest: formatedInterests.split(",")){
            parse.add(interest);
        }
        return parse;
    }



}
