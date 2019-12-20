package com.example.weatherapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
    public SettingsFragment stF = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager().beginTransaction().replace(R.id.settings, stF).commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.setting_preferences, rootKey);

            Preference prf=findPreference("push_setting");
            prf.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                Intent intent;
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    intent = new Intent(getContext(), PushSettingActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
            Preference showD=findPreference("info");
            showD.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog);
                    dialog.setTitle("       날씨 어때       ");
                    dialog.setMessage("verision:1.0.0\n\nMade by\n김동운\n방인규\n윤상수");
                    dialog.setNegativeButton("확인",null);
                    dialog.show();
                    return false;
                }
            });
        }
    }
}