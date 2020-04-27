package com.yyb.yyblib.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.FileProvider;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * ClassName: CommonUtil
 * Description: TODO<通用工具类>
 * Author: Zuobb
 * Date: 2019-05-07 10:45
 * Version: v1.0
 */
public final class CommonUtil {
    /**
     * 获取进程名字
     *
     * @param cxt 上下文
     * @param pid 进程pid参数
     * @return 进程名字
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (runningApps == null) {
                return null;
            }

            for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
        }

        return null;
    }


    /**
     * 判断手机格式是否正确
     *
     * @param phone 手机号
     */
    public static boolean isPhone(String phone) {
        return phone.matches("^1\\d{10}$");
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context context
     * @param pkgName 应用包名
     * @return true:存在；false：不存在
     */
    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 获取当前版本号
     */
    public static String getVersionName(Context mContext) {
        String version = "";
        try {
            // 获取PackageManager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    mContext.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 生成GUID 表主键
     */
    public static String GenerateGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 对数字进行四舍五入处理
     * <p>
     * 处理参数
     *
     * @param num      保留小数位数
     * @param isMakeup 如果小数点后面的位数小于要保留小数点位数，是否补0
     */
    public static String roundOf(double value, int num, boolean isMakeup) {
        String str = value + "";
        // 对数字字符串进行四舍五入处理
        str = roundOf(str, num);

        // 取得小数点后面的数字字符串
        String str1 = str.substring(str.indexOf(".") + 1, str.length());
        // 如果小数点后面的位数小于要保留小数点位数
        if (str1.length() < num) {
            if (isMakeup) {
                for (int n = 0; n < (num - str1.length()); n++) {
                    str = str + "0";
                }
            }
        }
        return str;
    }

    /**
     * 对数字字符串进行四舍五入处理
     *
     * @param str   处理参数
     * @param scale 保留小数位数
     */
    public static String roundOf(String str, int scale) {
        // 输入精度小于0则抛出异常
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }

        if (TextUtils.isEmpty(str)) {
            return "";
        }
        // 取得数值
        BigDecimal b = new BigDecimal(str);
        // 取得数值1
        BigDecimal one = new BigDecimal("1");
        // 原始值除以1，保留scale位小数，进行四舍五入
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 安装apk
     * 兼容7.0
     *
     * @param context 上下文
     * @param file    文件
     */
    public static void installAPK(Context context, File file) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(FileProvider.getUriForFile(context, "com.gegeda.student.fileprovider", file), "application/vnd.android.package-archive");//通过FileProvider创建一个content类型的Uri
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 关闭键盘
     *
     * @param activity Activity
     */
    public static void hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() != null)
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制关闭软键盘
     */
    public static void closeKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 跳到拨号盘-拨打电话
     *
     * @param context 上下文
     * @param phone   手机号码
     */

    @SuppressLint("MissingPermission")
    public static void actionDial(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.setAction(Intent.ACTION_DIAL);// 拨号盘
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机getDeviceId
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Activity.TELEPHONY_SERVICE);
        try {
            if (tm != null) {
                String deviceId = tm.getDeviceId();
                if (TextUtils.isEmpty(deviceId)) {
                    return android.os.Build.SERIAL;
                } else {
                    return deviceId;
                }
            } else {
                return android.os.Build.SERIAL;
            }

        } catch (Exception e) {
            return android.os.Build.SERIAL;
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取View的高
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static int getViewHeight(Context context, int resourceId) {
        Resources resources = context.getResources();
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    /**
     * 设置状态栏高度
     *
     * @param view
     */
    public static void setStatusBarHeight(Context context, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = getStatusBarHeight(context);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 当然跳转前要判断一下该URL Scheme是否有效
     *
     * @param mContext
     * @param url
     * @return
     */
    public static boolean schemeValid(Context mContext, String url) {
        PackageManager manager = mContext.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(url));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    /**
     * 获取剪贴板内容
     *
     * @param mContext
     * @return
     */
    public static String getClipboard(Context mContext) {
        try {
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            if (null == data) {
                return "";
            }
            ClipData.Item item = data.getItemAt(0);
            if (item == null) {
                return "";
            }
            return item.getText().toString();
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 判断app是否在前台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 据逗号分隔到List数组中
     *
     * @param str
     * @return
     */
    public static List<String> getList(String str) {
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        String str2 = str.replace(" ", "");//去掉所用空格
        List<String> list = Arrays.asList(str2.split(","));
        List<String> list1 = new ArrayList<>();
        list1.addAll(list);
        return list1;
    }

    /**
     * List集合中的数据用分隔符分开
     *
     * @param stringList
     * @return
     */
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(","); // 分隔符
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    /**
     * list中内容转化为逗号分隔的字符串
     *
     * @param list
     * @return
     */
    public static String getListString(List<String> list) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int newWidth = outMetrics.widthPixels;
        return newWidth;
    }
}