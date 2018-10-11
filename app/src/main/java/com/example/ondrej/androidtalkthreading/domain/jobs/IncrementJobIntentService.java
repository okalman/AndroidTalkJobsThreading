package com.example.ondrej.androidtalkthreading.domain.jobs;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.example.ondrej.androidtalkthreading.App;
import com.example.ondrej.androidtalkthreading.Constants;
import com.example.ondrej.androidtalkthreading.data.entity.Counter;
import com.example.ondrej.androidtalkthreading.data.room.AppDatabase;

public class IncrementJobIntentService extends JobIntentService {

    private AppDatabase appDatabase;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, IncrementJobIntentService.class, 36563, work);
    }

    @Override public void onCreate() {
        super.onCreate();
        appDatabase = App.getDatabase();
    }

    @Override protected void onHandleWork(@NonNull Intent intent) {
        Counter counter = appDatabase.counterDao().getCounter();
        ++counter.count;
        appDatabase.counterDao().insert(counter);
        getApplicationContext().sendBroadcast(Constants.getUpdateIntent());
    }
}
