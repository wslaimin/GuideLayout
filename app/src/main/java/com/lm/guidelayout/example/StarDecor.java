package com.lm.guidelayout.example;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import com.lm.guidelayout.GuideFrame;

public class StarDecor implements GuideFrame.Decor {
    Paint paint;
    Path path;
    PointF point0,point1,point2,point3,point4;

    public StarDecor() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        path=new Path();
        point0=new PointF();
        point1=new PointF();
        point2=new PointF();
        point3=new PointF();
        point4=new PointF();
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        path.reset();

        int w=Math.max(rect.width(),rect.height());

        point0.x=0;
        point0.y=-w/2f;

        point1.x=(float)(Math.cos(Math.PI*18/180)*w/2f);
        point1.y=-(float)(Math.sin(Math.PI*18/180)*w/2);

        point2.x=(float)(Math.cos(Math.PI*-54/180)*w/2f);
        point2.y=-(float)(Math.sin(Math.PI*-54/180)*w/2f);

        point3.x=-point2.x;
        point3.y=point2.y;

        point4.x=-point1.x;
        point4.y=point1.y;

        path.moveTo(point0.x,point0.y);
        path.lineTo(point3.x,point3.y);
        path.lineTo(point1.x,point1.y);
        path.lineTo(point4.x,point4.y);
        path.lineTo(point2.x,point2.y);
        path.close();
        path.offset((rect.left+rect.right)/2f,(rect.top+rect.bottom)/2f);

        canvas.drawPath(path,paint);
    }
}
