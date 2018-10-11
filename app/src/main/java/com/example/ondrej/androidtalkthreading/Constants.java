package com.example.ondrej.androidtalkthreading;

import android.content.Intent;

public class Constants {
    public static String ACTION_UPDATE = "update";
    public static Intent getUpdateIntent(){
        return new Intent().setAction(ACTION_UPDATE);
    }
}
