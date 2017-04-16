package com.liberty.wikepro.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.liberty.wikepro.R;

/**
 * Created by liberty on 2017/3/24.
 */

public class RecommendView extends RelativeLayout implements View.OnClickListener{

    private AppCompatImageView kindImg,right;

    private Drawable shape,shape_selected;

    private boolean checked;

    private ValueAnimator trueAnimator,falseAnimator;

    public RecommendView(Context context) {
        this(context,null);
    }

    public RecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.recommend_view,this,true);
        kindImg= (AppCompatImageView) findViewById(R.id.kind_img);
        right= (AppCompatImageView) findViewById(R.id.right);
        right.setScaleX(0f);
        right.setScaleY(0f);
        shape=getResources().getDrawable(R.drawable.recommend_shape);
        shape.mutate();
        setBackground(shape);
        shape_selected=getResources().getDrawable(R.drawable.recommend_shape_selected);
        shape_selected.mutate();
        setOnClickListener(this);
        trueAnimator= ValueAnimator.ofFloat(0.0f,1.0f);
        trueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction=animation.getAnimatedFraction();
                right.setScaleX(fraction);
                right.setScaleY(fraction);
            }
        });
    }

    public void setImgBitmap(Bitmap bitmap){
        kindImg.setImageBitmap(bitmap);
    }

    public AppCompatImageView getKindImg() {
        return kindImg;
    }

    @Override
    public void onClick(View v) {
        if (!checked){
            setBackground(shape_selected);
            trueAnimator.start();
            checked=true;
        }else {
            setBackground(shape);
            trueAnimator.reverse();
            checked=false;
        }
        if (onCheckListener!=null){
            onCheckListener.onChecked(checked);
        }
    }

    private OnCheckListener onCheckListener;

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    public interface OnCheckListener{
        void onChecked(boolean checked);
    }
}
