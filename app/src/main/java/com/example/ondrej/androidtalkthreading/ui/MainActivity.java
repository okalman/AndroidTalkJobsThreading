package com.example.ondrej.androidtalkthreading.ui;

import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.ondrej.androidtalkthreading.Constants;
import com.example.ondrej.androidtalkthreading.R;
import com.example.ondrej.androidtalkthreading.tools.JobInfoHelper;

public class MainActivity extends AppCompatActivity implements MainView {

    private Button plusRunnable;
    private Button minusRunnable;
    private Button plusAsyncTask;
    private Button minusAsyncTask;
    private Button plusJobSched;
    private Button minusJobSched;
    private TextView counter;

    private MainPresenter presenter = new MainPresenter();
    private IntentFilter filter = new IntentFilter(Constants.ACTION_UPDATE);
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            presenter.updateFromDb();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setClickListeners();
        presenter.attachView(this);
    }

    @Override protected void onStart() {
        super.onStart();
        registerReceiver(receiver, filter);
    }

    @Override protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override public void  updateCounter(Integer count){
        counter.setText(count.toString());
    }

    @Override public void scheduleDecrementJob() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(JobInfoHelper.getDecrementJob(this));
    }

    private void setClickListeners() {
        plusRunnable.setOnClickListener(view -> presenter.onPlusRunnable());
        minusRunnable.setOnClickListener(view -> presenter.onMinusRunnable());
        plusAsyncTask.setOnClickListener(view -> presenter.onPlusAsyncTask());
        minusAsyncTask.setOnClickListener(view -> presenter.onMinusAsyncTask());
        plusJobSched.setOnClickListener(view -> presenter.onPlusJobSched());
        minusJobSched.setOnClickListener(view -> presenter.onMinusJobSched());
    }

    private void findViews() {
        plusRunnable = findViewById(R.id.plusRunnable);
        minusRunnable = findViewById(R.id.minusRunnable);
        plusAsyncTask = findViewById(R.id.plusAsyncTask);
        minusAsyncTask = findViewById(R.id.minusAsyncTask);
        plusJobSched = findViewById(R.id.plusJob);
        minusJobSched = findViewById(R.id.minusJob);
        counter = findViewById(R.id.counter);
    }

}
