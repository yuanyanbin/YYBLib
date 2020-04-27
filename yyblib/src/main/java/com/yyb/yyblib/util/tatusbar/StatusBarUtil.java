package com.yyb.yyblib.util.tatusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;


/**
 * 设置系统状态栏颜色
 *
 * @author msdx (msdx.android@qq.com)
 * @version 0.5.1
 * @since 0.1
 */
public class StatusBarUtil {

    static final IStatusBar IMPL;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IMPL = new StatusBarMImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isEMUI()) {
            IMPL = new StatusBarLollipopImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMPL = new StatusBarKitkatImpl();
        } else {
            IMPL = new IStatusBar() {
                @Override
                public void setStatusBarColor(Window window, @ColorInt int color) {
                }
            };
        }
    }

    private static boolean isEMUI() {
        File file = new File(Environment.getRootDirectory(), "build.prop");
        if (file.exists()) {
            Properties properties = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                properties.load(fis);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return properties.containsKey("ro.build.hw_emui_api_level");
        }
        return false;
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        boolean isLightColor = toGrey(color) > 225;
        setStatusBarColor(activity, color, isLightColor);
    }

    /**
     * 把颜色转换成灰度值。
     * 代码来自 Flyme 示例代码
     */
    public static int toGrey(@ColorInt int color) {
        int blue = Color.blue(color);
        int green = Color.green(color);
        int red = Color.red(color);
        return (red * 38 + green * 75 + blue * 15) >> 7;
    }

    /**
     * Set system status bar color.
     *
     * @param activity       The activity to set.
     * @param color          status bar color
     * @param lightStatusBar if the status bar color is light. Only effective in some devices.
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color, boolean lightStatusBar) {
        setStatusBarColor(activity.getWindow(), color, lightStatusBar);
    }

    /**
     * Set the system status bar color
     *
     * @param window         the window
     * @param color          status bar color
     * @param lightStatusBar if the status bar color is light. Only effective in some devices.
     */
    public static void setStatusBarColor(Window window, @ColorInt int color, boolean lightStatusBar) {
        if ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) > 0
                || StatusBarExclude.exclude) {
            return;
        }
        IMPL.setStatusBarColor(window, color);
        LightStatusBarCompat.setLightStatusBar(window, lightStatusBar);
    }

    /**
     * Sets whether or not this view should account for system screen decorations
     *
     * @param window           The window to set
     * @param fitSystemWindows If true, the android content view would be applied the insets
     */
    public static void setFitsSystemWindows(Window window, boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            internalSetFitsSystemWindows(window, fitSystemWindows);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    static void internalSetFitsSystemWindows(Window window, boolean fitSystemWindows) {
        final ViewGroup contentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        final View childView = contentView.getChildAt(0);
        if (childView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            childView.setFitsSystemWindows(fitSystemWindows);
        }
    }

    public static void resetActionBarContainerTopMargin(Window window) {
        View contentView = window.findViewById(android.R.id.content);
        ViewGroup group = (ViewGroup) contentView.getParent();
        if (group.getChildCount() > 1) {
            View view = group.getChildAt(1);
            internalResetActionBarContainer(view);
        }
    }

    /**
     * @param window               The window of the current activity.
     * @param actionBarContainerId android.support.v7.appcompat.R.id.action_bar_container
     */
    public static void resetActionBarContainerTopMargin(Window window, @IdRes int actionBarContainerId) {
        View view = window.findViewById(actionBarContainerId);
        internalResetActionBarContainer(view);
    }

    private static void internalResetActionBarContainer(View actionBarContainer) {
        if (actionBarContainer != null) {
            ViewGroup.LayoutParams params = actionBarContainer.getLayoutParams();
            if (params instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) params).topMargin = 0;
                actionBarContainer.setLayoutParams(params);
            }
        }
    }

    /**
     * @param window           the window will be set
     * @param isLightStatusBar if the status bar color is light
     * @since 0.5.1
     */
    public static void setLightStatusBar(Window window, boolean isLightStatusBar) {
        LightStatusBarCompat.setLightStatusBar(window, isLightStatusBar);
    }

    /**
     * Set the status bar to be translucent
     *
     * @param window      The window which status bar would be set
     * @param translucent True if set the status bar to be translucent
     */
    public static void setTranslucent(Window window, boolean translucent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (translucent) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                internalSetFitsSystemWindows(window, false);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }


    /**
     * 设置状态栏透明
     */
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            attributes.flags |= flagTranslucentStatus;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }

    /**
     *设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @return 1:MIUI 2:Flyme 3:android6.0
     */
    public static int statusBarDarkMode(Activity activity){
        int result=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(miuiSetStatusBarDarkMode(activity.getWindow(), true)){
                result=1;
            }else if(flymeSetStatusBarDarkMode(activity.getWindow(), true)){
                result=2;
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result=3;
            }
        }
        return result;
    }

    /**
     *设置状态栏白色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @return 1:MIUI 2:Flyme 3:android6.0
     */
    public static int statusBarLightMode(Activity activity){
        int result=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(miuiSetStatusBarDarkMode(activity.getWindow(), false)){
                result=1;
            }else if(flymeSetStatusBarDarkMode(activity.getWindow(), false)){
                result=2;
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                result=3;
            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    private static boolean miuiSetStatusBarDarkMode(Window window, boolean dark) {
        boolean result = false;
        try {
            //判断是否是MIUI系统
            Class.forName("android.view.MiuiWindowManager$LayoutParams");
            result = true;
        } catch (Exception e) {}

        if (result) {
            if (window != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //MIUI 基于6.0的版本有调整，兼容Android原生的方式
                    if (dark) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    }
                }
                //MIUI 旧版本
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    if(dark){
                        extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                    }else{
                        extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                    }
                }catch (Exception e){

                }
            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    private static boolean flymeSetStatusBarDarkMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }
}
