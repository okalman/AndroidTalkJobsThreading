package com.example.ondrej.androidtalkthreading.ui;

import android.content.Intent;
import android.util.Log;

import com.example.ondrej.androidtalkthreading.App;
import com.example.ondrej.androidtalkthreading.domain.DisposableInteractor;
import com.example.ondrej.androidtalkthreading.domain.asynctasks.GetCounterTaskInteractor;
import com.example.ondrej.androidtalkthreading.domain.asynctasks.SetCounterTaskInteractor;
import com.example.ondrej.androidtalkthreading.domain.jobs.IncrementJobIntentService;
import com.example.ondrej.androidtalkthreading.domain.runnables.GetCounterRunnableInteractor;
import com.example.ondrej.androidtalkthreading.domain.runnables.SetCounterRunnableInteractor;

class MainPresenter {

    private MainView view;

    private GetCounterRunnableInteractor getCounterRunnableInteractor;
    private SetCounterRunnableInteractor setCounterRunnableInteractor;
    private GetCounterTaskInteractor getCounterTaskInteractor;
    private SetCounterTaskInteractor setCounterTaskInteractor;
    private DisposableInteractor getCounterInteractorDisposable = new DisposableInteractor();
    private DisposableInteractor setCounterInteractorDisposable = new DisposableInteractor();

     void attachView(MainView view) {
        this.view = view;
        getCounterRunnableInteractor = new GetCounterRunnableInteractor();
        setCounterRunnableInteractor = new SetCounterRunnableInteractor();
        getCounterTaskInteractor = new GetCounterTaskInteractor();
        setCounterTaskInteractor = new SetCounterTaskInteractor();
    }

     void detachView() {
        getCounterInteractorDisposable.dispose();
        setCounterInteractorDisposable.dispose();
        view = null;
    }


     void onPlusRunnable() {
         getCounterInteractorDisposable.dispose();
         setCounterInteractorDisposable.dispose();
        getCounterInteractorDisposable = getCounterRunnableInteractor.execute(data -> {
            ++data.count;
            setCounterInteractorDisposable = setCounterRunnableInteractor.execute(data,
                    updatedData -> view.updateCounter(updatedData.count),
                    error -> Log.e("TAG", "e", error));
        }, error -> Log.e("TAG", "e", error));

    }

     void onMinusRunnable() {
         getCounterInteractorDisposable.dispose();
         setCounterInteractorDisposable.dispose();
        getCounterInteractorDisposable = getCounterRunnableInteractor.execute(data -> {
            --data.count;
            setCounterRunnableInteractor.execute(data,
                    updatedData -> view.updateCounter(updatedData.count),
                    error -> Log.e("TAG", "e", error));
        }, error -> Log.e("TAG", "e", error));
    }

     void onPlusAsyncTask() {
         getCounterInteractorDisposable.dispose();
         setCounterInteractorDisposable.dispose();
         getCounterInteractorDisposable = getCounterTaskInteractor.execute(data -> {
             ++data.count;
             setCounterInteractorDisposable = setCounterTaskInteractor.execute(data,
                     updatedData -> view.updateCounter(updatedData.count),
                     error -> Log.e("TAG", "e", error));
         }, error -> Log.e("TAG", "e", error));
    }

     void onMinusAsyncTask() {
         getCounterInteractorDisposable.dispose();
         setCounterInteractorDisposable.dispose();
         getCounterInteractorDisposable = getCounterTaskInteractor.execute(data -> {
             --data.count;
             setCounterInteractorDisposable = setCounterTaskInteractor.execute(data,
                     updatedData -> view.updateCounter(updatedData.count),
                     error -> Log.e("TAG", "e", error));
         }, error -> Log.e("TAG", "e", error));
    }

     void onPlusJobSched() {
         IncrementJobIntentService.enqueueWork(App.getContext(), new Intent());
    }

     void onMinusJobSched() {
        view.scheduleDecrementJob();
    }

    public void updateFromDb() {
         getCounterInteractorDisposable.dispose();
         getCounterRunnableInteractor.execute(data -> view.updateCounter(data.count),
                 error -> Log.e("TAG", "e", error));
    }
}
