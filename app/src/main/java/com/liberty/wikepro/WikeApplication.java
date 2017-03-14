package com.liberty.wikepro;

import android.app.Application;

import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerApplicationComponent;
import com.liberty.wikepro.module.ApplicationModule;
import com.liberty.wikepro.util.NetStateReceiver;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class WikeApplication extends Application {

    private static WikeApplication instance;
    public ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mAppComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(instance))
                .build();
        NetStateReceiver.registerNetworkStateReceiver(instance);
    }

    @Override
    public void onLowMemory() {
        if (instance!=null){
            NetStateReceiver.unRegisterNetworkStateReceiver(instance);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        super.onLowMemory();
    }

    public static WikeApplication getInstance() {
        return instance;
    }
}
