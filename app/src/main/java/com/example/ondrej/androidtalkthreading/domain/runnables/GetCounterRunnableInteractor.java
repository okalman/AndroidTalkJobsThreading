package com.example.ondrej.androidtalkthreading.domain.runnables;

import com.example.ondrej.androidtalkthreading.App;
import com.example.ondrej.androidtalkthreading.data.entity.Counter;
import com.example.ondrej.androidtalkthreading.data.room.AppDatabase;
import com.example.ondrej.androidtalkthreading.domain.DisposableInteractor;
import com.example.ondrej.androidtalkthreading.tools.AppExecutors;

public class GetCounterRunnableInteractor {

    private AppExecutors executors;
    private AppDatabase database;


    public GetCounterRunnableInteractor(){
        executors = AppExecutors.getInstance();
        database = App.getDatabase();
    }


    public DisposableInteractor execute(SuccesCallback onSuccess, ErrorCallback onError){
        DisposableInteractor disposable = new DisposableInteractor();
        Runnable backgroundTask = () -> {
            Counter counter = database.counterDao().getCounter();
            executors.mainThread().execute(() -> {if(counter != null){
                if(!disposable.isDisposed()){
                    onSuccess.onSuccess(counter);
                }
            }else {
                if(!disposable.isDisposed()) {
                    onSuccess.onSuccess(new Counter());
                }
            }});
        };
        executors.diskIO().execute(backgroundTask);
        return disposable;
    }

    public interface SuccesCallback {
         void onSuccess(Counter counter);
    }

    public interface ErrorCallback {
        void onError(Throwable error);
    }
}
