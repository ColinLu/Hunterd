package com.colin.hunter.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colin.hunter.R;


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
public class EditViewPopupWindow extends PopupWindow implements View.OnClickListener, TextWatcher {
    private Activity activity;
    private View view;
    private TextView text_popupwindow_edit_title;
    private EditText edit_popupwindow_edit_content;
    private Button button_popupwindow_edit_ok;

    private OnEditViewPopupWindowListener onEditViewPopupWindowListener = null;

    public void setOnEditViewPopupWindowListener(OnEditViewPopupWindowListener onPopupWindowHeadLister) {
        this.onEditViewPopupWindowListener = onPopupWindowHeadLister;
    }

    public EditViewPopupWindow(Activity activity, String title) {
        super(activity);
        this.activity = activity;
        initView(title, "");
    }

    public EditViewPopupWindow(Activity activity, String title, String editContent) {
        super(activity);
        this.activity = activity;
        initView(title, editContent);
    }

    private void initView(String title, String editContent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(R.layout.popupwindow_edittext, null);
        this.text_popupwindow_edit_title = (TextView) this.view.findViewById(R.id.text_popupwindow_edit_title);
        this.edit_popupwindow_edit_content = (EditText) this.view.findViewById(R.id.edit_popupwindow_edit_content);
        this.button_popupwindow_edit_ok = (Button) this.view.findViewById(R.id.button_popupwindow_edit_ok);

        this.edit_popupwindow_edit_content.setText(editContent);
        if (TextUtils.isEmpty(title)) {
            this.text_popupwindow_edit_title.setText(activity.getString(R.string.edit));
        } else {
            this.text_popupwindow_edit_title.setText(title);
        }
        this.button_popupwindow_edit_ok.setEnabled(!TextUtils.isEmpty(editContent));
        this.button_popupwindow_edit_ok.setOnClickListener(this);
        this.edit_popupwindow_edit_content.addTextChangedListener(this);


        //弹出软键盘
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //再设置模式，和Activity的一样，覆盖，调整大小。
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
            case R.id.button_popupwindow_edit_ok:
                boolean sign = edit_popupwindow_edit_content.getText().length() > 0;
                if (onEditViewPopupWindowListener != null) {
                    onEditViewPopupWindowListener.click(sign, edit_popupwindow_edit_content.getText().toString());
                    dismiss();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean sign1 = edit_popupwindow_edit_content.getText().length() > 0;
        button_popupwindow_edit_ok.setEnabled(sign1);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnEditViewPopupWindowListener {
        void click(boolean b, String string);
    }
}
