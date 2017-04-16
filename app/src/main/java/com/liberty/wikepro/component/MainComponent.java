package com.liberty.wikepro.component;

import com.liberty.wikepro.view.activity.HistoryActivity;
import com.liberty.wikepro.view.activity.MyCourseActivity;
import com.liberty.wikepro.view.fragment.HomePageFragment;
import com.liberty.wikepro.view.fragment.KindFragment;
import com.liberty.wikepro.view.fragment.MineFragment;

import dagger.Component;

/**
 * Created by liberty on 2017/3/18.
 */

@Component(dependencies = ApplicationComponent.class)
public interface MainComponent {

    void inject(HomePageFragment pageFragment);

    void inject(KindFragment kindFragment);

    void inject(MineFragment mineFragment);

    void inject(MyCourseActivity myCourseActivity);

    void inject(HistoryActivity historyActivity);
}
