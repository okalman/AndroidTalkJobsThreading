package com.example.ondrej.androidtalkthreading.tools;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;

import com.example.ondrej.androidtalkthreading.domain.jobs.DecrementJobService;

import java.util.Random;

public class JobInfoHelper {

    public static JobInfo getDecrementJob(Context context){
        JobInfo.Builder builder = new JobInfo.Builder(new Random().nextInt(), new ComponentName(context, DecrementJobService.class));
        builder.setPersisted(false);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        return builder.build();

    }
}
