package com.example.aotedan;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class Boot extends Application {
    @Override
    public  void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}
