package com.yyb.yyblib.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Copyright (C)
 * FileName: LubanUtil
 * Author: 员外
 * Date: 2019-11-09 19:28
 * Description: TODO<图片压缩>
 * Version: 1.0
 */
public class LubanUtil {

    /**
     * 压缩图片
     *
     * @param mContext
     */
    public static void compressImage(Context mContext, String photoPath, final OnCompressListener1 listener1) {
        Luban.with(mContext)
                .load(photoPath)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(getPath(mContext))
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        listener1.onSuccess(file);
                        LogUtil.e("图片大小" + file.length());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        LogUtil.e(e.getMessage());
                    }
                }).launch();    //启动压缩
    }

    private static String getPath(Context mContext) {
        String path = mContext.getCacheDir().getAbsolutePath();
//        File file = new File(path);
//        if (file.mkdirs()) {
//            return path;
//        }
        return path;
    }

    public interface OnCompressListener1 {

        void onSuccess(File file);
    }
}
