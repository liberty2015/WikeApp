package com.liberty.wikepro.component;

import com.liberty.wikepro.view.activity.CatagoryActivity;
import com.liberty.wikepro.view.activity.CourseDetailActivity;
import com.liberty.wikepro.view.activity.CourseVideoActivity;
import com.liberty.wikepro.view.activity.ScoreActivity;
import com.liberty.wikepro.view.fragment.ChapterListFragment;
import com.liberty.wikepro.view.fragment.CourseDetailFragment;

import dagger.Component;

/**
 * Created by liberty on 2017/3/27.
 */

@Component(dependencies = ApplicationComponent.class)
public interface CourseComponent {
    void inject(CourseDetailActivity courseDetailActivity);

    void inject(CourseVideoActivity videoActivity);

    void inject(ChapterListFragment fragment);

    void inject(CourseDetailFragment fragment);

    void inject(CatagoryActivity catagoryActivity);

    void inject(ScoreActivity scoreActivity);
}
