package com.github.simplyblue77.appsizetest.manager;

import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import com.github.simplyblue77.appsizetest.data.AppSizeData;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HiddenAPIAppSizeManager implements AppSizeManager {

    private Context context;

    public HiddenAPIAppSizeManager(Context context) {
        this.context = context;
    }

    @Override
    public void getAppSizeData(@NotNull final String packageName, @NotNull final AppSizeDataListener listener) {
        PackageManager pm = context.getPackageManager();

        Method getPackageSizeInfo = null;
        try {
            getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (getPackageSizeInfo != null) {
            try {
                getPackageSizeInfo.invoke(pm, packageName, new IPackageStatsObserver.Stub() {
                    @Override
                    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) {
                        if (listener != null) {
                            AppSizeData appSizeData = null;
                            if (pStats != null && succeeded) {
                                appSizeData = new AppSizeData(pStats.cacheSize, pStats.dataSize, pStats.codeSize);
                            }
                            listener.onGetAppSizeDataCompleted(appSizeData);
                        }
                    }
                });
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (listener != null) {
            listener.onGetAppSizeDataCompleted(null);
        }
    }
}
