package xyz.sunting.opengles.light.model;

import android.app.Application;

public class TSApplication extends Application {
    private static TSApplication sInstance;

    public static TSApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }
}