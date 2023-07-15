package com.roy.downloader;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.roy.downloader.core.DownloadNotifier;
import com.roy.downloader.ui.errorreport.ActivityErrorReport;

import org.acra.ACRA;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.DialogConfigurationBuilder;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.data.StringFormat;

//TODO firebase analytic

//TODO ic_launcher
//TODO proguard
//TODO keystore
//TODO rename app
//TODO rate, more app, share app
//TODO leak canary
//TODO policy
//TODO ad applovin
//done

public class MainApplication extends MultiDexApplication {
    public static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        setupApp();
    }

    private void setupApp() {
        CoreConfigurationBuilder builder = new CoreConfigurationBuilder();
        builder
                .withBuildConfigClass(BuildConfig.class)
                .withReportFormat(StringFormat.JSON);
        builder.withPluginConfigurations(new MailSenderConfigurationBuilder()
                .withMailTo("roy93group@gmail.com")
                .build());
        builder.withPluginConfigurations(new DialogConfigurationBuilder()
                .withEnabled(true)
                .withReportDialogClass(ActivityErrorReport.class)
                .build());
        if (Thread.getDefaultUncaughtExceptionHandler() == null) {
            Thread.setDefaultUncaughtExceptionHandler((t, e) ->
                    Log.e(TAG, "Uncaught exception in " + t + ": " + Log.getStackTraceString(e))
            );
        }
        ACRA.init(this, builder);

        DownloadNotifier downloadNotifier = DownloadNotifier.getInstance(this);
        downloadNotifier.makeNotifyChans();
        downloadNotifier.startUpdate();
    }
}
