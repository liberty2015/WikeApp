package com.liberty.wikepro.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by liberty on 2017/4/4.
 */

public class GlideCacheUtil {
    private static GlideCacheUtil instance;

    public static GlideCacheUtil getInstance(){
        if (instance==null){
            synchronized (GlideCacheUtil.class){
                if (instance==null){
                    instance=new GlideCacheUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 清除磁盘图片缓存
     * @param context
     */
    public void cleanImageDiskCache(final Context context){
        if (Looper.myLooper()==Looper.getMainLooper()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(context).clearDiskCache();
                }
            }).start();
        }else {
            Glide.get(context).clearDiskCache();
        }
    }

    /**
     * 清除图片内存缓存
     * @param context
     */
    public void cleanImageMemoryCache(Context context){
        if (Looper.myLooper()==Looper.getMainLooper()){
            Glide.get(context).clearMemory();
        }
    }

    /**
     * 清除所有图片缓存
     * @param context
     */
    public void cleanImageAllCache(Context context){
        cleanImageDiskCache(context);
        cleanImageMemoryCache(context);
        String imgExternalCatchDir=context.getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(imgExternalCatchDir,true);
    }

    private void deleteFolderFile(String  filePath,boolean deleteThisPath){
        if (!TextUtils.isEmpty(filePath)){
            File file=new File(filePath);
            if (file.isDirectory()){
                File files[]=file.listFiles();
                for (File file1:files){
                    deleteFolderFile(file1.getAbsolutePath(),deleteThisPath);
                }
            }
            if (deleteThisPath){
                if (!file.isDirectory()){
                    file.delete();
                }else if (file.listFiles().length==0){
                    file.delete();
                }
            }
        }
    }


    /**
     * 获取Glide造成的缓存大小
     * @param context
     * @return
     */
    public String getCacheSize(Context context){
        return getFormatSize(getFolderSize(new File(context.getCacheDir()+"/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
    }

    /**
     * 获取指定文件夹内所有文件大小总和
     * @param file
     * @return
     */
    private long getFolderSize(File file){
        long size=0;
        File[] files=file.listFiles();
        if (files!=null&&files.length>0){
            for (File file1:files){
                if (file1.isDirectory()){
                    size=size+getFolderSize(file1);
                }else {
                    size+=file1.length();
                }
            }
        }
        return size;
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    private static String getFormatSize(double size){
        double kiloByte=size/1024;
        if (kiloByte<1){
            return size+"Byte";
        }

        double megaByte=kiloByte/1024;
        if (megaByte<1){
            BigDecimal result=new BigDecimal(Double.toString(kiloByte));
            return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"KB";
        }
        double gigaByte=megaByte/1024;
        if (gigaByte<1){
            BigDecimal result=new BigDecimal(Double.toString(megaByte));
            return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"MB";
        }
        double teraByte=gigaByte/1024;
        if (teraByte<1){
            BigDecimal result=new BigDecimal(Double.toString(gigaByte));
            return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"GB";
        }
        BigDecimal result=new BigDecimal(teraByte);
        return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"TB";
    }
}
