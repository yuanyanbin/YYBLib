package com.yyb.yyblib.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * ClassName: SoftInputManager
 * Description: TODO<软键盘管理工具类>
 * Author: jgduan
 * Date: 2019/05/17 10:56
 * Email: jgduan@dohenes.com
 * Version: v1.0
 */
public class SoftInputManager {

    private static boolean state = false;

    /**
     * 打开或关闭软键盘--根据当前软键盘是否展示判断
     *
     * @param context context
     * @param flag    flag
     */
    public static void openOrCloseSoftInput(Context context, boolean flag) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            state = flag;
        }
    }

    /**
     * 打开软键盘
     *
     * @param context context
     * @param view    view
     */
    public static void openSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            // 解决传统方法失效问题
//            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            state = true;
        }
    }

    /**
     * 关闭软键盘
     *
     * @param context context
     * @param view    view
     */
    public static void closeSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
            // 解决传统方法失效问题
//            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            state = false;
        }
    }

    /**
     * 获取输入法状态
     *
     * @return isOpen若返回true，则表示输入法打开
     */
    public static boolean getState() {
        // isOpen若返回true，则表示输入法打开
        return state;
    }

    /**
     * 判断是否应该隐藏输入法
     *
     * @param v     v
     * @param event event
     * @return true or false.
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}