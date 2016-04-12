package com.colin.hunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.help.ImageHelp;
import com.colin.hunter.help.ToastHelp;
import com.colin.hunter.view.GetHeadImagePopupWindow;

public class RegisterActivity extends BaseAppCompatActivity implements TextWatcher, View.OnClickListener, View.OnFocusChangeListener {
    private TextView text_register_xieyi;
    private EditText edit_register_nick;
    private EditText edit_register_phone;
    private EditText edit_register_password;
    private Button button_register;
    private ImageView image_register_head;
    private ImageView image_register_password_hide;
    private ImageView image_register_delete_nick;
    private ImageView image_register_delete_phone;
    private ImageView image_register_delete_password;
    private GetHeadImagePopupWindow getHeadImagePopupWindow = null;
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onDestroy() {
        if (getHeadImagePopupWindow != null) {
            getHeadImagePopupWindow = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar);
        this.edit_register_nick = (EditText) this.findViewById(R.id.edit_register_nick);
        this.edit_register_phone = (EditText) this.findViewById(R.id.edit_register_phone);
        this.edit_register_password = (EditText) this.findViewById(R.id.edit_register_password);

        this.text_register_xieyi = (TextView) this.findViewById(R.id.text_register_xieyi);
        this.button_register = (Button) this.findViewById(R.id.button_register);
        this.image_register_head = (ImageView) this.findViewById(R.id.image_register_head);
        this.image_register_password_hide = (ImageView) this.findViewById(R.id.image_register_password_hide);

        this.image_register_delete_nick = (ImageView) this.findViewById(R.id.image_register_delete_nick);
        this.image_register_delete_phone = (ImageView) this.findViewById(R.id.image_register_delete_phone);
        this.image_register_delete_password = (ImageView) this.findViewById(R.id.image_register_delete_password);


    }

    private void initToolbar(Toolbar toolbar) {
        //        图标
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        标题  标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setTitle(getResources().getString(R.string.register));
//        副标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + "点击上面的" + "\"" + "注册" + "\"" + "按钮,即表示你同意" + "</font>" + "<u>" + "<font color=" + "\"" + "#576B95" + "\">" + "《腾讯微信软件许可及服务协议》" + "</font>" + "</u>";
        text_register_xieyi.setText(Html.fromHtml(xieyi));
    }

    @Override
    protected void initListener() {
        this.edit_register_phone.addTextChangedListener(this);
        this.edit_register_password.addTextChangedListener(this);
        this.edit_register_nick.addTextChangedListener(this);
        this.button_register.setOnClickListener(this);
        this.image_register_head.setOnClickListener(this);
        this.image_register_password_hide.setOnClickListener(this);
        this.edit_register_password.setOnFocusChangeListener(this);

        this.image_register_delete_nick.setOnClickListener(this);
        this.image_register_delete_phone.setOnClickListener(this);
        this.image_register_delete_password.setOnClickListener(this);
    }

    @Override
    protected void getAsynData() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean sign1 = edit_register_nick.getText().length() > 0;
        boolean sign2 = edit_register_phone.getText().length() > 0;
        boolean sign3 = edit_register_password.getText().length() > 0;
        if (sign1 && sign2 && sign3) {
            button_register.setEnabled(true);
        } else {
            button_register.setEnabled(false);
        }
        image_register_delete_nick.setVisibility(sign1 == true ? View.VISIBLE : View.INVISIBLE);
        image_register_delete_phone.setVisibility(sign2 == true ? View.VISIBLE : View.INVISIBLE);
        image_register_delete_password.setVisibility(sign3 == true ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                break;
            case R.id.image_register_head:
                hideKeyword();
                showPopupWindow();
                break;
            case R.id.image_register_password_hide:
                showPassWord();
                break;
            case R.id.image_register_delete_nick:
                edit_register_nick.setText("");
                break;
            case R.id.image_register_delete_phone:
                edit_register_phone.setText("");
                break;
            case R.id.image_register_delete_password:
                edit_register_password.setText("");
                break;
        }
    }

    private void showPassWord() {
        isShow = !isShow;
        image_register_password_hide.setSelected(isShow);
        if (isShow) {
            edit_register_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else if (!isShow) {
            edit_register_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    /**
     * 选择图片
     */
    private void showPopupWindow() {
        if (getHeadImagePopupWindow == null) {
            getHeadImagePopupWindow = new GetHeadImagePopupWindow(this);
        }
        // 显示窗口
        getHeadImagePopupWindow.showAtLocation(
                RegisterActivity.this.findViewById(R.id.linear_register_content), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
        getHeadImagePopupWindow.setOnPopupWindowHeadLister(new GetHeadImagePopupWindow.OnPopupWindowHeadLister() {
            @Override
            public void click(String imagepath) {
                if (!TextUtils.isEmpty(imagepath)) {
                    image_register_head.setImageBitmap(ImageHelp.getLoacalBitmapByUrl(imagepath));
                } else {
                    ToastHelp.showToast(RegisterActivity.this, "图片获取失败");
                }
            }
        });

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.edit_login_password:
                image_register_password_hide.setSelected(hasFocus);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getHeadImagePopupWindow != null) {
            getHeadImagePopupWindow.onActivityResult(requestCode, resultCode, data);
        }
    }
}
