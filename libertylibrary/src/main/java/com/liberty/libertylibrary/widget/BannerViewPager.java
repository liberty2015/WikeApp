package com.liberty.libertylibrary.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.liberty.libertylibrary.widget.Indicator.CircleIndicator;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */

public class BannerViewPager extends ViewPager {

    private Context mContext;
    private int duration;
    private CircleIndicator indicator;
    private TextView title;
    private List<ImageView> imageList;
    private List<String > titleList;
    private Banner banner;
    private List<Banner.photos> photoses;
    private OnItemClickListener itemClickListener;

    /**
     * 判断手指是否正在滑动ViewPager
     */
    private boolean isScroll=false;
    private boolean isMoving=false;

    private Thread moveThread=new Thread(){
        @Override
        public void run() {
            while (true){
                try {
                    sleep(duration);
                    if (!isMoving&&!isScroll){
                        handler.sendEmptyMessage(0x11);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    int position=BannerViewPager.this.getCurrentItem();
                    BannerViewPager.this.setCurrentItem(position+1,true);
                    break;
            }
        }
    };


    public interface OnItemClickListener{
        public void onItemClick(Banner banner,int position);
    }

    public BannerViewPager(Context context) {
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        this.setOffscreenPageLimit(3);
        initView();
    }

    private void initView(){
        duration=4000;
        this.setAdapter(new BannerPagerAdapter());
        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (indicator!=null){
                    indicator.setmCurrentPage(position);
                }
                setTitle(position);
            }

            @Override
            public void onPageSelected(int position) {
                isScroll=false;
                if (indicator!=null){
                    indicator.setmCurrentPage(position);
                }
                setTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state== ViewPager.SCROLL_STATE_IDLE){
                    int length=imageList.size();
                    int position=BannerViewPager.this.getCurrentItem();
                    if (position==0){
                        BannerViewPager.this.setCurrentItem(length-2,false);
                    }else if (position==length-1){
                        BannerViewPager.this.setCurrentItem(1,false);
                    }
                }
                isScroll=state!= ViewPager.SCROLL_STATE_IDLE;
            }
        });

        try {
            Field field=ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            MyScroller scroller=new MyScroller(mContext);
            scroller.setmDuration(1200);
            field.set(this,scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        moveThread.start();
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isMoving=false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isMoving=false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isMoving=true;
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!moveThread.isAlive()){
            moveThread.start();
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private void setTitle(int position){
        if (banner==null||photoses==null){
            return;
        }
        if (position==0){
            this.title.setText(photoses.get(photoses.size()-1).getName());
        }else if (position==getAdapter().getCount()-1){
            this.title.setText(photoses.get(0).getName());
        }else {
            this.title.setText(photoses.get(position-1).getName());
        }
    }

    public BannerViewPager setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public BannerViewPager setImageList(Context context, List<Integer> idList){
        if (imageList!=null){
            imageList.clear();
        }else {
            imageList=new ArrayList<>();
        }
        for (int id:idList){
            ImageView imageView=new ImageView(mContext);
            ViewGroup.LayoutParams params=getLayoutParams();
            params.width= ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            imageView.setImageResource(id);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageView);
        }
        int length=idList.size();
        ImageView first=new ImageView(mContext);
        ViewGroup.LayoutParams params=getLayoutParams();
        params.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        first.setImageResource(idList.get(length-1));
        first.setLayoutParams(params);
        first.setScaleType(ImageView.ScaleType.FIT_XY);
        imageList.add(0,first);
        ImageView last=new ImageView(mContext);
        last.setImageResource(idList.get(0));
        last.setLayoutParams(params);
        last.setScaleType(ImageView.ScaleType.FIT_XY);
        imageList.add(last);
        initView();
        return this;
    }

    public BannerViewPager setImageListWithUrl(Context context, List<String> urlList){
        if (imageList!=null){
            imageList.clear();
        }else {
            imageList=new ArrayList<>();
        }
        for (String url:urlList){
            ImageView imageView=new ImageView(mContext);
            ViewGroup.LayoutParams params=getLayoutParams();
            params.width= ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            Picasso.with(context).load(url).into(imageView);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageView);
        }
        int length=urlList.size();
        ImageView first=new ImageView(mContext);
        ViewGroup.LayoutParams params=getLayoutParams();
        params.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        Picasso.with(context).load(urlList.get(length-1)).into(first);
        first.setLayoutParams(params);
        first.setScaleType(ImageView.ScaleType.FIT_XY);
        imageList.add(0,first);
        ImageView last=new ImageView(mContext);
        Picasso.with(context).load(urlList.get(0)).into(last);
        last.setLayoutParams(params);
        last.setScaleType(ImageView.ScaleType.FIT_XY);
        imageList.add(last);
        initView();
        return this;
    }

    public void setTitleList(List<String> titleList) {
        if (this.titleList!=null){
            this.titleList.clear();
            this.titleList.addAll(titleList);
        }else {
            this.titleList = titleList;
        }
    }

    /**
     * 设置Banner轮播数据类
     * @param context
     * @param banner
     */
    public void setBanner(Context context, Banner banner) {
        if (banner==null){
            return;
        }
        this.banner = banner;
        if (photoses!=null){
            photoses.clear();
        }else {
            photoses=new ArrayList<>();
        }
        photoses.addAll(banner.getPhotos());
        if (imageList!=null){
            imageList.clear();
        }else {
            imageList=new ArrayList<>();
        }
        List<Banner.photos> photoses=banner.getPhotos();
        int head=0,prev=0;
        for (Banner.photos photos:photoses){
            ImageView imageView=new ImageView(mContext);
            ViewGroup.LayoutParams params=getLayoutParams();
            params.width= ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            Picasso.with(context).load(photos.getPhotoUrl()).into(imageView);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageView);
            prev++;
        }
        int length=photoses.size();
        ImageView first=new ImageView(mContext);
        ViewGroup.LayoutParams params=getLayoutParams();
        params.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        Picasso.with(context).load(photoses.get(length-1).getPhotoUrl()).into(first);
        first.setLayoutParams(params);
        first.setScaleType(ImageView.ScaleType.FIT_XY);
        imageList.add(0,first);
        ImageView last=new ImageView(mContext);
        Picasso.with(context).load(photoses.get(0).getPhotoUrl()).into(last);
        last.setLayoutParams(params);
        last.setScaleType(ImageView.ScaleType.FIT_XY);
        imageList.add(last);
        for (int position=0;position<imageList.size();position++){
            final int finalPosition;
            if (position==0){
                finalPosition=prev;
            }else if (position==imageList.size()-1){
                finalPosition=head;
            }else {
                finalPosition=position-1;
            }
            imageList.get(position).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(BannerViewPager.this.banner,finalPosition);
                    }
                }
            });
        }
        getAdapter().notifyDataSetChanged();
        this.indicator.setmPageCount(this.getAdapter().getCount());
        this.setCurrentItem(1);
        indicator.notifyDataSetChanged();
    }

    public void bindPagerIndicator(CircleIndicator indicator){
        this.indicator=indicator;

    }

    public void bindTitle(TextView title){
        this.title=title;
    }

    private class MyScroller extends Scroller {

        private int mDuration=1200;
        public MyScroller(Context context) {
            super(context);
        }

        public MyScroller(Context context, Interpolator interpolator){
            super(context,interpolator);
        }

        public void setmDuration(int mDuration) {
            this.mDuration = mDuration;
        }

        public int getmDuration() {
            return mDuration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy,mDuration);
        }
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList!=null?imageList.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object ;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=imageList.get(position);
            if (imageView.getParent()==null){
                container.addView(imageList.get(position));
            }
            return imageList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(imageList.get(position));
        }
    }

    @Override
    protected void detachAllViewsFromParent() {
        super.detachAllViewsFromParent();
    }
}
