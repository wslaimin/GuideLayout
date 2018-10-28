package com.lm.guidelayout;

/**
 * Created by 10528 on 2018/10/23.
 */

/**
 * 參考{@link android.view.Gravity}
 */
public class Gravity {
    public static final int NO_GRAVITY=0X0000;
    public static final int AXIS_SPECIFIED=0X0001;
    public static final int MASK1=0X0002;
    public static final int MASK2=0X0004;
    public static final int LEFT=MASK1|AXIS_SPECIFIED;
    public static final int RIGHT=MASK2|AXIS_SPECIFIED;
    public static final int TOP=(MASK1|AXIS_SPECIFIED)<<4;
    public static final int BOTTOM=(MASK2|AXIS_SPECIFIED)<<4;

    public static int getXGravity(int gravity){
        int g=gravity&(LEFT|RIGHT);
        if(g==(LEFT|RIGHT)){
            throw new IllegalArgumentException("gravity can not has left and right");
        }else if(g==LEFT){
            return LEFT;
        }else if(g==RIGHT){
            return RIGHT;
        }
        return NO_GRAVITY;
    }

    public static int getYGravity(int gravity){
        int g=gravity&(TOP|BOTTOM);
        if(g==(TOP|BOTTOM)){
            throw new IllegalArgumentException("gravity can not has top and bottom");
        }else if(g==TOP){
            return TOP;
        }else if(g==BOTTOM){
            return BOTTOM;
        }
        return NO_GRAVITY;
    }
}
