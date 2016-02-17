package com.polimi.jgc.findamate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Calendar;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ActivityItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ActivityItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ActivityItemFragment newInstance(int columnCount) {
        ActivityItemFragment fragment = new ActivityItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activityitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyActivityItemRecyclerViewAdapter(getListActivities(), mListener));
        }
        return view;
    }

    private ArrayList<ActivityItem> getListActivities(){
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
        activity1.setDate(calendar);
        activities.add(activity1);

        activity2.setTitle("Partida de Call Of Duty");
        activity2.setParticipants(10);
        activity2.setCategory("VIDEOGAMES");
        activity2.setDate(calendar);
        activities.add(activity2);

        activity3.setTitle("Monopoly");
        activity3.setParticipants(4);
        activity3.setCategory("BOARD GAMES");
        activity3.setDate(calendar);
        activities.add(activity3);

        activity4.setTitle("Ajedrez");
        activity4.setParticipants(2);
        activity4.setCategory("BOARD GAMES");
        activity4.setDate(calendar);
        activities.add(activity4);

        activity5.setTitle("Just Dance");
        activity5.setParticipants(4);
        activity5.setCategory("VIDEOGAMES");
        activity5.setDate(calendar);
        activities.add(activity5);

        activity6.setTitle("Basket");
        activity6.setParticipants(10);
        activity6.setCategory("SPORTS");
        activity6.setDate(calendar);
        activities.add(activity6);

        activity7.setTitle("Raid del WOW");
        activity7.setParticipants(25);
        activity7.setCategory("VIDEOGAMES");
        activity7.setDate(calendar);
        activities.add(activity7);

        activity8.setTitle("Tennis");
        activity8.setParticipants(4);
        activity8.setCategory("SPORTS");
        activity8.setDate(calendar);
        activities.add(activity8);

        activity9.setTitle("Partido de futbol 5");
        activity9.setParticipants(10);
        activity9.setCategory("SPORTS");
        activity9.setDate(calendar);
        activities.add(activity9);

        return activities;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onActivitySelected(ActivityItem item);
    }
}
