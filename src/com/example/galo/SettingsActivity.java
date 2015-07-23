package com.example.galo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity{
	
	@Override
	protected void onCreate(Bundle savedIntanceState){
		super.onCreate(savedIntanceState);
		addPreferencesFromResource(R.xml.configs);
		
		
		final SharedPreferences shadf = PreferenceManager.getDefaultSharedPreferences(this);
		String valcorrente;
		
		final ListPreference opcao = (ListPreference) findPreference("oplista");
		opcao.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference,Object newValue) {
				opcao.setSummary((String)newValue);
				return true;
			}
		});
		valcorrente = shadf.getString("oplista", null);
		if(valcorrente != null){
			opcao.setSummary(valcorrente);;
		}
		
		
		
		final EditTextPreference name1 = (EditTextPreference) findPreference("play1AAA");
		name1.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				name1.setSummary((String)newValue);
				return true;
			}
		});
		valcorrente = shadf.getString("play1AAA", null);
		if(valcorrente != null){
			name1.setSummary(valcorrente);;
		}
		
		
		
		final EditTextPreference name2 = (EditTextPreference) findPreference("play2AAA");
		name2.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				name2.setSummary((String)newValue);
				return true;
			}
		});
		valcorrente = shadf.getString("play2AAA", null);
		if(valcorrente != null){
			name2.setSummary(valcorrente);;
		}
		
	}
}
