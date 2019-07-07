package com.lm.guidelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class GuideFrame extends ConstraintLayout {
    private View closestAnchorView;
    private View anchorView;
    private Rect anchorRect;
    private ConstraintSet constraintSet = new ConstraintSet();
    private Decor drawDecor;
    private int toAnchor;
    private int toAnchorX, toAnchorY;
    private ViewTreeObserver.OnPreDrawListener onPreDrawListener1, onPreDrawListener2;

    public GuideFrame(Context context) {
        this(context, null);
    }

    public GuideFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onPreDrawListener1 = new ViewTreeObserver.OnPreDrawListener() {
            Rect rect = new Rect();

            @Override
            public boolean onPreDraw() {
                if (rect.equals(anchorRect)) {
                    return true;
                }
                rect = anchorRect;
                anchor();
                return false;
            }
        };
        onPreDrawListener2 = new ViewTreeObserver.OnPreDrawListener() {
            Rect rect = new Rect();

            @Override
            public boolean onPreDraw() {
                if (anchorRect == null) {
                    anchorRect = new Rect();
                } else {
                    anchorRect.setEmpty();
                }
                ViewGroup root = anchorView.getRootView().findViewById(android.R.id.content);
                root.offsetDescendantRectToMyCoords(anchorView, anchorRect);
                anchorRect.right += anchorView.getMeasuredWidth();
                anchorRect.bottom += anchorView.getMeasuredHeight();

                if (rect.equals(anchorRect)) {
                    return true;
                }
                rect = anchorRect;
                anchor();
                return false;
            }
        };
    }

    public void setAnchorView(View view) {
        this.anchorView = view;
        getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener1);
        getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener2);
        getViewTreeObserver().addOnPreDrawListener(onPreDrawListener2);
    }

    public void setAnchorRect(Rect rect) {
        this.anchorRect = rect;
        getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener1);
        getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener2);
        getViewTreeObserver().addOnPreDrawListener(onPreDrawListener1);
    }

    public void setDrawDecor(Decor drawDecor) {
        this.drawDecor = drawDecor;
    }

    void drawDecor(Canvas canvas) {
        if (drawDecor == null) {
            drawDecor = new DefaultDecor();
        }
        if (anchorRect != null) {
            drawDecor.draw(canvas, anchorRect);
        }
    }

    @Override
    public ConstraintLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        LayoutParams childParams = (LayoutParams) params;
        if (childParams.closest) {
            closestAnchorView = child;
            toAnchor = childParams.toAnchor == 0 ? Gravity.LEFT : childParams.toAnchor;
            toAnchorX = childParams.toAnchorX;
            toAnchorY = childParams.toAnchorY;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return !anchorRect.contains((int)event.getX(),(int)event.getY());
    }

    private void anchor() {
        constraintSet.clone(this);

        int anchorX, anchorY;
        switch (Gravity.getXGravity(toAnchor)) {
            case Gravity.LEFT:
                anchorX = anchorRect.left + toAnchorX;
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, anchorX - closestAnchorView.getMeasuredWidth());
                break;
            case Gravity.RIGHT:
                anchorX = anchorRect.right + toAnchorX;
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, anchorX);
                break;
            case Gravity.CENTER_HORIZONTAL:
                anchorX = anchorRect.centerX();
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, anchorX);
                break;
            default:
                anchorX = anchorRect.left + toAnchorX;
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, anchorX - closestAnchorView.getMeasuredWidth());
                break;
        }

        switch (Gravity.getYGravity(toAnchor)) {
            case Gravity.TOP:
                anchorY = anchorRect.top + toAnchorY;
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, anchorY - closestAnchorView.getMeasuredHeight());
                break;
            case Gravity.BOTTOM:
                anchorY = anchorRect.bottom + toAnchorY;
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, anchorY);
                break;
            case Gravity.CENTER_VERTICAL:
                anchorY = anchorRect.centerY();
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, anchorY);
                break;
            default:
                anchorY = anchorRect.top + toAnchorY;
                constraintSet.connect(closestAnchorView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, anchorY - closestAnchorView.getMeasuredHeight());
                break;
        }
        constraintSet.applyTo(this);
    }

    public static class LayoutParams extends ConstraintLayout.LayoutParams {
        boolean closest;
        int toAnchor;
        int toAnchorX, toAnchorY;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.GuideFrame2_Layout);
            closest = a.getBoolean(R.styleable.GuideFrame2_Layout_closest, false);
            toAnchor = a.getInt(R.styleable.GuideFrame2_Layout_to_anchor, 0);
            toAnchorX = a.getDimensionPixelSize(R.styleable.GuideFrame2_Layout_to_anchor_x, 0);
            toAnchorY = a.getDimensionPixelSize(R.styleable.GuideFrame2_Layout_to_anchor_y, 0);
            a.recycle();
        }

    }

    public interface Decor {
        void draw(Canvas canvas, Rect rect);
    }
}
