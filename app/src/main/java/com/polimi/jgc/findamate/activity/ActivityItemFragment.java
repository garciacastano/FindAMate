package com.polimi.jgc.findamate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
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
    private RecyclerView recyclerView;

   @SuppressWarnings("unused")
    public static ActivityItemFragment newInstance(String mode, HashMap<String, String> user) {
        ActivityItemFragment fragment = new ActivityItemFragment();
        Bundle args = new Bundle();
        args.putString(Defaults.ARG_ACTIVITY_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listactivity_list, container, false);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        downloadActivities();
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

    public interface OnListFragmentInteractionListener {
        void onActivitySelected(ActivityItem item);    }

    public void downloadActivities() {
        Bundle b = getArguments();
        HashMap user = ListActivitiesActivity.session.getUserDetails();
        BackendlessDataQuery query = new BackendlessDataQuery();
        switch (b.getString(Defaults.ARG_ACTIVITY_MODE)) {
            case Defaults.ARG_YOUR_INTERESTS:
                query.setWhereClause("category in ("+user.get(Defaults.KEY_INTERESTS_FORMATED).toString()+") " +
                        "and distance( "+Double.parseDouble(user.get(Defaults.KEY_MYLAT).toString())+
                        "," +Double.parseDouble(user.get(Defaults.KEY_MYLON).toString())+
                        ", location.latitude, location.longitude ) < km(10)");

                ActivityItem.findAsync(query, new DefaultCallback<BackendlessCollection<ActivityItem>>(getActivity()) {
                    @Override
                    public void handleResponse(BackendlessCollection<ActivityItem> response) {
                        super.handleResponse(response);
                        ActivityItemRecyclerViewAdapter adapter = new ActivityItemRecyclerViewAdapter(response.getData(), mListener);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;

            case Defaults.ARG_CREATED_ACTIVITIES:
                query.setWhereClause("ownerId = '"+user.get(Defaults.KEY_EMAIL)+"'");
                ActivityItem.findAsync(query, new DefaultCallback<BackendlessCollection<ActivityItem>>(getActivity()) {
                    @Override
                    public void handleResponse(BackendlessCollection<ActivityItem> response) {
                        super.handleResponse(response);
                        ActivityItemRecyclerViewAdapter adapter = new ActivityItemRecyclerViewAdapter(response.getData(), mListener);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
    }

}
