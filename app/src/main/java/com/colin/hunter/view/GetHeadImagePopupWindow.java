package com.colin.hunter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.colin.hunter.CutImageActivity;
import com.colin.hunter.R;
import com.colin.hunter.data.Constants;

import java.io.File;

/**
 * Created by Administrator on 2016/1/19.
 * 主界面重写onActivityResult方法
 * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * if (getHeadImagePopupWindow != null) {
 * getHeadImagePopupWindow.onActivityResult(requestCode, resultCode, data);
 * }
 * }
 */
public class GetHeadImagePopupWindow extends PopupWindow implements View.OnClickListener {
    private Activity activity;
    private View view;
    private Button button_popupwindow_head_album;
    private Button button_popupwindow_head_camera;
    private Button button_popupwindow_head_cancle;
    // 设置头像
    public static final int CHOOSE_IMAGE_ALBUM = 0; // 相册
    public static final int CHOOSE_IMAGE_CAMERA = 1; // 拍照
    public static final int CHOOSE_IMAGE_CUT = 2; // 截取
    private String camera_save_image;// 保存路径
    private String camera_save_name;// 图片名
    private String image_path;// 图片全路径

    private OnPopupWindowHeadLister onPopupWindowHeadLister = null;

    public void setOnPopupWindowHeadLister(OnPopupWindowHeadLister onPopupWindowHeadLister) {
        this.onPopupWindowHeadLister = onPopupWindowHeadLister;
    }

    public GetHeadImagePopupWindow(Activity activity) {
        super(activity);
        this.activity = activity;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(R.layout.popupwindow_getheadimage, null);
        this.button_popupwindow_head_album = (Button) this.view.findViewById(R.id.button_popupwindow_head_album);
        this.button_popupwindow_head_camera = (Button) this.view.findViewById(R.id.button_popupwindow_head_camera);
        this.button_popupwindow_head_cancle = (Button) this.view.findViewById(R.id.button_popupwindow_head_cancle);

        this.button_popupwindow_head_album.setOnClickListener(this);
        this.button_popupwindow_head_camera.setOnClickListener(this);
        this.button_popupwindow_head_cancle.setOnClickListener(this);
        // 设置允许在外点击消失
        this.setOutsideTouchable(false);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(android.R.style.Animation_InputMethod);
        // 设置背景色
        Resources resources = view.getResources();
        Drawable drawable = resources.getDrawable(android.R.color.white);
        this.setBackgroundDrawable(drawable);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_popupwindow_head_camera:
                chooiceHeadCamera();
                break;
            case R.id.button_popupwindow_head_album:
                chooiceHeadAlbum();
                break;
            case R.id.button_popupwindow_head_cancle:
                dismiss();
                break;
        }
    }

    private void chooiceHeadCamera() {
        initFile();
        camera_save_name = String.valueOf(System.currentTimeMillis()) + ".png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(camera_save_image, camera_save_name));
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, CHOOSE_IMAGE_CAMERA);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void chooiceHeadAlbum() {
        initFile();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, CHOOSE_IMAGE_ALBUM);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    /**
     * 初始化图片路径
     */
    private void initFile() {
        File file = new File(Constants.IMAGE_HEAD_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        camera_save_image = Constants.IMAGE_HEAD_PATH;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            Intent intent = null;
            Bundle bundle = null;
            switch (requestCode) {
                case CHOOSE_IMAGE_CAMERA:
                    image_path = camera_save_image + File.separator + camera_save_name;
                    uri = Uri.fromFile(new File(image_path));
                    //图片剪切
                    bundle = new Bundle();
                    bundle.putBoolean("iscamera", true);
                    bundle.putString("image_path", image_path);
                    intent = new Intent(activity, CutImageActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, CHOOSE_IMAGE_CUT);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case CHOOSE_IMAGE_ALBUM:
                    if (data == null) {
                        return;
                    }
                    uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = activity.getContentResolver().query(uri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    image_path = cursor.getString(column_index);// 图片在的路径
                    //图片剪切
                    bundle = new Bundle();
                    bundle.putString("image_path", image_path);
                    intent = new Intent(activity, CutImageActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, CHOOSE_IMAGE_CUT);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case CHOOSE_IMAGE_CUT:
                    final String temppath = data.getStringExtra("image_path");
                    if (onPopupWindowHeadLister != null) {
                        onPopupWindowHeadLister.click(temppath);
                    }
                    break;

            }
        }
    }


    public interface OnPopupWindowHeadLister {
        void click(String imagepath);
    }
}
