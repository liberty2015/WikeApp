package com.liberty.libertylibrary.widget.Indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.liberty.libertylibrary.R;


/**
 * Created by Administrator on 2016/9/26.
 */

public class CircleIndicator extends View {
    private float mRadius;
    private float mIndicatorRadius;
    private final Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintIndicator = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mIndicatorSpace;
    private ViewPager.OnPageChangeListener mListener;
    private boolean mCenterHorizontal;
    private ViewPager mViewPager;
    private int mCurrentPage;
    private int mFollowPage;
    private int mCount;
    private int mPageCount;

    public CircleIndicator(Context context) {
        this(context,null);

    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePagerIndicator);
        mCenterHorizontal = a.getBoolean(R.styleable.CirclePagerIndicator_circle_indicator_centerHorizontal, true);
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(a.getColor(R.styleable.CirclePagerIndicator_circle_indicator_color, 0x0000ff));
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setColor(a.getColor(R.styleable.CirclePagerIndicator_circle_indicator_stroke_color, 0x000000));
        mPaintStroke.setStrokeWidth(a.getDimension(R.styleable.CirclePagerIndicator_circle_indicator_stroke_width, 0));
        mPaintIndicator.setStyle(Paint.Style.FILL);
        mPaintIndicator.setColor(a.getColor(R.styleable.CirclePagerIndicator_circle_indicator_fill_color, 0x0000ff));
        mRadius = a.getDimension(R.styleable.CirclePagerIndicator_circle_indicator_radius, 10);
        mIndicatorSpace = a.getDimension(R.styleable.CirclePagerIndicator_circle_indicator_space, 20);
        mIndicatorRadius = a.getDimension(R.styleable.CirclePagerIndicator_circle_indicator_indicator_radius, 10);
        if (mIndicatorRadius < mRadius) mIndicatorRadius = mRadius;
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int width;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)) {
            width = specSize;
        } else {
            final int count = mViewPager.getAdapter().getCount();
            width = (int) (getPaddingLeft() + getPaddingRight()
                    + (count * 2 * mRadius) + (mIndicatorRadius - mRadius) * 2 + (count - 1) * mIndicatorSpace);
            if (specMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, specSize);
            }
        }
        return width;
    }

    private int measureHeight(int measureSpec) {
        int height;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            height = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, specSize);
            }
        }
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count=mCount;
        if (count==0){
            return;
        }
        int width = getWidth();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();

        final float circleAndSpace = 2 * mRadius + mIndicatorSpace;//直径+圆的间隔
        final float yOffset = paddingTop + mRadius;//竖直方向圆心偏移量
        float xOffset = paddingLeft + mRadius;//水平方向圆心偏移量
        //如果采用水平居中对齐
        if (mCenterHorizontal) {
            xOffset += ((width - paddingLeft - paddingRight) - (count * circleAndSpace)) / 2.0f;
        }else {
            xOffset+=(width - paddingLeft - paddingRight) - (count * circleAndSpace);
        }

        float cX;
        float cY;
        float strokeRadius = mRadius;

        //如果绘制外圆
        if (mPaintStroke.getStrokeWidth() > 0) {
            strokeRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
        }
        for (int i=0;i<count;i++){
            cX = xOffset + (i * circleAndSpace);//计算下个圆绘制起点偏移量
            cY = yOffset;
            if (i==mCurrentPage){
                canvas.drawCircle(cX,cY,mIndicatorRadius,mPaintIndicator);
            }else {
                //绘制圆
                if (mPaintFill.getAlpha() > 0) {
                    canvas.drawCircle(cX, cY, strokeRadius, mPaintFill);
                }
                //绘制外圆
                if (strokeRadius != mRadius) {
                    canvas.drawCircle(cX, cY, mRadius, mPaintStroke);
                }
            }
        }
    }

    public void notifyDataSetChanged() {
        invalidate();
        requestLayout();
    }

    public void setmCurrentPage(int mCurrentPage){
        if (mCurrentPage==0){
            this.mCurrentPage=mCount-1;
        }else if (mCurrentPage==mPageCount-1){
            this.mCurrentPage=0;
        }else {
            this.mCurrentPage=mCurrentPage-1;
        }
        invalidate();
    }

    public void setmPageCount(int mPageCount) {
        if (mPageCount>2){
            this.mPageCount=mPageCount;
            this.mCount=mPageCount-2;
        }else if (mPageCount>=0){
            this.mPageCount=mPageCount;
            this.mCount=mPageCount;
        }else {
            this.mPageCount=0;
            this.mCount=0;
        }
    }
}
