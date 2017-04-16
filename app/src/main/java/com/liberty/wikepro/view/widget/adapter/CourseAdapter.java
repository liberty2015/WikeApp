package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.net.WikeApi;
import com.liberty.wikepro.util.ImageUtil;

/**
 * Created by LinJinFeng on 2017/3/2.
 */

public class CourseAdapter extends BaseRecyclerAdapter<Course> {
    public CourseAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        return new BaseHolder<Course>(viewGroup, R.layout.course_item) {
            @Override
            public void setData(Course item) {
                super.setData(item);
                ImageUtil.getCircleImageIntoImageView(getmContext(),
                        (ImageView) getView(R.id.courseCover),
                        WikeApi.getInstance().getImageUrl(item.getPdev()),false);
                ((TextView)getView(R.id.courseTitle)).setText(item.getCname());
                ((TextView)getView(R.id.stu_num)).setText(Integer.toString(item.getUnum()));
            }
        };
    }
}
