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
 * Created by liberty on 2017/4/16.
 */

public class SearchAdapter extends BaseRecyclerAdapter<Course> {
    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
//        switch (viewType){
//            case RECENT:{
//                return new BaseHolder<Search>(viewGroup, R.layout.recent_item) {
//                    @Override
//                    public void setData(Search item) {
//                        super.setData(item);
//                        ((TextView)getView(R.id.query)).setText(item.getQuery());
//                    }
//                };
//            }
//            case SEARCH:{
                return new BaseHolder<Course>(viewGroup,R.layout.home_item3) {
                    @Override
                    public void setData(Course item) {
                        super.setData(item);
                        ImageView coverImg=getView(R.id.cover);
                        ImageUtil.getCircleImageIntoImageView(getmContext(),coverImg,
                               item.getPdev(),false);
                        ((TextView)getView(R.id.courseTitle)).setText(item.getSearchElement().getSearchSpannable(item.getCname()));
                        ((TextView)getView(R.id.courseDescription)).setText(item.getSearchElement().getSearchSpannable(item.getDescribtion()));
                        ((TextView)getView(R.id.courseCount)).setText(item.getUnum()+"人学习");
                    }
                };
//            }
//        }
//        return null;
    }

//    @Override
//    public int getViewType(int position) {
//        itemType item = getItem(position);
//        if (item instanceof Search){
//            return RECENT;
//        }else if (item instanceof Course){
//            return SEARCH;
//        }
//        return super.getViewType(position);
//    }
//
//    private final static int RECENT=0x11;
//    private final static int SEARCH=0x12;
}
