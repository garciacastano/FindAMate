package com.polimi.jgc.findamate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.polimi.jgc.findamate.ActivityItemFragment.OnListFragmentInteractionListener;
import java.util.ArrayList;


/**
 * {@link RecyclerView.Adapter} that can display a DummyItem and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 **/
public class MyActivityItemRecyclerViewAdapter extends RecyclerView.Adapter<MyActivityItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<ActivityItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyActivityItemRecyclerViewAdapter(ArrayList<ActivityItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_activityitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mCategory.setText(mValues.get(position).getCategory());
        holder.mDate.setText(mValues.get(position).getDayMonthYear());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onActivitySelected(holder.mItem);
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
        public final TextView mTitle;
        public final TextView mCategory;
        public final TextView mDate;
        public ActivityItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.list_title);
            mCategory = (TextView) view.findViewById(R.id.list_category);
            mDate = (TextView) view.findViewById(R.id.list_date);
        }
    }
}
