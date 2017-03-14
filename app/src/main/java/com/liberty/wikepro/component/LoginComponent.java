package com.liberty.wikepro.component;

import com.liberty.wikepro.view.activity.LoginActivity;

import dagger.Component;

/**
 * Created by liberty on 2017/3/14.
 */

@Component(dependencies = ApplicationComponent.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
