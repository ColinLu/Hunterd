package com.colin.hunter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.help.LogHelp;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

/**
 * 从其他APP中获取简历 pdf文件
 */
public class GetCurriculumVitaeActivity extends BaseAppCompatActivity implements OnPageChangeListener, OnDrawListener {
    private PDFView pdfview_curriculum_vitae;
    public static final String RESUME_FILE = "resume.pdf";
    private String pdfName = RESUME_FILE;
    private Integer pageNumber = 1;
    private Uri uri = null;
    private AlertDialog.Builder mAlertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_curriculum_vitae);
    }

    @Override
    protected void initView() {
        getData();
        image_title_back.setVisibility(View.VISIBLE);
        text_title.setVisibility(View.VISIBLE);
        setTitleText("其他界面");
        this.pdfview_curriculum_vitae = (PDFView) findViewById(R.id.pdfview_curriculum_vitae);
    }

    private void setTitleText(String s) {
        this.text_title.setText(s);
    }

    //    为了处理从Intent带过来的数据，可以通过调用getIntent()方法来获取到Intent对象。一旦你拿到这个对象，你可以对里面的数
//    据进行判断，从而决定下一步应该做什么。请记住，如果一个activity可以被其他的程序启动，你需要在检查intent的时候考虑
//    这种情况(是被其他程序而调用启动的)。
    private void getData() {
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        uri = intent.getData();
        // action=android.intent.action.VIEW
        // type  =application/pdf
        if (null != uri) {
            LogHelp.d("uri  =" + uri.toString());
            //    uri  =file:///storage/emulated/0/Tencent/QQfile_recv/Android%E6%8E%A7%E4%BB%B6%E5%A4%A7%E5%85%A8.pdf
        }

        LogHelp.d("action=" + action.toString());
        LogHelp.d("type  =" + type.toString());
    }

    @Override
    protected void initData() {
        display(pdfName, false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void getAsynData() {

    }

    private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage) pageNumber = 1;
        setTitle(pdfName = assetFileName);
        if (uri != null) {
//            pdfview_curriculum_vitae.fromFile(new File(uri.toString()))
//                    .defaultPage(pageNumber)
//                    .onPageChange(this)
//                    .swipeVertical(true)
//                    .enableSwipe(true)
//                    .onDraw(this)
//                    .load();
        } else {
            pdfview_curriculum_vitae.fromAsset(assetFileName)
                    .defaultPage(pageNumber)
                    .onPageChange(this)
                    .swipeVertical(true)
                    .enableSwipe(true)
                    .onDraw(this)
                    .load();
        }

    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitleText(String.format("%s %s / %s", pdfName, page, pageCount));
    }

}
