package com.github.simplyblue77.appsizetest.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import com.github.simplyblue77.appsizetest.data.AppSizeData;
import com.github.simplyblue77.appsizetest.manager.AppSizeManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppSizeUtilTest {

    @Test
    public void getAppSizeData() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        AppSizeUtil.getAppSizeData(appContext, appContext.getPackageName(), new AppSizeManager.AppSizeDataListener() {
            @Override
            public void onGetAppSizeDataCompleted(AppSizeData data) {
                System.out.println("totalSize : " + data.getTotalSize());
                assertNotNull(data);
            }
        });
    }

    @Test
    public void getAppSizeDataOtherPackage() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        AppSizeUtil.getAppSizeData(appContext, "com.kakao.talk", new AppSizeManager.AppSizeDataListener() {
            @Override
            public void onGetAppSizeDataCompleted(AppSizeData data) {
                System.out.println("totalSize : " + data.getTotalSize());
                assertNotNull(data);
            }
        });
    }
}