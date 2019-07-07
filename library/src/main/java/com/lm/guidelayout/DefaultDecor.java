package com.lm.guidelayout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class DefaultDecor implements GuideFrame.Decor {
    Paint paint;

    public DefaultDecor(){
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        canvas.drawRect(rect,paint);
    }
}
