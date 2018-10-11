package com.example.ondrej.androidtalkthreading;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.ondrej.androidtalkthreading.data.room.AppDatabase;

public class App extends Application {

    private static AppDatabase appDatabase;
    private static App appInstance;

    public static AppDatabase getDatabase() {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(appInstance, AppDatabase.class, "database-name").build();
        }
        return appDatabase;
    }

    public static Context getContext() {
        return appInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }
}
