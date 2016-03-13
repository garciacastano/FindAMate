package com.app.jgc.findamate.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.jgc.findamate.R;
import com.app.jgc.findamate.activity.ActivityItemFragment.OnListFragmentInteractionListener;
import com.app.jgc.findamate.model.ActivityItem;

import java.util.List;


/**
 * {@link RecyclerView.Adapter} that can display a DummyItem and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 **/
public class ActivityItemRecyclerViewAdapter extends RecyclerView.Adapter<ActivityItemRecyclerViewAdapter.ViewHolder> {

    private final List<ActivityItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ActivityItemRecyclerViewAdapter(List<ActivityItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_listactivity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mCategory.setText(mValues.get(position).getCategory());
        //holder.mDate.setText(mValues.get(position).getDateToString(Defaults.DETAILS_DATE));
        holder.mImage.setImageResource(mValues.get(position).getImageId());

        holder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onDetailsSelected(holder.mItem);
                }
            }
        });

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onShareSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues==null) return 0 ;
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mCategory;
        //public final TextView mDate;
        public ActivityItem mItem;
        public final ImageView mImage;
        public Button mDetails;
        public Button mShare;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.listactivity_item_title);
            mCategory = (TextView) view.findViewById(R.id.listactivity_item_category);
            mDetails= (Button) view.findViewById(R.id.listactivity_item_details);
            mShare= (Button) view.findViewById(R.id.listactivity_item_share);
            //mDate = (TextView) view.findViewById(R.id.listactivity_item_date);
            mImage = (ImageView) view.findViewById(R.id.listactivity_item_image);
        }
    }
}
