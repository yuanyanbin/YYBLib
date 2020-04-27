package com.yyb.yyblib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zfc on 2018/3/30.
 */

public class FileUtil {
    /**
     * 创建保存得到的图片的文件
     *
     * @return
     * @throws IOException
     */
    public static File createImageFile(Context context) {

//            File mediaStorageDir = new File(context.getCacheDir(), "photo");
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "photo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        String suffix = ".jpg";
        File image = new File(mediaStorageDir + File.separator + imageFileName + suffix);
//            mCurrentPhotoPath = image.getAbsolutePath();
        LogUtil.e("path", "image = " + image.getAbsolutePath());
        return image;
    }


    public static Uri saveBitmap(Bitmap bitmap, Context context) throws IOException {
        File file = createImageFile(context);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File createAudioFile(Context context) {
//        Environment.getExternalStorageDirectory()
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "audio");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PCM_" + timeStamp;
        String suffix = ".amr";
        File image = new File(mediaStorageDir + File.separator + imageFileName + suffix);
//            mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
