package com.example.ondrej.androidtalkthreading.domain.asynctasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.ondrej.androidtalkthreading.App;
import com.example.ondrej.androidtalkthreading.data.entity.Counter;
import com.example.ondrej.androidtalkthreading.data.room.AppDatabase;
import com.example.ondrej.androidtalkthreading.domain.DisposableInteractor;

public class GetCounterTaskInteractor {

    private AppDatabase database;

    public GetCounterTaskInteractor() {
        database = App.getDatabase();
    }


    @SuppressLint("StaticFieldLeak")
    public DisposableInteractor execute(SuccesCallback onSuccess, ErrorCallback onError) {
        DisposableInteractor disposable = new DisposableInteractor();

        new AsyncTask<Void, Void, Counter>() {
            @Override protected Counter doInBackground(Void... voids) {
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
        void onSuccess(Counter counter);
    }

    public interface ErrorCallback {
        void onError(Throwable error);
    }

}
