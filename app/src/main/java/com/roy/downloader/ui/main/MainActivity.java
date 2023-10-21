package com.roy.downloader.ui.main;

import static com.roy.downloader.ext.ActivityKt.moreApp;
import static com.roy.downloader.ext.ActivityKt.rateApp;
import static com.roy.downloader.ext.ActivityKt.shareApp;
import static com.roy.downloader.ext.ApplovinKt.createAdBanner;
import static com.roy.downloader.ext.ApplovinKt.destroyAdBanner;
import static com.roy.downloader.ext.ApplovinKt.showMediationDebuggerApplovin;
import static com.roy.downloader.ext.ContextKt.openBrowserPolicy;
import static com.roy.downloader.ext.ContextKt.openUrlInBrowser;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.applovin.mediation.ads.MaxAdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import com.roy.downloader.R;
import com.roy.downloader.ui.BatteryOptimizationDialog;
import com.roy.downloader.ui.DialogPermissionDenied;
import com.roy.downloader.core.RepositoryHelper;
import com.roy.downloader.core.model.DownloadEngine;
import com.roy.downloader.core.settings.SettingsRepository;
import com.roy.downloader.core.utils.Utils;
import com.roy.downloader.receiver.NotificationReceiver;
import com.roy.downloader.service.DownloadService;
import com.roy.downloader.ui.BaseAlertDialog;
import com.roy.downloader.ui.PermissionManager;
import com.roy.downloader.ui.adddownload.ActivityAddDownload;
import com.roy.downloader.ui.browser.ActivityBrowser;
import com.roy.downloader.ui.main.drawer.AdapterDrawerExpandable;
import com.roy.downloader.ui.main.drawer.DrawerGroup;
import com.roy.downloader.ui.main.drawer.DrawerGroupItem;
import com.roy.downloader.ui.settings.ActivitySettings;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String TAG_ABOUT_DIALOG = "about_dialog";
    private static final String TAG_PERM_DENIED_DIALOG = "perm_denied_dialog";
    private static final String TAG_BATTERY_DIALOG = "battery_dialog";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private RecyclerView drawerItemsList;
    private LinearLayoutManager layoutManager;
    private AdapterDrawerExpandable drawerAdapter;
    private RecyclerViewExpandableItemManager drawerItemManager;

    private DownloadsViewModel fragmentViewModel;
    private SearchView searchView;
    private DownloadEngine engine;
    private SettingsRepository pref;
    protected CompositeDisposable disposables = new CompositeDisposable();
    private BaseAlertDialog.SharedViewModel dialogViewModel;
    private BaseAlertDialog aboutDialog;
    private DialogPermissionDenied permDeniedDialog;
    private BatteryOptimizationDialog batteryDialog;
    private PermissionManager permissionManager;

    private MaxAdView adView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Utils.getAppTheme(getApplicationContext()));
        super.onCreate(savedInstanceState);

        if (getIntent().getAction() != null && getIntent().getAction().equals(NotificationReceiver.NOTIFY_ACTION_SHUTDOWN_APP)) {
            finish();
            return;
        }

        ViewModelProvider provider = new ViewModelProvider(this);
        fragmentViewModel = provider.get(DownloadsViewModel.class);
        dialogViewModel = provider.get(BaseAlertDialog.SharedViewModel.class);
        FragmentManager fm = getSupportFragmentManager();
        aboutDialog = (BaseAlertDialog) fm.findFragmentByTag(TAG_ABOUT_DIALOG);
        permDeniedDialog = (DialogPermissionDenied) fm.findFragmentByTag(TAG_PERM_DENIED_DIALOG);
        batteryDialog = (BatteryOptimizationDialog) fm.findFragmentByTag(TAG_BATTERY_DIALOG);

        permissionManager = new PermissionManager(this, new PermissionManager.Callback() {
            @Override
            public void onStorageResult(boolean isGranted, boolean shouldRequestStoragePermission) {
                if (!isGranted && shouldRequestStoragePermission) {
                    if (fm.findFragmentByTag(TAG_PERM_DENIED_DIALOG) == null) {
                        permDeniedDialog = DialogPermissionDenied.newInstance();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.add(permDeniedDialog, TAG_PERM_DENIED_DIALOG);
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onNotificationResult(boolean isGranted, boolean shouldRequestNotificationPermission) {
                permissionManager.setDoNotAskNotifications(!isGranted);
            }
        });

        setContentView(R.layout.activity_main);

        pref = RepositoryHelper.getSettingsRepository(getApplicationContext());
        Utils.disableBrowserFromSystem(getApplicationContext(), pref.browserDisableFromSystem());
        Utils.enableBrowserLauncherIcon(getApplicationContext(), pref.browserLauncherIcon());

        engine = DownloadEngine.getInstance(getApplicationContext());

        initLayout();
        engine.restoreDownloads();

        if (!permissionManager.checkPermissions() && permDeniedDialog == null) {
            permissionManager.requestPermissions();
        }

        if (Utils.shouldShowBatteryOptimizationDialog(this)) {
            showBatteryOptimizationDialog();
        }

        adView = createAdBanner(this, getClass().getSimpleName(), Color.TRANSPARENT, findViewById(R.id.flAd), true);
    }

    @Override
    protected void onDestroy() {
        destroyAdBanner(findViewById(R.id.flAd), adView);
        super.onDestroy();
    }

    private void initLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        /* Android data binding doesn't work with layout aliases */
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        TabLayout tabLayout = findViewById(R.id.downloadListTabs);
        ViewPager2 viewPager = findViewById(R.id.downloadListViewPager);
        FloatingActionButton fab = findViewById(R.id.addFab);
        drawerItemsList = findViewById(R.id.drawerItemsList);
        layoutManager = new LinearLayoutManager(this);

        toolbar.setTitle(R.string.app_name);
        /* Disable elevation for portrait mode */
        if (!Utils.isTwoPane(this))
            toolbar.setElevation(0);
        setSupportActionBar(toolbar);

        if (drawerLayout != null) {
            toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
            drawerLayout.addDrawerListener(toggle);
        }
        initDrawer();
        fragmentViewModel.resetSearch();

        DownloadListPagerAdapter pagerAdapter = new DownloadListPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(DownloadListPagerAdapter.NUM_FRAGMENTS);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case DownloadListPagerAdapter.QUEUED_FRAG_POS -> tab.setText(R.string.fragment_title_queued);
                case DownloadListPagerAdapter.COMPLETED_FRAG_POS -> tab.setText(R.string.fragment_title_completed);
            }
        }).attach();

        fab.setOnClickListener((v) -> startActivity(new Intent(this, ActivityAddDownload.class)));
    }

    private void initDrawer() {
        drawerItemManager = new RecyclerViewExpandableItemManager(null);
        drawerItemManager.setDefaultGroupsExpandedState(false);
        drawerItemManager.setOnGroupCollapseListener((groupPosition, fromUser, payload) -> {
            if (fromUser) saveGroupExpandState(groupPosition, false);
        });
        drawerItemManager.setOnGroupExpandListener((groupPosition, fromUser, payload) -> {
            if (fromUser) saveGroupExpandState(groupPosition, true);
        });
        GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();
        /*
         * Change animations are enabled by default since support-v7-recyclerview v22.
         * Need to disable them when using animation indicator.
         */
        animator.setSupportsChangeAnimations(false);

        List<DrawerGroup> groups = Utils.getNavigationDrawerItems(this, PreferenceManager.getDefaultSharedPreferences(this));
        drawerAdapter = new AdapterDrawerExpandable(groups, drawerItemManager, this::onDrawerItemSelected);
        RecyclerView.Adapter wrappedDrawerAdapter = drawerItemManager.createWrappedAdapter(drawerAdapter);
        onDrawerGroupsCreated();

        drawerItemsList.setLayoutManager(layoutManager);
        drawerItemsList.setAdapter(wrappedDrawerAdapter);
        drawerItemsList.setItemAnimator(animator);
        drawerItemsList.setHasFixedSize(false);

        drawerItemManager.attachRecyclerView(drawerItemsList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (toggle != null) toggle.syncState();
    }

    @Override
    public void onStart() {
        super.onStart();

        subscribeAlertDialog();
        subscribeSettingsChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

        disposables.clear();
    }

    private void subscribeAlertDialog() {
        Disposable d = dialogViewModel.observeEvents().subscribe((event) -> {
            if (event.dialogTag == null) {
                return;
            }
            switch (event.dialogTag) {
                case TAG_ABOUT_DIALOG:
                    switch (event.type) {
                        case NEGATIVE_BUTTON_CLICKED -> openChangelogLink();
                        case DIALOG_SHOWN -> initAboutDialog();
                    }
                    break;
                case TAG_PERM_DENIED_DIALOG:
                    if (event.type != BaseAlertDialog.EventType.DIALOG_SHOWN) {
                        permDeniedDialog.dismiss();
                    }
                    if (event.type == BaseAlertDialog.EventType.NEGATIVE_BUTTON_CLICKED) {
                        permissionManager.requestPermissions();
                    }
                    break;
                case TAG_BATTERY_DIALOG:
                    if (event.type != BaseAlertDialog.EventType.DIALOG_SHOWN) {
                        batteryDialog.dismiss();
                        pref.askDisableBatteryOptimization(false);
                    }
                    if (event.type == BaseAlertDialog.EventType.POSITIVE_BUTTON_CLICKED) {
                        Utils.requestDisableBatteryOptimization(this);
                    }
                    break;
            }
        });
        disposables.add(d);
    }

    private void showBatteryOptimizationDialog() {
        var fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(TAG_BATTERY_DIALOG) == null) {
            batteryDialog = BatteryOptimizationDialog.newInstance();
            var ft = fm.beginTransaction();
            ft.add(batteryDialog, TAG_BATTERY_DIALOG);
            ft.commitAllowingStateLoss();
        }
    }

    private void subscribeSettingsChanged() {
        invalidateOptionsMenu();
        disposables.add(pref.observeSettingsChanged().subscribe((key) -> {
            if (key.equals(getString(R.string.pref_key_browser_hide_menu_icon))) {
                invalidateOptionsMenu();
            }
        }));
    }

    private void onDrawerGroupsCreated() {
        for (int pos = 0; pos < drawerAdapter.getGroupCount(); pos++) {
            DrawerGroup group = drawerAdapter.getGroup(pos);
            if (group == null) return;

            Resources res = getResources();
            if (group.id == res.getInteger(R.integer.drawer_category_id)) {
                fragmentViewModel.setCategoryFilter(Utils.getDrawerGroupCategoryFilter(this, group.getSelectedItemId()), false);

            } else if (group.id == res.getInteger(R.integer.drawer_status_id)) {
                fragmentViewModel.setStatusFilter(Utils.getDrawerGroupStatusFilter(this, group.getSelectedItemId()), false);

            } else if (group.id == res.getInteger(R.integer.drawer_date_added_id)) {
                fragmentViewModel.setDateAddedFilter(Utils.getDrawerGroupDateAddedFilter(this, group.getSelectedItemId()), false);

            } else if (group.id == res.getInteger(R.integer.drawer_sorting_id)) {
                fragmentViewModel.setSort(Utils.getDrawerGroupItemSorting(this, group.getSelectedItemId()), false);
            }

            applyExpandState(group, pos);
        }
    }

    private void applyExpandState(DrawerGroup group, int pos) {
        if (group.getDefaultExpandState()) drawerItemManager.expandGroup(pos);
        else drawerItemManager.collapseGroup(pos);
    }

    private void saveGroupExpandState(int groupPosition, boolean expanded) {
        DrawerGroup group = drawerAdapter.getGroup(groupPosition);
        if (group == null) return;

        Resources res = getResources();
        String prefKey = null;
        if (group.id == res.getInteger(R.integer.drawer_category_id))
            prefKey = getString(R.string.drawer_category_is_expanded);

        else if (group.id == res.getInteger(R.integer.drawer_status_id))
            prefKey = getString(R.string.drawer_status_is_expanded);

        else if (group.id == res.getInteger(R.integer.drawer_date_added_id))
            prefKey = getString(R.string.drawer_time_is_expanded);

        else if (group.id == res.getInteger(R.integer.drawer_sorting_id))
            prefKey = getString(R.string.drawer_sorting_is_expanded);

        if (prefKey != null)
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(prefKey, expanded).apply();
    }

    private void onDrawerItemSelected(DrawerGroup group, DrawerGroupItem item) {
        Resources res = getResources();
        String prefKey = null;
        if (group.id == res.getInteger(R.integer.drawer_category_id)) {
            prefKey = getString(R.string.drawer_category_selected_item);
            fragmentViewModel.setCategoryFilter(Utils.getDrawerGroupCategoryFilter(this, item.getId()), true);

        } else if (group.id == res.getInteger(R.integer.drawer_status_id)) {
            prefKey = getString(R.string.drawer_status_selected_item);
            fragmentViewModel.setStatusFilter(Utils.getDrawerGroupStatusFilter(this, item.getId()), true);

        } else if (group.id == res.getInteger(R.integer.drawer_date_added_id)) {
            prefKey = getString(R.string.drawer_time_selected_item);
            fragmentViewModel.setDateAddedFilter(Utils.getDrawerGroupDateAddedFilter(this, item.getId()), true);

        } else if (group.id == res.getInteger(R.integer.drawer_sorting_id)) {
            prefKey = getString(R.string.drawer_sorting_selected_item);
            fragmentViewModel.setSort(Utils.getDrawerGroupItemSorting(this, item.getId()), true);
        }

        if (prefKey != null) saveSelectionState(prefKey, item);

        if (drawerLayout != null) drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void saveSelectionState(String prefKey, DrawerGroupItem item) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putLong(prefKey, item.getId()).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        initSearch();

        return true;
    }

    private void initSearch() {
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnCloseListener(() -> {
            fragmentViewModel.resetSearch();

            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragmentViewModel.setSearchQuery(query);
                /* Submit the search will hide the keyboard */
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fragmentViewModel.setSearchQuery(newText);

                return true;
            }
        });
        searchView.setQueryHint(getString(R.string.search));
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        /* Assumes current activity is the searchable activity */
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.browserMenu).setVisible(!pref.browserHideMenuIcon());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.pauseAllMenu) {
            pauseAll();
        } else if (itemId == R.id.resumeAllMenu) {
            resumeAll();
        } else if (itemId == R.id.settingsMenu) {
            startActivity(new Intent(this, ActivitySettings.class));
        } else if (itemId == R.id.shutdownAppMenu) {
            closeOptionsMenu();
            shutdown();
        } else if (itemId == R.id.browserMenu) {
            startActivity(new Intent(this, ActivityBrowser.class));
        } else if (itemId == R.id.rateMenu) {
            rateApp(this, getPackageName());
        } else if (itemId == R.id.moreMenu) {
            moreApp(this, "Roy93Group");
        } else if (itemId == R.id.shareAppMenu) {
            shareApp(this);
        } else if (itemId == R.id.policyMenu) {
            openBrowserPolicy(this);
        } else if (itemId == R.id.githubMenu) {
            openUrlInBrowser(this, "https://github.com/TachibanaGeneralLaboratories/download-navi");
        } else if (itemId == R.id.licenseMenu) {
            openUrlInBrowser(this, "https://raw.githubusercontent.com/TachibanaGeneralLaboratories/download-navi/master/LICENSE.md");
        } else if (itemId == R.id.helpTranslateTheAppMenu) {
            openUrlInBrowser(this, "https://hosted.weblate.org/engage/download-navi/");
        } else if (itemId == R.id.contributorsAppMenu) {
            openUrlInBrowser(this, "https://raw.githubusercontent.com/TachibanaGeneralLaboratories/download-navi/master/CONTRIBUTING.md");
        } else if (itemId == R.id.developersAppMenu) {
            openUrlInBrowser(this, "https://github.com/proninyaroslav");
        } else if (itemId == R.id.applovinConfig) {
            showMediationDebuggerApplovin(this);
        }

        return true;
    }

    private void pauseAll() {
        engine.pauseAllDownloads();
    }

    private void resumeAll() {
        engine.resumeDownloads(false);
    }

    private void showAboutDialog() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(TAG_ABOUT_DIALOG) == null) {
            aboutDialog = BaseAlertDialog.newInstance(getString(R.string.about_title), null, R.layout.dlg_dialog_about, getString(R.string.ok), getString(R.string.about_changelog), null, true);
            aboutDialog.show(fm, TAG_ABOUT_DIALOG);
        }
    }

    private void initAboutDialog() {
        if (aboutDialog == null) return;

        Dialog dialog = aboutDialog.getDialog();
        if (dialog != null) {
            TextView versionTextView = dialog.findViewById(R.id.about_version);
            TextView descriptionTextView = dialog.findViewById(R.id.aboutDescription);
            String versionName = Utils.getAppVersionName(this);
            if (versionName != null) versionTextView.setText(versionName);
            descriptionTextView.setText(Html.fromHtml(getString(R.string.about_description)));
            descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void openChangelogLink() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(getString(R.string.about_changelog_link)));
        startActivity(i);
    }

    public void shutdown() {
        Intent i = new Intent(getApplicationContext(), DownloadService.class);
        i.setAction(DownloadService.ACTION_SHUTDOWN);
        startService(i);
        finish();
    }
}
