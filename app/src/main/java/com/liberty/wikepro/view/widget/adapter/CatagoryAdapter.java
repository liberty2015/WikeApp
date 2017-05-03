package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.util.ImageUtil;

/**
 * Created by liberty on 2017/4/3.
 */

public class CatagoryAdapter extends BaseRecyclerAdapter<Course> {


    public CatagoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        return new BaseHolder<Course>(viewGroup, R.layout.home_item3) {
            @Override
            public void setData(Course item) {
                super.setData(item);
                ImageView coverImg=getView(R.id.cover);
                ImageUtil.getCircleImageIntoImageView(getmContext(),coverImg,
                        item.getPdev(),false);
                ((TextView)getView(R.id.courseTitle)).setText(item.getCname());
                ((TextView)getView(R.id.courseDescription)).setText(item.getDescribtion());
                ((TextView)getView(R.id.courseCount)).setText(item.getUnum()+"人学习");
            }
        };
    }
}
