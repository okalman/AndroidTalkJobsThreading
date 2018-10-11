package com.example.ondrej.androidtalkthreading.domain.asynctasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.ondrej.androidtalkthreading.App;
import com.example.ondrej.androidtalkthreading.data.entity.Counter;
import com.example.ondrej.androidtalkthreading.data.room.AppDatabase;
import com.example.ondrej.androidtalkthreading.domain.DisposableInteractor;

public class SetCounterTaskInteractor {

        AppDatabase database;

    public SetCounterTaskInteractor(){
        database = App.getDatabase();
    }

    @SuppressLint("StaticFieldLeak")
    public DisposableInteractor execute(Counter newCounter, SuccesCallback onSuccess, ErrorCallback onError) {
        DisposableInteractor disposable = new DisposableInteractor();

        new AsyncTask<Void, Void, Counter>() {
            @Override protected Counter doInBackground(Void... voids) {
                database.counterDao().insert(newCounter);
                return database.counterDao().getCounter();
            }

            @Override protected void onPostExecute(Counter counter) {
                super.onPostExecute(counter);
                if (!disposable.isDisposed()) {
                    onSuccess.onSuccess(counter);
                }
            }
        }.execute();

        return disposable;
    }

    public interface SuccesCallback {
        void onSuccess(Counter updatedCounter);
    }

    public interface ErrorCallback {
        void onError(Throwable error);
    }
}
