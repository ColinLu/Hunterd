package com.colin.hunter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.data.Constants;
import com.colin.hunter.view.CutImageLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;


public class CutImageActivity extends BaseAppCompatActivity {
    private CutImageLayout cutimagelayout_cut_show;
    private String image_path = "";
    private boolean iscamera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_image);
    }


    @Override
    protected void initView() {
        this.text_title.setText("剪切图片");
        this.text_title.setVisibility(View.VISIBLE);
        this.image_title_back.setVisibility(View.VISIBLE);
        this.cutimagelayout_cut_show = (CutImageLayout) findViewById(R.id.cutimagelayout_cut_show);
        getData();
        getImage();

    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            image_path = bundle.getString("image_path");
            iscamera = bundle.getBoolean("iscamera", false);
        }
    }

    private void getImage() {
        if (TextUtils.isEmpty(image_path) || !(new File(image_path).exists())) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = convertToBitmap(image_path, 600, 600);
        if (bitmap == null) {
            Toast.makeText(this, "没有找到图片", Toast.LENGTH_SHORT).show();
            return;
        }
        cutimagelayout_cut_show.setBitmap(getBitmapByExifInterface(image_path, bitmap));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void getAsynData() {

    }

    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.button_cut_ok:
                cutImage();
                break;
        }
    }

    private void cutImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = cutimagelayout_cut_show.cutRectangle();
//                Bitmap bitmap = cutimagelayout_cut_show.cutImageCircle();
                String image_path = Constants.IMAGE_HEAD_PATH + File.separator + System.currentTimeMillis() + ".png";
                savePhotoToSDCard(bitmap, image_path);
                Intent intent = new Intent();
                intent.putExtra("image_path", image_path);
                setResult(RESULT_OK, intent);
                finish();
            }
        }).start();
    }

    /**
     * 根据路径加载bitmap
     *
     * @param path 路径
     * @param w    款
     * @param h    长
     * @return
     */
    public static final Bitmap convertToBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 设置为ture只获取图片大小
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
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


    public static void savePhotoToSDCard(Bitmap photoBitmap, String path) {
        if (checkSDCardAvailable()) {
            File photoFile = new File(path);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
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
}
