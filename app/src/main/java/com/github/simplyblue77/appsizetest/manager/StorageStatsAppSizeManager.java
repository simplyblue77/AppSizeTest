package com.github.simplyblue77.appsizetest.manager;

import android.annotation.SuppressLint;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.support.annotation.RequiresApi;
import com.github.simplyblue77.appsizetest.data.AppSizeData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public class StorageStatsAppSizeManager implements AppSizeManager {

    private Context context;

    public StorageStatsAppSizeManager(Context context) {
        this.context = context;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getAppSizeData(@NotNull final String packageName, @NotNull final AppSizeDataListener listener) {
        AppSizeData appSizeData = null;
        StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);
        if (storageStatsManager != null) {
            UserHandle userHandle = Process.myUserHandle();
            UUID uuid = StorageManager.UUID_DEFAULT;
            try {
                StorageStats storageStats = storageStatsManager.queryStatsForPackage(uuid, packageName, userHandle);
                if (storageStats != null) {
                    appSizeData = new AppSizeData(storageStats.getCacheBytes(), storageStats.getDataBytes(), storageStats.getAppBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        listener.onGetAppSizeDataCompleted(appSizeData);
    }
}
