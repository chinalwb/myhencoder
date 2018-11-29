package com.chinalwb.threadproject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class MyIntentService extends IntentService {

    private String name;
    public MyIntentService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
