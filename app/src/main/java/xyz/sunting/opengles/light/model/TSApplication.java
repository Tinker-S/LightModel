package xyz.sunting.opengles.light.model;

import android.app.Application;

public class TSApplication extends Application {
    private static TSApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static TSApplication getInstance() {
        return sInstance;
    }
}