package com.colin.hunter;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.bean.CandidateBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.OptionsHelp;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CandidateDetailActivity extends BaseAppCompatActivity implements OnPageChangeListener, OnDrawListener {
    private String title = "";
    private CandidateBean candidateBean = null;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView image_toolbar_background;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private PDFView pdfview_candidate_detail;
    public static final String RESUME_FILE = "resume.pdf";
    String pdfName = RESUME_FILE;
    Integer pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_detail);
    }

    @Override
    protected void initView() {
        getData();
        initToolbar();

        pdfview_candidate_detail = (PDFView) findViewById(R.id.pdfview_candidate_detail);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
//            candidateBean = bundle.getParcelable("class");
        }
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.image_toolbar_background = (ImageView) this.findViewById(R.id.image_toolbar_background);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbar.setTitle(title);
        collapsingToolbar.setForegroundGravity(Gravity.CENTER);
        imageLoader.displayImage(Constants.image_url[9], this.image_toolbar_background, OptionsHelp.getListOptions());
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

        pdfview_candidate_detail.fromAsset(assetFileName)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .swipeVertical(true)
                .enableSwipe(true)
                .onDraw(this)
                .load();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        this.collapsingToolbar.setTitle(String.format("%s %s / %s", pdfName, page, pageCount));
    }
}
