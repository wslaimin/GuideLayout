package com.lm.guidelayout.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import com.lm.guidelayout.DefaultDecor;
import com.lm.guidelayout.GuideFrame;

public class ImageDecor implements GuideFrame.Decor {
    BitmapDrawable drawable;
    DefaultDecor defaultDecor;
    Rect bounds;

    public ImageDecor(Context context) {
        bounds=new Rect();
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.star);
        drawable=new BitmapDrawable(context.getResources(),bitmap);
        defaultDecor=new DefaultDecor();
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        bounds.setEmpty();
        int w=Math.max(rect.width(),rect.height())+200;
        bounds.left=rect.left-((w-rect.width())/2);
        bounds.right=rect.right+((w-rect.width())/2);
        bounds.top=rect.top-((w-rect.height())/2);
        bounds.bottom=rect.bottom+((w-rect.height())/2);
        drawable.setBounds(bounds);
        drawable.draw(canvas);
    }
}
