package com.liberty.wikepro;

import android.app.Application;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class WikeApplication extends Application {

    private static WikeApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }



    public static WikeApplication getInstance() {
        return instance;
    }
}
