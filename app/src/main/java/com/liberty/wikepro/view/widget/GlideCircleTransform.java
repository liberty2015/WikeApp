package com.liberty.wikepro.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by LinJinFeng on 2017/2/22.
 */

public class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform==null) return null;
        int size=Math.min(toTransform.getWidth(),toTransform.getHeight());
        int x=(toTransform.getWidth()-size)/2;
        int y=(toTransform.getHeight()-size)/2;
        Log.d("xxxxxxxx","x="+x+"  y="+y+"  size="+size);
        Bitmap transform=Bitmap.createBitmap(toTransform,x,y,size,size);
        Bitmap result=pool.get(size,size, Bitmap.Config.ARGB_8888);
        if (result==null){
            result=Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas=new Canvas(result);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setFilterBitmap(true);
        paint.setShader(new BitmapShader(transform, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        float r=size/2f;
        canvas.drawCircle(r,r,r,paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
