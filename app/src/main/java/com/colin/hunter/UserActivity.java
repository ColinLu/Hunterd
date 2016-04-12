package com.colin.hunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.ImageHelp;
import com.colin.hunter.help.OptionsHelp;
import com.colin.hunter.help.ToastHelp;
import com.colin.hunter.view.EditViewPopupWindow;
import com.colin.hunter.view.GetHeadImagePopupWindow;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private TextView text_user_name;
    private TextView text_user_company;
    private TextView text_user_validity;
    private TextView text_user_contact;
    private TextView text_user_email;
    private ImageView image_toolbar_background;
    private EditViewPopupWindow editViewPopupWindow = null;
    private GetHeadImagePopupWindow getHeadImagePopupWindow = null;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    @Override
    protected void onDestroy() {
        if (editViewPopupWindow != null) {
            editViewPopupWindow = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        initToolbar();
        this.text_user_name = (TextView) this.findViewById(R.id.text_user_name);
        this.text_user_company = (TextView) this.findViewById(R.id.text_user_company);
        this.text_user_validity = (TextView) this.findViewById(R.id.text_user_validity);
        this.text_user_contact = (TextView) this.findViewById(R.id.text_user_contact);
        this.text_user_email = (TextView) this.findViewById(R.id.text_user_email);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.image_toolbar_background = (ImageView) this.findViewById(R.id.image_toolbar_background);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbar.setTitle("Colin");
        collapsingToolbar.setForegroundGravity(Gravity.CENTER);
        imageLoader.displayImage(Constants.image_url[9], this.image_toolbar_background, OptionsHelp.getListOptions());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        this.findViewById(R.id.linear_user_name).setOnClickListener(this);
        this.findViewById(R.id.linear_user_company).setOnClickListener(this);
        this.findViewById(R.id.linear_user_validity).setOnClickListener(this);

        this.findViewById(R.id.text_user_contact_edit).setOnClickListener(this);
        this.findViewById(R.id.text_user_email_edit).setOnClickListener(this);
        this.image_toolbar_background.setOnClickListener(this);
    }

    @Override
    protected void getAsynData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_user_name:
                showPopwindow(Constants.EDIT_USER_NAME);
                break;
            case R.id.linear_user_company:
                showPopwindow(Constants.EDIT_USER_COMPANY);
                break;
            case R.id.linear_user_validity:
                showPopwindow(Constants.EDIT_USER_VALIDITY);
                break;
            case R.id.text_user_contact_edit:
                showPopwindow(Constants.EDIT_USER_CONTACT);
                break;
            case R.id.text_user_email_edit:
                showPopwindow(Constants.EDIT_USER_EMAIL);
                break;
            case R.id.image_toolbar_background:
                hideKeyword();
                showPopupWindow();
                break;
        }
    }

    private void showPopwindow(final int which) {
        String title = "";
        String content = "";
        if (which == Constants.EDIT_USER_NAME) {
            title = "修改姓名";
            content = text_user_name.getText().toString().trim();
        } else if (which == Constants.EDIT_USER_COMPANY) {
            title = "修改公司名称";
            content = text_user_company.getText().toString().trim();
        } else if (which == Constants.EDIT_USER_VALIDITY) {
            title = "修改有效期";
            content = text_user_validity.getText().toString().trim();
        } else if (which == Constants.EDIT_USER_CONTACT) {
            title = "修改联系电话";
            content = text_user_contact.getText().toString().trim();
        } else if (which == Constants.EDIT_USER_EMAIL) {
            title = "修改邮箱地址";
            content = text_user_email.getText().toString().trim();
        }

        editViewPopupWindow = new EditViewPopupWindow(this, title, content);
        // 显示窗口
        editViewPopupWindow.showAtLocation(this.findViewById(R.id.coordinator_user_content), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        editViewPopupWindow.setOnEditViewPopupWindowListener(new EditViewPopupWindow.OnEditViewPopupWindowListener() {
            @Override
            public void click(boolean b, String string) {
                if (b) {
                    setText(which, string);
                    editViewPopupWindow.dismiss();
                    editViewPopupWindow = null;
                }
            }
        });
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
                UserActivity.this.findViewById(R.id.coordinator_user_content), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
        getHeadImagePopupWindow.setOnPopupWindowHeadLister(new GetHeadImagePopupWindow.OnPopupWindowHeadLister() {
            @Override
            public void click(String imagepath) {
                if (!TextUtils.isEmpty(imagepath)) {
                    image_toolbar_background.setImageBitmap(ImageHelp.getLoacalBitmapByUrl(imagepath));
                } else {
                    ToastHelp.showToast(UserActivity.this, "图片获取失败");
                }
            }
        });

    }

    private void setText(int which, String string) {
        if (which == Constants.EDIT_USER_NAME) {
            text_user_name.setText(string);
        } else if (which == Constants.EDIT_USER_COMPANY) {
            text_user_company.setText(string);
        } else if (which == Constants.EDIT_USER_VALIDITY) {
            text_user_validity.setText(string);
        } else if (which == Constants.EDIT_USER_CONTACT) {
            text_user_contact.setText(string);
        } else if (which == Constants.EDIT_USER_EMAIL) {
            text_user_email.setText(string);
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
