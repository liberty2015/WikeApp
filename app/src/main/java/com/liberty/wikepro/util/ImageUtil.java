package com.liberty.wikepro.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.liberty.wikepro.R;
import com.liberty.wikepro.view.widget.GlideCircleTransform;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class ImageUtil {

    private static RequestListener<String ,GlideDrawable> requestListener=new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
            if (e!=null)
                e.printStackTrace();
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
            return false;
        }
    };

    public static void getCircleImageIntoImageView(Context context, ImageView imageView,String url,boolean needCircle){
        if (needCircle){
            Glide
                    .with(context)
                    .load(url)
                    .placeholder(R.mipmap.placeholder)
                    .error(R.mipmap.picerror)
                    .listener(requestListener)
                    .transform(new GlideCircleTransform(context))
                    .into(imageView);
        }else {
            Glide
                    .with(context)
                    .load(url)
                    .placeholder(R.mipmap.placeholder)
                    .listener(requestListener)
                    .error(R.mipmap.picerror)
                    .into(imageView);
        }
    }

    public static Bitmap getCircleBitmap(Context context,Bitmap source){
        Bitmap dst=Bitmap.createBitmap(source.getWidth(),source.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(dst);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        paint.setShader(new BitmapShader(source, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        int cx=source.getWidth()/2;
        int cy=source.getHeight()/2;
        int radius=(Math.min(source.getWidth(),source.getHeight()))/2;
        canvas.drawCircle(cx,cy,radius,paint);
        return dst;
    }

    public static Bitmap getCircleBitmap(Context context,String url){
        BitmapTarget target=new BitmapTarget();
        Glide.with(context)
                .load(url)
                .asBitmap()
//                .transform(new GlideCircleTransform(context))
                .into(target);
        return target.getBitmap();
    }

    static class BitmapTarget extends SimpleTarget<Bitmap>{

        Bitmap bitmap;

        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
            this.bitmap=bitmap;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }
    }
}
