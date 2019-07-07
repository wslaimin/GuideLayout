package com.lm.guidelayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

public class GuideLayout extends FrameLayout {
    private List<GuideFrame> frames;
    private int frameIndex=-1;

    public GuideLayout(@NonNull Context context) {
        this(context,null);
    }

    public GuideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GuideLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_HARDWARE,null);
        setWillNotDraw(false);
        frames=new ArrayList<>();
    }

    public void addFrame(GuideFrame view){
        frames.add(view);
    }

    public void addFrame(int layoutId){
        GuideFrame frame= (GuideFrame) LayoutInflater.from(getContext()).inflate(layoutId,this,false);
        addFrame(frame);
    }

    public void toFrame(int i){
        if(frameIndex !=i) {
            frameIndex=i;
            removeAllViews();
            addView(frames.get(i));
        }
    }

    public int getFrameIndex(){
        return frameIndex;
    }

    public GuideFrame getFrame(int index){
        return frames.get(index);
    }

    public void dismiss(){
        ((ViewGroup)getParent()).removeView(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        frames.get(frameIndex).drawDecor(canvas);
    }
}
