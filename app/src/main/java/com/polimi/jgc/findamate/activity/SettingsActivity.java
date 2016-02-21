package com.polimi.jgc.findamate.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.polimi.jgc.findamate.R;

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
