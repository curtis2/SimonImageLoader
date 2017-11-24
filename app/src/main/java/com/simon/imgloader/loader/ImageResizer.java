package com.simon.imgloader.loader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */
public class ImageResizer {
    /**
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
  public static Bitmap decodeBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
      BitmapFactory.Options options=new BitmapFactory.Options();
      options.inJustDecodeBounds=true;
      BitmapFactory.decodeResource(res,resId,options);

      options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
      options.inJustDecodeBounds=false;
      return BitmapFactory.decodeResource(res,resId,options);
  }


  public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
      int width=options.outWidth;
      int height=options.outHeight;
      int inSampleSize=1;
      if(width>reqHeight||height>reqHeight){

          int halfWidth=width/2;
          int halfHeight=height/2;

          while((halfWidth/inSampleSize)>reqWidth&&(halfHeight/inSampleSize)>reqHeight){
              inSampleSize*=2;
          }
      }
      return inSampleSize;
  }




}
