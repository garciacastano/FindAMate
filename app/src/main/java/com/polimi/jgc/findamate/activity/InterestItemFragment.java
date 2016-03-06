package com.polimi.jgc.findamate.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.polimi.jgc.findamate.controller.InterestItemRecyclerViewAdapter;
import java.util.ArrayList;
import com.polimi.jgc.findamate.R;
import com.polimi.jgc.findamate.model.Defaults;
import com.polimi.jgc.findamate.util.CategoryManager;
import com.polimi.jgc.findamate.model.CategoryItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class InterestItemFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InterestItemFragment() {
    }

   @SuppressWarnings("unused")
    public static InterestItemFragment newInstance(String formatedInterests) {
       InterestItemFragment fragment = new InterestItemFragment();
        Bundle args = new Bundle();
        args.putString(Defaults.KEY_INTERESTS_FORMATED, formatedInterests);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onSaveInstanceState(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interestsactivity_list, container, false);
        CategoryManager categoryManager=new CategoryManager();
        ArrayList<CategoryItem> categories = categoryManager.getListCategories();
        ArrayList<CategoryItem> interests = CategoryManager.parseInterests(getArguments().getString(Defaults.KEY_INTERESTS_FORMATED));

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new InterestItemRecyclerViewAdapter(categories, mListener, interests));
        }

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
        void onInterestSelected(CategoryItem item);
    }
}
