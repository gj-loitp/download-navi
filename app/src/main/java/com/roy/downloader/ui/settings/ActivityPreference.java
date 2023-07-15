package com.roy.downloader.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

import com.roy.downloader.R;
import com.roy.downloader.core.utils.Utils;
import com.roy.downloader.ui.settings.sections.FragmentAppearanceSettings;
import com.roy.downloader.ui.settings.sections.FragmentBehaviorSettings;
import com.roy.downloader.ui.settings.sections.FragmentBrowserSettings;
import com.roy.downloader.ui.settings.sections.FragmentLimitationsSettings;
import com.roy.downloader.ui.settings.sections.FragmentStorageSettings;

public class ActivityPreference extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = ActivityPreference.class.getSimpleName();

    public static final String TAG_CONFIG = "config";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Utils.getSettingsTheme(getApplicationContext()));
        super.onCreate(savedInstanceState);

        /* Prevent create activity in two pane mode (after resizing window) */
        if (Utils.isLargeScreenDevice(this)) {
            finish();
            return;
        }

        setContentView(R.layout.a_preference);

        String fragment = null;
        String title = null;
        Intent intent = getIntent();
        if (intent.hasExtra(TAG_CONFIG)) {
            ActivityPreferenceConfig config = intent.getParcelableExtra(TAG_CONFIG);
            fragment = config.getFragment();
            title = config.getTitle();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (title != null) toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (fragment != null && savedInstanceState == null) setFragment(getFragment(fragment));
    }

    public <F extends PreferenceFragmentCompat> void setFragment(F fragment) {
        if (fragment == null) return;

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    private <F extends PreferenceFragmentCompat> F getFragment(String fragment) {
        if (fragment != null) {
            if (fragment.equals(FragmentAppearanceSettings.class.getSimpleName()))
                return (F) FragmentAppearanceSettings.newInstance();
            else if (fragment.equals(FragmentBehaviorSettings.class.getSimpleName()))
                return (F) FragmentBehaviorSettings.newInstance();
            else if (fragment.equals(FragmentStorageSettings.class.getSimpleName()))
                return (F) FragmentStorageSettings.newInstance();
            else if (fragment.equals(FragmentBrowserSettings.class.getSimpleName()))
                return (F) FragmentBrowserSettings.newInstance();
            else if (fragment.equals(FragmentLimitationsSettings.class.getSimpleName()))
                return (F) FragmentLimitationsSettings.newInstance();
            else return null;
        }

        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }
}
