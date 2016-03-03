package com.polimi.jgc.findamate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.persistence.BackendlessDataQuery;
import com.polimi.jgc.findamate.model.ActivityItem;
import com.polimi.jgc.findamate.model.Defaults;

import com.polimi.jgc.findamate.controller.ActivityItemRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.util.DefaultCallback;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */

public class ActivityItemFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private List<ActivityItem> activities;
    private RecyclerView recyclerView;

    public ActivityItemFragment() {
    }

   @SuppressWarnings("unused")
    public static ActivityItemFragment newInstance(String mode, HashMap<String, String> user) {
        ActivityItemFragment fragment = new ActivityItemFragment();
        Bundle args = new Bundle();
        args.putString(Defaults.ARG_ACTIVITY_MODE, mode);
        args.putString(Defaults.KEY_EMAIL, user.get(Defaults.KEY_EMAIL));
        args.putString(Defaults.KEY_INTERESTS_FORMATED, user.get(Defaults.KEY_INTERESTS_FORMATED));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onSaveInstanceState(savedInstanceState);
        super.onCreate(savedInstanceState);
        activities = new ArrayList<>();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listactivity_list, container, false);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        downloadData(null);
        return view;
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

    public void downloadData(String newInterests) {
        if(newInterests!=null) {
            getArguments().putString(Defaults.KEY_INTERESTS_FORMATED,newInterests);
        }
        BackendlessDataQuery query = new BackendlessDataQuery();
        switch (getArguments().getString(Defaults.ARG_ACTIVITY_MODE)) {
            case Defaults.ARG_YOUR_INTERESTS:
                query.setWhereClause("category in ("+getArguments().getString(Defaults.KEY_INTERESTS_FORMATED)+")");
                Log.d("ERROR QUERY", query.toString());
                ActivityItem.findAsync(query, new DefaultCallback<BackendlessCollection<ActivityItem>>(getActivity()) {
                    @Override
                    public void handleResponse(BackendlessCollection<ActivityItem> response) {
                        super.handleResponse(response);
                        activities = response.getData();
                        Log.d("RETRIEVE DATA", "Activities YOUR INTERESTS = " + activities.size());
                        ActivityItemRecyclerViewAdapter adapter = new ActivityItemRecyclerViewAdapter(activities, mListener);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                });
                break;

            case Defaults.ARG_YOUR_ACTIVITIES:
                query.setWhereClause("ownerId = '"+getArguments().getString(Defaults.KEY_EMAIL)+"'");
                ActivityItem.findAsync(query, new DefaultCallback<BackendlessCollection<ActivityItem>>(getActivity()) {
                    @Override
                    public void handleResponse(BackendlessCollection<ActivityItem> response) {
                        super.handleResponse(response);
                        activities = response.getData();
                        Log.d("RETRIEVE DATA", "Activities de USUARIO = " + activities.size());
                        ActivityItemRecyclerViewAdapter adapter = new ActivityItemRecyclerViewAdapter(activities, mListener);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
    }
}
