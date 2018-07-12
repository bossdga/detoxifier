package com.testbirds.detoxifier.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


public class BlacklistService extends IntentService {

    public BlacklistService() {
        super("BlacklistService");
    }

    public BlacklistService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("::: Service onHandleWork");
    }

}