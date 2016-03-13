package com.app.jgc.findamate.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.jgc.findamate.R;
import com.app.jgc.findamate.model.CategoryItem;
import com.app.jgc.findamate.model.Defaults;
import com.app.jgc.findamate.util.CategoryManager;

import java.util.ArrayList;


public class InterestsActivity extends ActionBarActivity implements InterestItemFragment.OnListFragmentInteractionListener {

    private ViewPager viewPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<CategoryItem> selectedInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests_activity);

        selectedInterests= CategoryManager.parseInterests(getIntent().getStringExtra(Defaults.KEY_INTERESTS_FORMATED));

        if(selectedInterests.get(0).getCategory().toString().equals("dummy")){
            selectedInterests.remove(0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onInterestSelected(CategoryItem item) {
        for(int i=0; i<selectedInterests.size();i++){
            if(selectedInterests.get(i).getCategory().equals(item.getCategory())){
                selectedInterests.remove(i);
                return;
            }
        }
        selectedInterests.add(item);
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return InterestItemFragment.newInstance(CategoryManager.formatInterests(selectedInterests));
        }

        @Override
        public int getCount() {
            // Show  total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "CATEGORIES";
        }

    }

    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        setResult(Defaults.ABORT, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }
        if (id == R.id.save_interests) {
            if(selectedInterests.size()==0){
                Toast.makeText(this, "Select at least one category", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            Intent returnIntent=new Intent ();
            returnIntent.putExtra(Defaults.KEY_INTERESTS_FORMATED, CategoryManager.formatInterests(selectedInterests));
            setResult(Defaults.CHANGE_INTERESTS, returnIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inteterests, menu);
        return true;
    }


}


