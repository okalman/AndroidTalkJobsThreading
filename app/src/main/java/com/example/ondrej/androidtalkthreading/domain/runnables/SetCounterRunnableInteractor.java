package com.example.ondrej.androidtalkthreading.domain.runnables;

import com.example.ondrej.androidtalkthreading.App;
import com.example.ondrej.androidtalkthreading.data.entity.Counter;
import com.example.ondrej.androidtalkthreading.data.room.AppDatabase;
import com.example.ondrej.androidtalkthreading.domain.DisposableInteractor;
import com.example.ondrej.androidtalkthreading.tools.AppExecutors;

public class SetCounterRunnableInteractor{

    private AppExecutors executors;
    private AppDatabase database;

    public SetCounterRunnableInteractor(){
        executors = AppExecutors.getInstance();
        database = App.getDatabase();
    }


    public DisposableInteractor execute(Counter newCounter, SuccesCallback onSuccess, ErrorCallback onError){
        DisposableInteractor disposable = new DisposableInteractor();
        Runnable backgroundTask = () -> {
            try {
                database.counterDao().insert(newCounter);
                Counter updatedCounter = database.counterDao().getCounter();

                if(!disposable.isDisposed()) {
                    executors.mainThread().execute(() -> onSuccess.onSuccess(updatedCounter));
                }

            }catch (Exception e){
                if(!disposable.isDisposed()) {
                    executors.mainThread().execute(() -> onError.onError(e));
                }
            }
        };
        executors.diskIO().execute(backgroundTask);
        return disposable;
    }

    public interface SuccesCallback {
        void onSuccess(Counter updatedCounter);
    }

    public interface ErrorCallback {
        void onError(Throwable error);
    }

}
