package com.liberty.libertylibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liberty.libertylibrary.R;
import com.liberty.libertylibrary.widget.Indicator.CircleIndicator;

import java.util.List;

/**
 * Created by LinJinFeng on 2016/9/12.
 */

public class BannerContainer extends RelativeLayout {

    private Context mContext;
    private BannerViewPager banner;
    private CircleIndicator indicator;
    private TextView imageTitle;

    public BannerContainer(Context context) {
        this(context,null);
    }

    public BannerContainer(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init();
    }

    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.banner_layout,this,true);
//        if (isInEditMode())return;
        banner= (BannerViewPager) findViewById(R.id.banner);
        indicator= (CircleIndicator) findViewById(R.id.indicator);
        imageTitle= (TextView) findViewById(R.id.imageTitle);
        banner.bindTitle(imageTitle);
        banner.bindPagerIndicator(indicator);
    }

    public void setBanner(Banner bannerBean){
        banner.setBanner(mContext,bannerBean);
    }

    public void setImageList(List<String> urlList){
        banner.setImageListWithUrl(mContext,urlList);
        banner.bindPagerIndicator(indicator);
    }

    public void setBannerItemClick(BannerViewPager.OnItemClickListener onItemClickListener){
        banner.setItemClickListener(onItemClickListener);
    }

    public void setImageIdList(List<Integer> idList){
        banner.setImageList(mContext,idList);
    }

}
