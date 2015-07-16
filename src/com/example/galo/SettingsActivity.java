package com.example.galo;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

	// @Override
	protected void OnCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		addPreferencesFromResource(R.xml.definicoes);
	}

}