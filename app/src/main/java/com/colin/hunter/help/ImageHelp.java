package com.colin.hunter.help;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Debug;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.colin.hunter.base.BaseApplication;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class ImageHelp {

    /*
     * Java文件操作 获取文件扩展名
     *
     * Created on: 2011-8-2 Author: blueeagle
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取图片大小
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    public static long getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    /**
     * 修改尺寸
     *
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap resize(Bitmap bitmap, int newWidth, int newHeight) {
        try {
            if (bitmap == null) {
                return null;
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            if (bitmap != null & !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
            return newbm;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 根据图片尺寸进行压缩
     *
     * @param srcPath
     * @param pixelSize
     * @return
     */
    private static Bitmap compressImageFromFile(String srcPath, int pixelSize) {
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;// 只读边,不读内容
            BitmapFactory.decodeFile(srcPath, newOpts);

            // Calculate inSampleSize
            newOpts.inSampleSize = calculateInSampleSize(newOpts, pixelSize, pixelSize);

            // Decode bitmap with inSampleSize set
            newOpts.inJustDecodeBounds = false;
            newOpts.inPreferredConfig = Config.RGB_565;
            newOpts.inPurgeable = true;// 同时设置才会有效
            newOpts.inInputShareable = true;// 当系统内存不够时候图片自动被回收

            FileInputStream is = new FileInputStream(srcPath);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);

            is.close();
            is = null;

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得缩略图，供本地显示使用
     * 缩略图为正方形，且经过旋转处理
     *
     * @param srcPath
     * @param pixelSize
     * @return
     */
    public static Bitmap getBitmapThumbnail(String srcPath, int pixelSize) {
        try {
            // 创建缩略图
            Bitmap bitmap = compressImageFromFile(srcPath, pixelSize);

            // 读取图片中的方向信息
            int digree = 0;
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(srcPath);
            } catch (IOException e) {
                e.printStackTrace();
                exif = null;
            }
            if (exif != null) {
                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;
                }
            }

            // 旋转图片
            if (digree != 0) {
                Matrix m = new Matrix();
                m.postRotate(digree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            }

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param fileName
     * @return byte[]
     * @throws Exception
     */
    public static String fileToString(String fileName) {
        try {
            logMemoryStats("开始");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);
            // logMemoryStats("-");

            // Calculate inSampleSize
            if (options.outHeight > options.outWidth) {
                options.inSampleSize = calculateInSampleSize(options, 1080, 1920);
            } else {
                options.inSampleSize = calculateInSampleSize(options, 1920, 1080);
            }
            // logMemoryStats("-");

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            FileInputStream is = new FileInputStream(fileName);
            // logMemoryStats("-");
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 40, baos);
            // logMemoryStats("-");

            byte[] bytes = baos.toByteArray();
            // logMemoryStats("-");
            String base64String = null;
            if (bytes != null) {
                base64String = Base64.encodeToString(bytes, Base64.DEFAULT);
            }
            // logMemoryStats("-");

            is.close();
            is = null;
            bitmap.recycle();
            bitmap = null;
            bytes = null;
            System.gc();
            logMemoryStats("结束");

            return base64String;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param bitmap
     * @return
     */
    public static String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param string
     * @return bitmap
     */
    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void logMemoryStats(String description) {
        ActivityManager.MemoryInfo mi1 = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) BaseApplication.getInstance().getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        Runtime rt = Runtime.getRuntime();
        long vmHeap = rt.totalMemory();
        long vmAlloc = rt.totalMemory() - rt.freeMemory();
        long nativeHeap = Debug.getNativeHeapSize();
        long nativeAlloc = Debug.getNativeHeapAllocatedSize();

        String text = String.format("%sMB; %s, %s, %s; %s, %s, %s", am.getMemoryClass(), formatMemoeryText(nativeAlloc
                + vmAlloc), formatMemoeryText(nativeAlloc), formatMemoeryText(vmAlloc), formatMemoeryText(vmHeap
                + nativeHeap), formatMemoeryText(nativeHeap), formatMemoeryText(vmHeap));
        Log.e(description, text);
    }

    private static String formatMemoeryText(long memory) {
        float memoryInMB = memory * 1f / 1024 / 1024;
        return String.format("%.1fMB", memoryInMB);
    }

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public static void forceRefreshSystemAlbum(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        String type = options.outMimeType;

        MediaScannerConnection
                .scanFile(BaseApplication.getInstance().getApplicationContext(), new String[]{path}, new String[]{type}, null);

    }

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUrl
     * @return
     * @throws MalformedURLException
     */
    public static Bitmap getbitmapByNetUrl(String imageUrl) {
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) myFileUrl.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
        if (checkSDCardAvailable()) {
            File photoFile = new File(path, photoName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (Exception e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void savePhotoToSDCard(Bitmap photoBitmap, String path) {
        if (checkSDCardAvailable()) {
            File photoFile = new File(path);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (Exception e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Check the SD card
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 根据路径加载bitmap
     *
     * @param path 路径
     * @param w    宽
     * @param h    长
     * @return
     */
    public static final Bitmap convertToBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 设置为ture只获取图片大小
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Config.ARGB_8888;
            // 返回为空
            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {
                // 缩放
                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(),
                    null, true);

            if (bMapRotate != null) {
                // return getBitmapByExifInterface(path, bMapRotate);
                return bMapRotate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmapByUrl(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 旋转图片
     *
     * @param image_path
     * @param bitmap
     * @return
     */
    public static Bitmap getBitmapByExifInterface(String image_path, Bitmap bitmap) {

        // 读取图片中的方向信息
        int digree = 0;
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(image_path);
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        if (exifInterface != null) {
            // 读取图片中相机方向信息
            int ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        // 旋转图片
        if (digree != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(digree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
}
