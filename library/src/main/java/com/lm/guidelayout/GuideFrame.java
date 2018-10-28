package com.lm.guidelayout;

import android.graphics.Rect;
import android.support.constraint.ConstraintSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10528 on 2018/10/14.
 */

public class GuideFrame {
    private List<View> views;
    private View anchorView, closedAnchorView;
    private int gravity;
    private Rect anchorRect;
    private int anchorX, anchorY;
    private ClickAnchorListener clickAnchorListener;
    private int offsetX, offsetY;
    private ConstraintSet constraintSet = new ConstraintSet();

    GuideFrame() {

    }

    void addView(View view) {
        if (views == null) {
            views = new ArrayList<>();
        }
        views.add(view);
    }

    public void setAnchorView(View view) {
        anchorView = view;
    }

    public void setClosedAnchorView(View view) {
        closedAnchorView = view;
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(left==oldLeft&&right==oldRight&&top==oldTop&&bottom==oldBottom){
                    return;
                }
                Rect r = new Rect();
                ViewGroup root = v.getRootView().findViewById(android.R.id.content);
                root.offsetDescendantRectToMyCoords(anchorView, r);
                anchorRect = new Rect();
                anchorRect.left = r.left;
                anchorRect.top = r.top;
                anchorRect.right = anchorRect.left + anchorView.getMeasuredWidth();
                anchorRect.bottom = anchorRect.top + anchorView.getMeasuredHeight();

                final GuideLayout guideLayout = (GuideLayout) closedAnchorView.getParent();
                constraintSet.clone(guideLayout);

                switch (Gravity.getXGravity(gravity)) {
                    case Gravity.LEFT:
                        anchorX = anchorRect.left + offsetX;
                        constraintSet.connect(closedAnchorView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, anchorX - closedAnchorView.getMeasuredWidth());
                        break;
                    case Gravity.RIGHT:
                        anchorX = anchorRect.right + offsetX;
                        constraintSet.connect(closedAnchorView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, anchorX);
                        break;
                    default:
                        anchorX = anchorRect.left + offsetX;
                        constraintSet.connect(closedAnchorView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, anchorX - closedAnchorView.getMeasuredWidth());
                        break;
                }

                switch (Gravity.getYGravity(gravity)) {
                    case Gravity.TOP:
                        anchorY = anchorRect.top + offsetY;
                        constraintSet.connect(closedAnchorView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, anchorY - closedAnchorView.getMeasuredHeight());
                        break;
                    case Gravity.BOTTOM:
                        anchorY = anchorRect.bottom + offsetY;
                        constraintSet.connect(closedAnchorView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, anchorY);
                        break;
                    default:
                        anchorY = anchorRect.top + offsetY;
                        constraintSet.connect(closedAnchorView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, anchorY - closedAnchorView.getMeasuredHeight());
                        break;
                }
                closedAnchorView.post(new Runnable() {
                    @Override
                    public void run() {
                        constraintSet.applyTo(guideLayout);
                        for (View v : views) {
                            v.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    Rect getAnchorRect() {
        return anchorRect;
    }

    void setGravity(int gravity) {
        this.gravity = gravity;
    }


    public void setClickAnchorListener(ClickAnchorListener clickAnchorListener) {
        this.clickAnchorListener = clickAnchorListener;
    }

    public View findViewById(int resId){
        for(View view : views){
            if(view.getId()==resId){
                return view;
            }
        }
        return null;
    }

    void onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (anchorRect.contains((int) event.getX(), (int) event.getY())) {
                    if (clickAnchorListener != null) {
                        clickAnchorListener.clickAnchor();
                    }
                }
                break;
        }
    }

    void disappear() {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    /**直接設置成VISIBLE會有位置在左上角再移動到相應位置的情況
     * 先測量，實際在onLayoutChange()監聽中顯示
     */
    void appear() {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }
}
