package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liberty.libertylibrary.widget.NestListView.BaseNestedAdapter;
import com.liberty.libertylibrary.widget.NestListView.NestFullViewHolder;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.util.ImageUtil;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class HomePageNestAdapter extends BaseNestedAdapter<Course> {

    public HomePageNestAdapter(Context context, List<Course> mDatas) {
        super(context, mDatas);
    }

    @Override
    public void onBind(int position, NestFullViewHolder holder) {
        Course course=getmDatas().get(position);
        ImageView coverImg=holder.getView(R.id.courseCover);
        ImageUtil.getCircleImageIntoImageView(getContext(),coverImg,course.getPdev(),false);
        ((TextView)holder.getView(R.id.courseTitle)).setText(course.getCname());
        ((TextView)holder.getView(R.id.courseDescription)).setText(course.getDescribtion());
        ((TextView)holder.getView(R.id.courseCount)).setText(Integer.toString(course.getCount()));
    }

    @Override
    public View onCreateView(ViewGroup parent, int position) {
        return LayoutInflater.from(getContext()).inflate(R.layout.home_item3,parent,false);
    }

    @Override
    public int getItemCount() {
        return getmDatas().size();
    }
}
