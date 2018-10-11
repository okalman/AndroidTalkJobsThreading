package com.example.ondrej.androidtalkthreading.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Counter {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String id = "PK";

    public Integer count = 0;

}
