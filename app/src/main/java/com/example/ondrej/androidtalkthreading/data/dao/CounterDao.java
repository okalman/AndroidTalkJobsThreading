package com.example.ondrej.androidtalkthreading.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.ondrej.androidtalkthreading.data.entity.Counter;

@Dao
public interface CounterDao {

    @Query("SELECT * FROM Counter where id==\"PK\" LIMIT 1 ")
    Counter getCounter();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Counter newValue);
}
