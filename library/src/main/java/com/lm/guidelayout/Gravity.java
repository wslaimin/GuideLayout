package com.lm.guidelayout;

/**
 * Created by 10528 on 2018/10/23.
 */

/**
 * 參考{@link android.view.Gravity}
 */
public class Gravity {
    private static final int SPECIFIED = 0X0001;
    private static final int MASK1 = 0x0002;
    private static final int MASK2 = 0x0004;
    private static final int MASK3 = 0x0006;
    private static final int MASK4 = 0x0008;
    private static final int MASK5 = 0x000A;
    static final int LEFT_TO_LEFT = MASK1 | SPECIFIED;
    static final int LEFT_TO_RIGHT = MASK2 | SPECIFIED;
    static final int RIGHT_TO_LEFT = MASK3 | SPECIFIED;
    static final int RIGHT_TO_RIGHT = MASK4 | SPECIFIED;
    static final int CENTER_HORIZONTAL = MASK5 | SPECIFIED;
    static final int TOP_TO_TOP = MASK1 << 3 | SPECIFIED;
    static final int TOP_TO_BOTTOM = MASK2 << 3 | SPECIFIED;
    static final int BOTTOM_TO_TOP = MASK3 << 3 | SPECIFIED;
    static final int BOTTOM_TO_BOTTOM = MASK4 << 3 | SPECIFIED;
    static final int CENTER_VERTICAL = MASK5 << 3 | SPECIFIED;

    static int getXGravity(int gravity) {
        return gravity & 0xF;
    }

    public static int getYGravity(int gravity) {
        return gravity & 0x71;
    }
}
