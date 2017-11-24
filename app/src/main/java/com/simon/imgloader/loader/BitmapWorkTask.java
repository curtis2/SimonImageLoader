package com.simon.imgloader.loader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.simon.imgloader.loader.ImageLoader.IO_BUFFER_SIZE;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */

public class BitmapWorkTask extends AsyncTask<String,Void,Bitmap>{

    private WeakReference<ImageView> imageViewWeakReference;
    private Resources resources;
    public  String url;

    public BitmapWorkTask(ImageView imageView, Resources resources) {
        this.resources=resources;
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... String) {
        //网络加载bitmap
        HttpURLConnection httpURLConnection;
        BufferedInputStream in=null;
        try {
            URL url=new URL(String[0]);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            in= new BufferedInputStream(httpURLConnection.getInputStream(),IO_BUFFER_SIZE);
            return  BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ImageView imageView = imageViewWeakReference.get();
        if(imageView!=null&&bitmap!=null){
            BitmapWorkTask bitmapWorkTask = getBitmapWorkTask(imageView);
            if(bitmapWorkTask!=null&&bitmapWorkTask==this){
                imageView.setImageBitmap(bitmap);
            }
        }
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
