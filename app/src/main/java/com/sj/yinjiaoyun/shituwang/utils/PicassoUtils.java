package com.sj.yinjiaoyun.shituwang.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.sj.yinjiaoyun.shituwang.R;
import com.squareup.picasso.Picasso;

/**
 * Created by wanzhiying on 2017/3/9.
 * Picasso封装加载圆角图片的工具类
 */
public class PicassoUtils {
    public static void LoadPathCorners(Context context , String path, ImageView imageView){
        Picasso.with(context).load(path).resize(60,60).centerCrop().error(R.drawable.master).into(imageView);

    }
    public static void LoadImg(Context context , String path, ImageView imageView){
        Picasso.with(context).load(path).resize(40,40).centerCrop().config(Bitmap.Config.RGB_565).error(R.drawable.master).into(imageView);

    }
    public static void LoadImg(Context context , int  id, ImageView imageView){
        Picasso.with(context).load(id).resize(40,40).centerCrop().config(Bitmap.Config.RGB_565).error(R.drawable.master).into(imageView);

    }
    public static void LoadPathCorners(Context context , String path,int width,int height, ImageView imageView){
        Picasso.with(context).load(path).resize(width,height).centerCrop().error(R.drawable.master).into(imageView);

    }
    public static void LoadCorners(Context context , int  id, ImageView imageView){
        Picasso.with(context).load(id).resize(75,75).centerCrop().into(imageView);

    }
    public static void LoadCorners(Context context , int  id,int width,int height ,ImageView imageView){
        Picasso.with(context).load(id).resize(width,height).centerCrop().into(imageView);

    }
}
