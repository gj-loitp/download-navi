package com.roy.downloader.ui.settings;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.roy.downloader.R;
import com.roy.downloader.core.utils.Utils;

public class ActivitySettings extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = ActivitySettings.class.getSimpleName();

    public static final String TAG_OPEN_PREFERENCE = "open_preference";

    public static final String AppearanceSettings = "AppearanceSettingsFragment";
    public static final String BehaviorSettings = "BehaviorSettingsFragment";
    public static final String LimitationsSettings = "LimitationsSettingsFragment";
    public static final String StorageSettings = "StorageSettingsFragment";
    public static final String BrowserSettings = "BrowserSettingsFragment";

    private TextView detailTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Utils.getSettingsTheme(getApplicationContext()));
        super.onCreate(savedInstanceState);

        SettingsViewModel viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.settings));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailTitle = findViewById(R.id.detailTitle);
        viewModel.detailTitleChanged.observe(this, title -> {
            if (title != null && detailTitle != null) detailTitle.setText(title);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();

        return true;
    }
}
