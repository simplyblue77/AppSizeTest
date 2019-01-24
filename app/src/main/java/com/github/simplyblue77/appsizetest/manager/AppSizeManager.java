package com.github.simplyblue77.appsizetest.manager;

import com.github.simplyblue77.appsizetest.data.AppSizeData;
import org.jetbrains.annotations.NotNull;

public interface AppSizeManager {
    interface AppSizeDataListener {
        void onGetAppSizeDataCompleted(AppSizeData data);
    }
    void getAppSizeData(@NotNull final String packageName, @NotNull final AppSizeDataListener listener);
}
