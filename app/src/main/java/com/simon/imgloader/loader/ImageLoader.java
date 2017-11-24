package com.simon.imgloader.loader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.simon.imgloader.R;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */

public class ImageLoader{

    public static  final int IO_BUFFER_SIZE=8*1024;
    public static ImageLoader mInstance;
    private static Resources resources;
    private static Bitmap  mPlaceHolderBitmap;
    private ImageLoader(Context context) {
        resources=context.getResources();
        mPlaceHolderBitmap= BitmapFactory.decodeResource(resources,R.drawable.empty_photo);
    }

    public static ImageLoader getmInstance(Context context){
        if(mInstance==null){
            synchronized (ImageLoader.class){
                if(mInstance==null){
                    mInstance=new ImageLoader(context);
                }
            }
        }
        return mInstance;
    }

    public void loadBitmap(@Nullable String url, ImageView imageView){
       if(url==null) {return;}
       if(cancelPotentialWork(url,imageView)){
            BitmapWorkTask bitmapWorkTask=new BitmapWorkTask(imageView,resources);
            AsyncDrawable asyncDrawable=new AsyncDrawable(resources,mPlaceHolderBitmap,bitmapWorkTask);
            imageView.setImageDrawable(asyncDrawable);
            bitmapWorkTask.execute(url);
       }
    }

    private  boolean cancelPotentialWork(String url, ImageView imageView) {
        BitmapWorkTask bitmapWorkTask=getBitmapWorkTask(imageView);
        if(bitmapWorkTask!=null){
            if(TextUtils.isEmpty(bitmapWorkTask.url)||!bitmapWorkTask.url.equals(url)){
                //cancel the previous
                bitmapWorkTask.cancel(true);
            }else{
                // the same work already running....
                return false;
            }
        }
        // no task associated with the imageView or the previous task was cancel
        return true;
    }

    private  BitmapWorkTask getBitmapWorkTask(ImageView imageView) {
        if(imageView!=null){
            Drawable drawable = imageView.getDrawable();
            if(drawable!=null){
                if(drawable instanceof AsyncDrawable){
                    AsyncDrawable asyncDrawable= (AsyncDrawable) drawable;
                    return  asyncDrawable.getBitmapWorkTask();
                }
            }
        }
        return null;
    }


}
