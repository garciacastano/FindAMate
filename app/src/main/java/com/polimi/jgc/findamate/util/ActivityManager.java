package com.polimi.jgc.findamate.util;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;
import com.polimi.jgc.findamate.model.ActivityItem;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by JGC on 18/02/2016.
 */
public class ActivityManager {

    private static final String ARG_YOUR_INTERESTS = "your_interests";
    private static final String ARG_YOUR_ACTIVITIES ="your_activities";

    public ActivityManager(){
    }

    public ArrayList<ActivityItem> getListActivities(String mode){
        switch(mode){
            case ARG_YOUR_INTERESTS:
                return getInterestingActivities();
            case ARG_YOUR_ACTIVITIES:
                return getYourActivities();
        }
        return getInterestingActivities();
    }

    private ArrayList<ActivityItem> getInterestingActivities(){
        ArrayList<ActivityItem> activities = new ArrayList<>();
        ActivityItem activity1 = new ActivityItem();
        ActivityItem activity2 = new ActivityItem();
        ActivityItem activity3 = new ActivityItem();
        ActivityItem activity4 = new ActivityItem();
        ActivityItem activity5 = new ActivityItem();
        ActivityItem activity6 = new ActivityItem();
        ActivityItem activity7 = new ActivityItem();
        ActivityItem activity8 = new ActivityItem();
        ActivityItem activity9 = new ActivityItem();

        activity1.setTitle("Partido de futbol 11");
        activity1.setParticipants(22);
        activity1.setCategory("SPORTS");
        Calendar calendar = Calendar.getInstance();
        activity1.setDate_(calendar.getTime());
        activities.add(activity1);

        activity2.setTitle("Partida de Call Of Duty");
        activity2.setParticipants(10);
        activity2.setCategory("VIDEOGAMES");
        activity2.setDate_(calendar.getTime());
        activities.add(activity2);

        activity3.setTitle("Monopoly");
        activity3.setParticipants(4);
        activity3.setCategory("BOARD GAMES");
        activity3.setDate_(calendar.getTime());
        activities.add(activity3);

        activity4.setTitle("Ajedrez");
        activity4.setParticipants(2);
        activity4.setCategory("BOARD GAMES");
        activity4.setDate_(calendar.getTime());
        activities.add(activity4);

        activity5.setTitle("Just Dance");
        activity5.setParticipants(4);
        activity5.setCategory("VIDEOGAMES");
        activity5.setDate_(calendar.getTime());
        activities.add(activity5);

        activity6.setTitle("Basket");
        activity6.setParticipants(10);
        activity6.setCategory("SPORTS");
        activity6.setDate_(calendar.getTime());
        activities.add(activity6);

        activity7.setTitle("Raid del WOW");
        activity7.setParticipants(25);
        activity7.setCategory("VIDEOGAMES");
        activity7.setDate_(calendar.getTime());
        activities.add(activity7);

        activity8.setTitle("Tennis");
        activity8.setParticipants(4);
        activity8.setCategory("SPORTS");
        activity8.setDate_(calendar.getTime());
        activities.add(activity8);

        activity9.setTitle("Partido de futbol 5");
        activity9.setParticipants(10);
        activity9.setCategory("SPORTS");
        activity9.setDate_(calendar.getTime());
        activities.add(activity9);

        return activities;
    }

    private ArrayList<ActivityItem> getYourActivities(){
        ArrayList<ActivityItem> activities = new ArrayList<>();
        ActivityItem activity1 = new ActivityItem();
        ActivityItem activity6 = new ActivityItem();
        ActivityItem activity8 = new ActivityItem();
        ActivityItem activity9 = new ActivityItem();

        activity1.setTitle("Partido de futbol 11");
        activity1.setParticipants(22);
        activity1.setCategory("SPORTS");
        Calendar calendar = Calendar.getInstance();
        activity1.setDate_(calendar.getTime());
        activities.add(activity1);

        activity6.setTitle("Basket");
        activity6.setParticipants(10);
        activity6.setCategory("SPORTS");
        activity6.setDate_(calendar.getTime());
        activities.add(activity6);

        activity8.setTitle("Tennis");
        activity8.setParticipants(4);
        activity8.setCategory("SPORTS");
        activity8.setDate_(calendar.getTime());
        activities.add(activity8);

        activity9.setTitle("Partido de futbol 5");
        activity9.setParticipants(10);
        activity9.setCategory("SPORTS");
        activity9.setDate_(calendar.getTime());
        activities.add(activity9);

        return activities;
    }

    public ActivityItem getActivityDetails(String id){
        ActivityItem activity1 = new ActivityItem();
        Calendar calendar = Calendar.getInstance();
        activity1.setDate_(calendar.getTime());
        activity1.setAssistants(13);
        activity1.setCategory("SPORTS");
        activity1.setTitle("Partido futbol 11");
        activity1.setDescription("El rey se ha dirigido a los invitados fundamentalmente en inglés, si bien con algunas frases en castellano y en catalán, idioma en el que ha recordado que la consideración de Barcelona como \"capital mundial del móvil\" es fruto de la \"suma de esfuerzos y voluntades\" entre las Administraciones y entidades implicadas. Tras citar entre ellas al Ayuntamiento de Barcelona, la Generalitat de Cataluña y el Ministerio de Industria, el monarca ha recalcado que \"este es el camino para garantizar la prosperidad de todos: trabajar juntos teniendo siempre como fin el bien común\".\n" +
                "\n" +
                "Ver más en: http://www.20minutos.es/noticia/2679076/0/rey-felipe/carles-puigdemont/encuentro-cena-barcelona/#xtor=AD-15&xts=467263");
        activity1.setLatitude(45.4764869);
        activity1.setLongitude(9.2241113);
        activity1.setParticipants(22);
        return activity1;
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
        Backendless.Data.of( ActivityItem.class ).findLast( callback );
    }

    public static BackendlessCollection<ActivityItem> find( BackendlessDataQuery query ){
        return Backendless.Data.of( ActivityItem.class ).find( query );
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<ActivityItem>> callback ){
        Backendless.Data.of( ActivityItem.class ).find( query, callback );
    }
}
