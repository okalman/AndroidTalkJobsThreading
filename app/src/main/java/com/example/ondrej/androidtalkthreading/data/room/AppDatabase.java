package com.example.ondrej.androidtalkthreading.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ondrej.androidtalkthreading.data.dao.CounterDao;
import com.example.ondrej.androidtalkthreading.data.entity.Counter;

@Database(entities = {Counter.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CounterDao counterDao();
}
