package com.liberty.wikepro.view.widget.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liberty.libertylibrary.adapter.base.BaseHolder;
import com.liberty.libertylibrary.adapter.base.BaseRecyclerAdapter;
import com.liberty.wikepro.R;
import com.liberty.wikepro.model.bean.Catalog;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.util.ImageUtil;

/**
 * Created by LinJinFeng on 2017/2/17.
 */

public class HomePageAdapter extends BaseRecyclerAdapter<itemType> {

    private static final int TYPE_NEW=0x11;
    private static final int NORMAL=0x13;
    private static final int NORMAL_LIST=0x12;

    public HomePageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseHolder onCreateHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case NORMAL:{
                return new BaseHolder<Course>(viewGroup, R.layout.home_item) {
                    @Override
                    public void setData(Course item) {
                        super.setData(item);
                        ImageUtil.getCircleImageIntoImageView(getmContext(),
                                (ImageView) getView(R.id.courseCover),
                                item.getPdev(),false);
                        ((TextView)getView(R.id.courseTitle)).setText(item.getCname());
                        ((TextView)getView(R.id.stu_num)).setText(Integer.toString(item.getUnum()));
                    }
                };
            }
            case NORMAL_LIST:{
                return new BaseHolder<Course>(viewGroup,R.layout.home_item3) {
                    @Override
                    public void setData(Course item) {
                        super.setData(item);
//                        NestFullListView listView=getView(R.id.listView);
//                        listView.setAdpter(new HomePageNestAdapter(getmContext(),item.getCourses()));
                        ImageView coverImg=getView(R.id.cover);
                        ImageUtil.getCircleImageIntoImageView(getmContext(),coverImg,item.getPdev(),false);
                        ((TextView)getView(R.id.courseTitle)).setText(item.getCname());
                        ((TextView)getView(R.id.courseDescription)).setText(item.getDescribtion());
                        ((TextView)getView(R.id.courseCount)).setText(Integer.toString(item.getCount()));
                    }
                };
            }
            case TYPE_NEW:{
                return new BaseHolder<Catalog>(viewGroup,R.layout.type_header) {
                    @Override
                    public void setData(Catalog item) {
                        super.setData(item);
                        ((TextView)getView(R.id.typeName)).setText(item.getTitle());
                        ((ImageView)getView(R.id.typeImg)).setImageResource(item.getResId());
                    }
                };
            }
        }
        return null;
    }

    @Override
    public int getViewType(int position) {
        itemType item = getItem(position);
        if (item instanceof Catalog){
            return TYPE_NEW;
        }else if (item instanceof Course){
            Course course= (Course) item;
            if (course.getSpanCount()==1){
                return NORMAL;
            }else if (course.getSpanCount()==2){
                return NORMAL_LIST;
            }
        }
//        else if (item instanceof CourseList){
//            return NORMAL_LIST;
//        }
        return super.getViewType(position);
    }

    public class MultiSpan extends GridHeaderSpan{
        int maxCount;

        public MultiSpan(int maxCount) {
            super(maxCount);
            this.maxCount=maxCount;
        }

        @Override
        public int getSpanSize(int position) {
            int count= super.getSpanSize(position);
            Log.d("xxxxxxx","count="+count+"position="+position);
            if (count==1){
                int size=getHeaderCount();
                itemType type=getItem(size>0?position-size:position);
                if (type instanceof Catalog){
                    count=maxCount;
                }else if (type instanceof Course){
                    Course c= (Course) type;
                    if (c.getSpanCount()==maxCount){
                        count=maxCount;
                    }
                }
            }
            return count;
        }
    }

    @Override
    public MultiSpan obtainGridHeaderSpan(int maxCount) {
        return new MultiSpan(maxCount);
    }
}
