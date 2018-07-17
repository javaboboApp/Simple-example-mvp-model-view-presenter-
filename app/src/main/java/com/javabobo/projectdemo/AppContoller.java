package com.javabobo.projectdemo;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

/**
 * Created by luis on 15/02/2018.
 */

public class AppContoller extends Application {
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static AppContoller getController() {
        return (AppContoller) getMContext();
    }


    public static Context getMContext() {
        return mContext;
    }

}
