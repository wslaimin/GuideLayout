package com.lm.guidelayout;

/**
 * Created by 10528 on 2018/10/23.
 */

/**
 * 參考{@link android.view.Gravity}
 */
public class Gravity {
    private static final int NO_GRAVITY=0X0000;
    private static final int AXIS_SPECIFIED=0X0001;
    private static final int MASK1=0X0002;
    private static final int MASK2=0X0004;
    private static final int MASK3=0X0008;
    static final int LEFT=MASK1|AXIS_SPECIFIED;
    static final int RIGHT=MASK2|AXIS_SPECIFIED;
    static final int CENTER_HORIZONTAL=MASK3|AXIS_SPECIFIED;
    static final int TOP=(MASK1|AXIS_SPECIFIED)<<4;
    static final int BOTTOM=(MASK2|AXIS_SPECIFIED)<<4;
    static final int CENTER_VERTICAL=(MASK3|AXIS_SPECIFIED)<<4;

    static int getXGravity(int gravity){
        int g=gravity&(LEFT|RIGHT|CENTER_HORIZONTAL);
        if(g==(LEFT|RIGHT)){
            throw new IllegalArgumentException("gravity can not has left and right");
        }else if(g==LEFT){
            return LEFT;
        }else if(g==RIGHT){
            return RIGHT;
        }else if(g==CENTER_HORIZONTAL){
            return CENTER_HORIZONTAL;
        }
        return NO_GRAVITY;
    }

    public static int getYGravity(int gravity){
        int g=gravity&(TOP|BOTTOM|CENTER_VERTICAL);
        if(g==(TOP|BOTTOM)){
            throw new IllegalArgumentException("gravity can not has top and bottom");
        }else if(g==TOP){
            return TOP;
        }else if(g==BOTTOM){
            return BOTTOM;
        }else if(g==CENTER_VERTICAL){
            return CENTER_VERTICAL;
        }
        return NO_GRAVITY;
    }
}
