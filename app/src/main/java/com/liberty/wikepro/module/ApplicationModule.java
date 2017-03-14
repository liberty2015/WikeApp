package com.liberty.wikepro.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liberty on 2017/3/14.
 */

@Module
public class ApplicationModule {
    Application mApplication;

    public ApplicationModule(Application application){
        mApplication=application;
    }

    @Provides
    Application providesApplication(){
        return mApplication;
    }
}
