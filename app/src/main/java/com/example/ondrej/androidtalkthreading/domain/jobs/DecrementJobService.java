package com.example.ondrej.androidtalkthreading.domain.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.example.ondrej.androidtalkthreading.App;
import com.example.ondrej.androidtalkthreading.Constants;
import com.example.ondrej.androidtalkthreading.data.entity.Counter;
import com.example.ondrej.androidtalkthreading.data.room.AppDatabase;
import com.example.ondrej.androidtalkthreading.tools.AppExecutors;

public class DecrementJobService extends JobService {


    @Override public boolean onStartJob(JobParameters params) {
        AppDatabase database = App.getDatabase();
        Counter counter = database.counterDao().getCounter();
        --counter.count;
        database.counterDao().insert(counter);
        App.getContext().sendBroadcast(Constants.getUpdateIntent());
        return true;
    }

//    @Override public boolean onStartJob(JobParameters params) {
//        Runnable task = () -> {
//            AppDatabase database = App.getDatabase();
//            Counter counter = database.counterDao().getCounter();
//            --counter.count;
//            database.counterDao().insert(counter);
//            App.getContext().sendBroadcast(Constants.getUpdateIntent());
//            jobFinished(params, false);
//        };
//        AppExecutors.getInstance().diskIO().execute(task);
//        return false;
//    }

    @Override public boolean onStopJob(JobParameters params) {
        return false;
    }
}
