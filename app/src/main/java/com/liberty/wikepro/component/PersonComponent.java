package com.liberty.wikepro.component;

import com.liberty.wikepro.view.activity.PersonActivity;

import dagger.Component;

/**
 * Created by liberty on 2017/3/16.
 */
@Component(dependencies = ApplicationComponent.class)
public interface PersonComponent {
    void inject(PersonActivity personActivity);

}
