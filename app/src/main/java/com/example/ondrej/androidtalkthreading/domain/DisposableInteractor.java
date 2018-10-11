package com.example.ondrej.androidtalkthreading.domain;

import java.util.concurrent.atomic.AtomicBoolean;

public class DisposableInteractor {
    AtomicBoolean disposed = new AtomicBoolean(false);

    public boolean isDisposed(){
        return disposed.get();
    }

    public void dispose() {
        disposed.set(true);
    }
}
