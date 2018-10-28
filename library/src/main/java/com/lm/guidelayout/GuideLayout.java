package com.lm.guidelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 10528 on 2018/10/11.
 */

public class GuideLayout extends ConstraintLayout {
    private Paint paint = new Paint();
    private Bitmap background;
    //幀數組，每一個引導頁看做一幀
    private SparseArray<GuideFrame> frameGroup=new SparseArray<>();
    private int nowFrame = -1;
    private boolean hasChanged;
    private Drawable originalBackground;

    public GuideLayout(Context context) {
        super(context);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        setClickable(true);
    }

    public GuideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        setClickable(true);
    }

    public GuideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        setClickable(true);
    }

    public void dismissGuide() {
        ((ViewGroup) getParent()).removeView(this);
    }

    public void nextFrame(int i) {
        if (nowFrame == i) {
            return;
        }
        nowFrame=i;
        for(int j=0;j<frameGroup.size();j++){
            frameGroup.valueAt(j).disappear();
        }
        frameGroup.get(nowFrame).appear();
        hasChanged = true;
    }

    public GuideFrame getFrame(int i){
        return frameGroup.get(i);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        frameGroup.get(nowFrame).onTouchEvent(event);
        return background != null;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (hasChanged) {
            background = createBackground(frameGroup.get(nowFrame).getAnchorRect());
            ViewCompat.setBackground(GuideLayout.this, new BitmapDrawable(getResources(), background));
        }

        if (background != null) {
            hasChanged = false;
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public ConstraintLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ConstraintLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        GuideLayout.LayoutParams childParams = (GuideLayout.LayoutParams) params;
        if (childParams.group != -1) {
            GuideFrame guideFrame = frameGroup.get(childParams.group);
            if (guideFrame == null) {
                guideFrame = new GuideFrame();
                frameGroup.put(childParams.group,guideFrame);
            }
            guideFrame.addView(child);
            if(childParams.closedAnchor) {
                guideFrame.setClosedAnchorView(child);
                guideFrame.setGravity(childParams.gravity);
            }
        }
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        if(originalBackground==null) {
            originalBackground = background;
        }
    }

    private Bitmap createBackground(Rect r) {
        if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if(originalBackground!=null){
            originalBackground.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
            originalBackground.draw(canvas);
        }
        paint.setColor(Color.WHITE);
        canvas.drawRect(r, paint);
        return bitmap;
    }

    public static class LayoutParams extends ConstraintLayout.LayoutParams {
        int group = -1;
        boolean closedAnchor;
        int gravity;

        public LayoutParams(ConstraintLayout.LayoutParams source) {
            super(source);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.GuideLayout_Layout);
            group = a.getInt(R.styleable.GuideLayout_Layout_group, -1);
            closedAnchor = a.getBoolean(R.styleable.GuideLayout_Layout_closed_anchor, false);
            gravity = a.getInt(R.styleable.GuideLayout_Layout_anchor_gravity,0);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
