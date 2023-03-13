package com.toksaitov.sms;

import android.app.Application;
import android.content.Context;

public class SnakeApplication extends Application {
    private static SnakeApplication _application;

    @Override
    public void onCreate() {
        super.onCreate();
        _application = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        _application = null;
    }

    public static Context getContext() {
        return _application.getApplicationContext();
    }
}
