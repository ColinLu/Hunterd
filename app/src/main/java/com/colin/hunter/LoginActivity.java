package com.colin.hunter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.colin.hunter.base.BaseAppCompatActivity;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener, TextWatcher {
    private EditText edit_login_phone;
    private EditText edit_login_password;
    private Button button_login_ok;
    private Button button_login_register;
    private ImageView image_login_delete_phone;
    private ImageView image_login_delete_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        initToolbar();
        this.edit_login_phone = (EditText) this.findViewById(R.id.edit_login_phone);
        this.edit_login_password = (EditText) this.findViewById(R.id.edit_login_password);
        this.button_login_ok = (Button) this.findViewById(R.id.button_login_ok);
        this.button_login_register = (Button) this.findViewById(R.id.button_login_register);
        this.image_login_delete_phone = (ImageView) this.findViewById(R.id.image_login_delete_phone);
        this.image_login_delete_password = (ImageView) this.findViewById(R.id.image_login_delete_password);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //        图标
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        标题  标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setTitle(getResources().getString(R.string.login));
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

    }

    @Override
    protected void initListener() {
        this.button_login_ok.setOnClickListener(this);
        this.button_login_register.setOnClickListener(this);
        this.image_login_delete_phone.setOnClickListener(this);
        this.image_login_delete_password.setOnClickListener(this);
        this.edit_login_phone.addTextChangedListener(this);
        this.edit_login_password.addTextChangedListener(this);
    }

    @Override
    protected void getAsynData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login_ok:
                startActivity(RegisterActivity.class, null, false);
                break;
            case R.id.button_login_register:
                startActivity(RegisterActivity.class, null, false);
                break;
            case R.id.image_login_delete_phone:
                edit_login_phone.setText("");
                break;
            case R.id.image_login_delete_password:
                edit_login_password.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean sign1 = edit_login_phone.getText().length() > 0;
        boolean sign2 = edit_login_password.getText().length() > 0;
        if (sign1 && sign2) {
            button_login_ok.setEnabled(true);
        } else {
            button_login_ok.setEnabled(false);
        }
        image_login_delete_phone.setVisibility(sign1 == true ? View.VISIBLE : View.INVISIBLE);
        image_login_delete_password.setVisibility(sign2 == true ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
