package com.yyb.yyblib.util;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.reactivex.functions.Consumer;

/**
 * This class is used for 6.0动态权限工具类
 * Created by drowtram on 2017/10/18.
 */

public class PermissionUtils {
    private static final String TAG = "RxPermissionTest";

    /**
     * Android6.0 获取动态权限
     *
     * @param activity    对应的activity
     * @param listener    授权成功或失败的回调
     * @param permissions 请求权限 例如 Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
     */
    public static void requestPermission(Activity activity, final OnPermissionGrantedListener listener, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new RxPermissions(activity)
                    .request(permissions)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Boolean granted) throws Exception {
                            if (listener != null) {
                                listener.onGranted(granted);
                            }
                        }
                    });
        } else {
            if (listener != null) {
                listener.onGranted(true);
            }
        }
    }


    public interface OnPermissionGrantedListener {
        /**
         * 授权回调
         *
         * @param granted true 表示获取到权限  false表示没有权限
         */
        void onGranted(boolean granted);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() ==                  NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE,                        Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==                                                                AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 跳转设置
     */
    public static void toSetting(Context mContext) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        mContext.startActivity(localIntent);
    }

}
