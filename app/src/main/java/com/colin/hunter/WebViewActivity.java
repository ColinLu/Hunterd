package com.colin.hunter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.colin.hunter.base.BaseAppCompatActivity;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.OptionsHelp;
import com.nostra13.universalimageloader.core.ImageLoader;


public class WebViewActivity extends BaseAppCompatActivity {
    private WebView webview_content;
    private ProgressBar progress_bar_webview;
    private String title = "";
    private String url = "";
    private ImageView image_toolbar_background;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void initView() {
        getData();
        initToolbar();
        this.webview_content = (WebView) this.findViewById(R.id.webview_content);
        this.progress_bar_webview = (ProgressBar) this.findViewById(R.id.progress_bar_webview);

        initWebView();
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
                webviewBack();
            }
        });

        collapsingToolbar.setTitle(title);
        collapsingToolbar.setForegroundGravity(Gravity.CENTER);
        imageLoader.displayImage(Constants.image_url[9], this.image_toolbar_background, OptionsHelp.getListOptions());
    }

    private void initWebView() {
        if (url.substring(0, 5).equals("file:")) {//加载本地文件数据
            // 设置支持JavaScript脚本
            WebSettings webSettings = webview_content.getSettings();
            webSettings.setDefaultTextEncodingName("UTF-8");
            webSettings.setJavaScriptEnabled(true);// 设置可以运行JS脚本
            // 设置可以访问文件
            webSettings.setAllowFileAccess(true);
            // 设置可以支持缩放
            webSettings.setSupportZoom(false);
            // 允许js弹出窗口
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);// 打开页面时， 自适应屏幕
            webSettings.setLoadWithOverviewMode(true);// 打开页面时， 自适应屏幕
            webSettings.setTextZoom(240);
            // webSettings.setTextZoom(120);//Sets the text zoom of the page in
            // percent. The default is 100.
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webview_content.setBackgroundResource(R.color.transparent);
            webview_content.loadUrl(url);// 加载网址
            // 添加js交互接口类，并起别名 imagelistner
            webview_content.setWebChromeClient(new MyWebChromeClient());
            webview_content.setWebViewClient(new MyWebViewClient());
        } else {//加载网络数据
            // 设置支持JavaScript脚本
            WebSettings webSettings = webview_content.getSettings();
            webSettings.setDefaultTextEncodingName("UTF-8");
            webSettings.setJavaScriptEnabled(true);// 设置可以运行JS脚本
            // 设置可以访问文件
            webSettings.setAllowFileAccess(true);
            // 设置可以支持缩放
            webSettings.setSupportZoom(false);
            // 设置默认缩放方式尺寸是far
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            // 设置出现缩放工具
            webSettings.setBuiltInZoomControls(false);
            webSettings.setDefaultFontSize(20);
            // 允许js弹出窗口
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);// 打开页面时， 自适应屏幕
            webSettings.setLoadWithOverviewMode(true);// 打开页面时， 自适应屏幕
            // webSettings.setTextZoom(120);//Sets the text zoom of the page in
            // percent. The default is 100.
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webview_content.setBackgroundResource(R.color.transparent);
            webview_content.loadUrl(url);// 加载网址
            // 添加js交互接口类，并起别名 imagelistner
            webview_content.setWebChromeClient(new MyWebChromeClient());
            webview_content.setWebViewClient(new MyWebViewClient());
        }
//        Android本身不支持打开pdf文件，搜了好多资料一直没有找到合适的方法，后来发现google提供了在线解析pdf。方法其实很简单。
//        webview_content.getSettings().setJavaScriptEnabled(true);
//        String pdf = "file:///storage/emulated/0/Tencent/QQfile_recv/Android%E6%8E%A7%E4%BB%B6%E5%A4%A7%E5%85%A8.pdf";
//        webview_content.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);

    }

    // 监听
    private class MyWebViewClient extends WebViewClient {
        // url拦截
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
            view.loadUrl(url);
            // 相应完成返回true
            return true;
            // return super.shouldOverrideUrlLoading(view, url);
        }

        // 页面开始加载
        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            progress_bar_webview.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(webView, url, favicon);
        }

        // 页面加载完成
        @Override
        public void onPageFinished(WebView webView, String url) {
            // webview_content.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(webView, url);
            progress_bar_webview.setVisibility(View.GONE);
            webview_content.setVisibility(View.VISIBLE);
            webview_content.invalidate();
        }

        // WebView加载的所有资源url
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            progress_bar_webview.setVisibility(View.GONE);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        // 处理javascript中的alert
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        ;

        @Override
        // 处理javascript中的confirm
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        ;

        @Override
        // 处理javascript中的prompt
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                                  final JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        ;

        // 设置网页加载的进度条
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progress_bar_webview.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        // 设置程序的Title
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            url = bundle.getString("url");
//            url = "http://weather1.sina.cn";
        }

    }

    private boolean webviewBack() {
        if (webview_content.canGoBack()) {
            webview_content.goBack();
            return false;
        } else {
            closeActivity();
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return webviewBack();
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
}
