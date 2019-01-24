package com.github.simplyblue77.appsizetest.util;

import android.content.Context;
import android.os.Build;
import com.github.simplyblue77.appsizetest.manager.AppSizeManager;
import com.github.simplyblue77.appsizetest.manager.HiddenAPIAppSizeManager;
import com.github.simplyblue77.appsizetest.manager.StorageStatsAppSizeManager;
import org.jetbrains.annotations.NotNull;

public class AppSizeUtil {

    public static void getAppSizeData(@NotNull Context context,
                                      @NotNull String packageName,
                                      @NotNull AppSizeManager.AppSizeDataListener listener) {
        AppSizeManager manager = null;

        int version = Build.VERSION.SDK_INT;
        if (version > Build.VERSION_CODES.O) {
            manager = new StorageStatsAppSizeManager(context);
        } else if (version > Build.VERSION_CODES.JELLY_BEAN) {
            manager = new HiddenAPIAppSizeManager(context);
        }

        if (manager != null) {
            manager.getAppSizeData(packageName, listener);
        } else {
            listener.onGetAppSizeDataCompleted(null);
        }
    }
}
