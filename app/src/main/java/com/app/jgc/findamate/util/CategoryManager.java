package com.app.jgc.findamate.util;

import com.app.jgc.findamate.model.CategoryItem;

import java.util.ArrayList;

/**
 * Created by JGC on 18/02/2016.
 */
public class CategoryManager {

    public static final String CATEGORIES[]= {"SOCCER", "VOLLEYBALL", "RUGBY", "TENNIS", "RUNNING", "FITNESS", "TABLE TENNIS", "BASKETBALL", "AMERICAN FOOTBALL", "BASEBALL", "HANDBALL", "CLIMBING", "SKI", "SNOWBOARD" };
    public static final String CATEGORIESSQL[]= {"'SOCCER'", "'VOLLEYBALL'", "'RUGBY'", "'TENNIS'", "'RUNNING'", "'FITNESS'", "'TABLE TENNIS'", "'BASKETBALL'", "'AMERICAN FOOTBALL'", "'BASEBALL'", "'HANDBALL'", "'CLIMBING'", "'SKI'", "'SNOWBOARD'" };

    public CategoryManager(){
    }

    public ArrayList<CategoryItem> getListCategories(){
        ArrayList<CategoryItem> categories=new ArrayList<>();

        for(int i=0; i<CATEGORIES.length; i++) {
            CategoryItem c = new CategoryItem(CATEGORIES[i],CATEGORIESSQL[i]);
            categories.add(c);
        }
        return categories;
    }
    public static String formatInterests (ArrayList<CategoryItem> selectedInterests){
        String s="";
        for(int i=0; i<selectedInterests.size(); i++){
            if(i==0){
                s=s+selectedInterests.get(i).getCategorySql();
                continue;
            }
            if(i==selectedInterests.size()-1){
                s=s+","+selectedInterests.get(i).getCategorySql();
                continue;
            }
            s=s+","+selectedInterests.get(i).getCategorySql();
        }
        return s;
    }

    public static ArrayList<CategoryItem> parseInterests (String formatedInterests){
        ArrayList<CategoryItem> parse=new ArrayList();
        for (String interest: formatedInterests.split(",")){
            CategoryItem c = new CategoryItem(interest.replace("'", ""),interest);
            if(false){
                continue;
            }
            else{
                parse.add(c);
            }
        }
        return parse;
    }



}
