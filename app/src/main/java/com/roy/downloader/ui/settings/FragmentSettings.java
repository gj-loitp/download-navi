package com.roy.downloader.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.roy.downloader.R;
import com.roy.downloader.core.utils.Utils;
import com.roy.downloader.ui.settings.sections.FragmentAppearanceSettings;
import com.roy.downloader.ui.settings.sections.FragmentBehaviorSettings;
import com.roy.downloader.ui.settings.sections.FragmentBrowserSettings;
import com.roy.downloader.ui.settings.sections.FragmentLimitationsSettings;
import com.roy.downloader.ui.settings.sections.FragmentStorageSettings;

import static com.roy.downloader.ui.settings.ActivitySettings.AppearanceSettings;
import static com.roy.downloader.ui.settings.ActivitySettings.BehaviorSettings;
import static com.roy.downloader.ui.settings.ActivitySettings.BrowserSettings;
import static com.roy.downloader.ui.settings.ActivitySettings.LimitationsSettings;
import static com.roy.downloader.ui.settings.ActivitySettings.StorageSettings;

public class FragmentSettings extends PreferenceFragmentCompat {
    @SuppressWarnings("unused")
    private static final String TAG = FragmentSettings.class.getSimpleName();

    private AppCompatActivity activity;
    private SettingsViewModel viewModel;

    public static FragmentSettings newInstance() {
        FragmentSettings fragment = new FragmentSettings();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AppCompatActivity) activity = (AppCompatActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (activity == null) activity = (AppCompatActivity) getActivity();

        assert activity != null;
        viewModel = new ViewModelProvider(activity).get(SettingsViewModel.class);

        String preference = activity.getIntent().getStringExtra(ActivitySettings.TAG_OPEN_PREFERENCE);
        if (preference != null) {
            openPreference(preference);
            if (!Utils.isLargeScreenDevice(activity)) activity.finish();

        } else if (Utils.isTwoPane(activity)) {
            Fragment f = activity.getSupportFragmentManager().findFragmentById(R.id.detailFragmentContainer);
            if (f == null)
                setFragment(FragmentAppearanceSettings.newInstance(), getString(R.string.pref_header_appearance));
        }

        Preference appearance = findPreference(FragmentAppearanceSettings.class.getSimpleName());
        if (appearance != null) {
            appearance.setOnPreferenceClickListener(prefClickListener);
        }

        Preference behavior = findPreference(FragmentBehaviorSettings.class.getSimpleName());
        if (behavior != null) {
            behavior.setOnPreferenceClickListener(prefClickListener);
        }

        Preference storage = findPreference(FragmentStorageSettings.class.getSimpleName());
        if (storage != null) {
            storage.setOnPreferenceClickListener(prefClickListener);
        }

        Preference browser = findPreference(FragmentBrowserSettings.class.getSimpleName());
        if (browser != null) {
            browser.setOnPreferenceClickListener(prefClickListener);
        }

        Preference limitations = findPreference(FragmentLimitationsSettings.class.getSimpleName());
        if (limitations != null) {
            limitations.setOnPreferenceClickListener(prefClickListener);
        }
    }

    private final Preference.OnPreferenceClickListener prefClickListener = (preference) -> {
        openPreference(preference.getKey());
        return true;
    };

    private void openPreference(String prefName) {
        switch (prefName) {
            case AppearanceSettings:
                if (Utils.isLargeScreenDevice(activity)) {
                    setFragment(FragmentAppearanceSettings.newInstance(), getString(R.string.pref_header_appearance));
                } else {
                    startActivity(FragmentAppearanceSettings.class, getString(R.string.pref_header_appearance));
                }
                break;
            case BehaviorSettings:
                if (Utils.isLargeScreenDevice(activity)) {
                    setFragment(FragmentBehaviorSettings.newInstance(), getString(R.string.pref_header_behavior));
                } else {
                    startActivity(FragmentBehaviorSettings.class, getString(R.string.pref_header_behavior));
                }
                break;
            case StorageSettings:
                if (Utils.isLargeScreenDevice(activity)) {
                    setFragment(FragmentStorageSettings.newInstance(), getString(R.string.pref_header_storage));
                } else {
                    startActivity(FragmentStorageSettings.class, getString(R.string.pref_header_storage));
                }
                break;
            case BrowserSettings:
                if (Utils.isLargeScreenDevice(activity)) {
                    setFragment(FragmentBrowserSettings.newInstance(), getString(R.string.pref_header_browser));
                } else {
                    startActivity(FragmentBrowserSettings.class, getString(R.string.pref_header_browser));
                }
                break;
            case LimitationsSettings:
                if (Utils.isLargeScreenDevice(activity)) {
                    setFragment(FragmentLimitationsSettings.newInstance(), getString(R.string.pref_header_limitations));
                } else {
                    startActivity(FragmentLimitationsSettings.class, getString(R.string.pref_header_limitations));
                }
                break;
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_headers, rootKey);
    }

    private <F extends PreferenceFragmentCompat> void setFragment(F fragment, String title) {
        viewModel.detailTitleChanged.setValue(title);

        if (Utils.isLargeScreenDevice(activity)) {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.detailFragmentContainer, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
    }

    private <F extends PreferenceFragmentCompat> void startActivity(Class<F> fragment, String title) {
        Intent i = new Intent(activity, ActivityPreference.class);
        ActivityPreferenceConfig config = new ActivityPreferenceConfig(fragment.getSimpleName(), title);

        i.putExtra(ActivityPreference.TAG_CONFIG, config);
        startActivity(i);
    }
}
