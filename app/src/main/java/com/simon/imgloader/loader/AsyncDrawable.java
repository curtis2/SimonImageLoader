package com.simon.imgloader.loader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */
public class AsyncDrawable extends BitmapDrawable{

   private WeakReference<BitmapWorkTask> bitmapWorkTaskWeakReference;

    public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkTask bitmapWorkTask) {
        super(res, bitmap);
        this.bitmapWorkTaskWeakReference = new WeakReference<>(bitmapWorkTask);
    }

    public BitmapWorkTask getBitmapWorkTask(){
        return bitmapWorkTaskWeakReference.get();
    }
}
