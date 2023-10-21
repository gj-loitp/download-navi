package com.roy.downloader;

import static com.roy.downloader.ext.ApplovinKt.setupApplovinAd;

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
//link tai apk, can tim cach de nhan vao item thi co the install dc apk
//https://d-11.winudf.com/custom/com.apkpure.aegon-3192247.apk?_fn=QVBLUHVyZV92My4xOS4yMl9hcGtwdXJlLmNvbS5hcGs&_p=Y29tLmFwa3B1cmUuYWVnb24&am=C3PIm0KTpOuOQlATfuWS1Q&arg=apkpure%3A%2F%2Fcampaign%2F%3Futm_medium%3Dapkpure%26utm_source%3Ddetails%26report_context%3D%7B%22id%22%3A%221398309105%22%2C%22channel_id%22%3A%221001%22%7D&at=1689433980&download_id=1994907656740157&k=059100da14636c98de905b47542b59cf64b40942&r=https%3A%2F%2Fapkpure.com%2Fvn%2Fapkpure%2Fcom.apkpure.aegon&uu=http%3A%2F%2F172.16.60.1%2Fcustom%2Fcom.apkpure.aegon-3192247.apk%3Fk%3Db3f3458e88a7f095b2baf13a5ff6228764b40942
//TODO add background animation
//TODO why you see ad
//TODO custom button selector

//done
//ic_launcher
//proguard
//leak canary
//rate, more app, share app
//policy
//keystore
//rename app
//github
//license
//ad applovin
//vung bi mat de show applovin config

public class MainApplication extends MultiDexApplication {
    public static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        setupApp();
    }

    private void setupApp() {
        setupApplovinAd(this);
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
