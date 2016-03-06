package com.polimi.jgc.findamate.util;

import com.polimi.jgc.findamate.model.CategoryItem;

import java.util.ArrayList;

/**
 * Created by JGC on 03/03/2016.
 */
public class Assistance {

    public static String toSql (String string){
        return "'"+string+"'";
    }

    public static String addEmail (String add, String added){
        ArrayList<String> addedList = parseString(added);

        for(int i=0; i<addedList.size(); i++){
            if(addedList.get(i).equals(add)){
                return added;
            }
        }

        addedList.add(add);
        return formatString(addedList);
    }

    public static String removeEmail (String email, String added){
        ArrayList<String> addedList = parseString(added);

        for(int i=0; i<addedList.size(); i++){
            if(addedList.get(i).equals(email)){
                addedList.remove(i);
            }
        }

        return formatString(addedList);
    }

    public static String formatString (ArrayList<String> string){
        String s="";
        for(int i=0; i<string.size(); i++){
            if(i==0){
                s=s+toSql(string.get(i));
                continue;
            }
            if(i==string.size()-1){
                s=s+","+toSql(string.get(i));
                continue;
            }
            s=s+","+toSql(string.get(i));
        }
        return s;
    }

    public static ArrayList<String> parseString (String string){
        ArrayList<String> parse=new ArrayList();
        if(string != null){
            for (String interest: string.split(",")){
                String c = interest.replace("'", "");
                parse.add(c);
            }
        }
        return parse;
    }
}
