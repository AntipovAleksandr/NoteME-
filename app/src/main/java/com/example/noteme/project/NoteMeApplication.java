package com.example.noteme.project;

import android.app.Application;
import android.content.Context;


public class NoteMeApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        NoteMeApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return NoteMeApplication.context;
    }
}
