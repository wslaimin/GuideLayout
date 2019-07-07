package com.lm.guidelayout;

import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;

public class DecorSet implements GuideFrame.Decor{
    private List<GuideFrame.Decor> decors=new ArrayList<>();

    public void addDecor(GuideFrame.Decor decor){
        decors.add(decor);
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        for(GuideFrame.Decor decor : decors){
            decor.draw(canvas,rect);
        }
    }
}
