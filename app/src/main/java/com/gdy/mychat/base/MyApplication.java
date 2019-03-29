package com.gdy.mychat.base;

import android.app.Application;

import com.gdy.mychat.utils.Global;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Global.init(this);
    }
}
