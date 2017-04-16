package com.liberty.wikepro.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liberty on 2017/3/14.
 */

@Module
public class ApplicationModule {
    Application mApplication;
    Context context;

    public ApplicationModule(Application application){
        mApplication=application;
        context=application;
    }

    @Provides
    public Application provideApplication(){
        return mApplication;
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
