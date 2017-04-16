package com.liberty.wikepro.component;

import android.content.Context;

import com.liberty.wikepro.module.ApplicationModule;

import dagger.Component;

/**
 * Created by liberty on 2017/3/14.
 */

@Component(
    modules = ApplicationModule.class
)
public interface ApplicationComponent {

    Context getContext();
}
