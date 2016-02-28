package com.polimi.jgc.findamate.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.activity.InterestItemFragment.OnListFragmentInteractionListener;
import com.polimi.jgc.findamate.model.CategoryItem;
import java.util.ArrayList;


/**
 * {@link RecyclerView.Adapter} that can display a DummyItem and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 **/
public class InterestItemRecyclerViewAdapter extends RecyclerView.Adapter<InterestItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<CategoryItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public InterestItemRecyclerViewAdapter(ArrayList<CategoryItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_interestsactivity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getCategory());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onInterestSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox mName;
        public CategoryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (CheckBox) view.findViewById(R.id.interestactivity_item_checkbox);
        }
    }
}
