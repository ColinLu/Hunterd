package com.colin.hunter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.bean.PositionBean;
import com.colin.hunter.help.ToastHelp;

/**
 * 发布职位
 */
public class ReleasePositionActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String title;
    private PositionBean positionBean;
    private boolean is_finish = true;
    private TextView text_release_position_recommend_number;
    private TextView text_release_position_time;
    private TextView text_release_position_company;
    private TextView text_release_position_city;
    private TextView text_release_position_position;
    private TextView text_release_position_vacancy;
    private TextView text_release_position_profession;
    private TextView text_release_position_function;
    private TextView text_release_position_annual_salary;
    private TextView text_release_position_report_to;
    private TextView text_release_position_job_description;
    private TextView text_release_position_job_requirements;
    private TextView text_release_position_job_selling_point;
    private TextView text_release_position_interview_address;

    private LinearLayout linear_release_position_buttons;
    private Button button_release_position_reject;
    private Button button_release_position_receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_position);
    }

    @Override
    protected void initView() {
        getData();
        initToolbar();

        this.text_release_position_recommend_number = (TextView) this.findViewById(R.id.text_release_position_recommend_number);
        this.text_release_position_time = (TextView) this.findViewById(R.id.text_release_position_time);
        this.text_release_position_company = (TextView) this.findViewById(R.id.text_release_position_company);
        this.text_release_position_city = (TextView) this.findViewById(R.id.text_release_position_city);
        this.text_release_position_position = (TextView) this.findViewById(R.id.text_release_position_position);
        this.text_release_position_vacancy = (TextView) this.findViewById(R.id.text_release_position_vacancy);
        this.text_release_position_profession = (TextView) this.findViewById(R.id.text_release_position_profession);
        this.text_release_position_function = (TextView) this.findViewById(R.id.text_release_position_function);
        this.text_release_position_annual_salary = (TextView) this.findViewById(R.id.text_release_position_annual_salary);
        this.text_release_position_report_to = (TextView) this.findViewById(R.id.text_release_position_report_to);
        this.text_release_position_job_description = (TextView) this.findViewById(R.id.text_release_position_job_description);
        this.text_release_position_job_requirements = (TextView) this.findViewById(R.id.text_release_position_job_requirements);
        this.text_release_position_job_selling_point = (TextView) this.findViewById(R.id.text_release_position_job_selling_point);
        this.text_release_position_interview_address = (TextView) this.findViewById(R.id.text_release_position_interview_address);

        this.linear_release_position_buttons = (LinearLayout) this.findViewById(R.id.linear_release_position_buttons);
        this.button_release_position_reject = (Button) this.findViewById(R.id.button_release_position_reject);
        this.button_release_position_receive = (Button) this.findViewById(R.id.button_release_position_receive);

        if (is_finish) {
            this.linear_release_position_buttons.setVisibility(View.GONE);
            this.button_release_position_reject.setVisibility(View.GONE);
            this.button_release_position_receive.setVisibility(View.GONE);
        } else if (!is_finish) {
            this.linear_release_position_buttons.setVisibility(View.VISIBLE);
            this.button_release_position_reject.setVisibility(View.VISIBLE);
            this.button_release_position_receive.setVisibility(View.VISIBLE);
        }

    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            is_finish = bundle.getBoolean("is_finish", true);
            positionBean = bundle.getParcelable("class");
        }
    }

    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        图标
////        toolbar.setLogo(R.mipmap.ic_launcher);
////        标题  标题的文字需在setSupportActionBar之前，不然会无效
//        toolbar.setTitle(title);
////        副标题
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });


        this.text_title.setText(positionBean.getName());
        this.text_title.setVisibility(View.VISIBLE);
        this.image_title_back.setVisibility(View.VISIBLE);
    }


    @Override
    protected void initData() {
        if (positionBean != null) {
            setViewData();
        }


    }

    private void setViewData() {
//        this.text_release_position_company.setText(String.valueOf(positionBean.ge));
//        this.text_release_position_city.setText(String.valueOf(positionBean.getc));
//        this.text_release_position_position.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_vacancy.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_profession.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_function.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_annual_salary.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_report_to.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_job_description.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_job_requirements.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_job_selling_point.setText(String.valueOf(positionBean.getMax_salary()));
//        this.text_release_position_interview_address.setText(String.valueOf(positionBean.getMax_salary()));
    }

    @Override
    protected void initListener() {
        this.button_release_position_reject.setOnClickListener(this);
        this.button_release_position_receive.setOnClickListener(this);
    }

    @Override
    protected void getAsynData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_release_position_reject:
                ToastHelp.showToast(ReleasePositionActivity.this, "拒接");
                break;
            case R.id.button_release_position_receive:
                ToastHelp.showToast(ReleasePositionActivity.this, "接受");
                break;
        }
    }
}
