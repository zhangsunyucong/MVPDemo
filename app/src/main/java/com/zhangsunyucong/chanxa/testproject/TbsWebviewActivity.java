package com.zhangsunyucong.chanxa.testproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.librarys.materialsearchview.MaterialSearchView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import com.example.librarys.utils.RegexUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;

import java.util.ArrayList;

import butterknife.BindView;

@Route(path = ARouterConstant.PATH_ACTIVITY_TBS_WEBVIEW)
public class TbsWebviewActivity extends BaseActivity {

    private final static String EXTRA_KEY_LINL = "EXTRA_KEY_LINL";
    private final static String EXTRA_KEY_SHOW_SEARCH = "EXTRA_KEY_SHOW_SEARCH";

    @BindView(R.id.webview)
    com.tencent.smtt.sdk.WebView webview;

    @BindView(R.id.search_view)
    MaterialSearchView search_view;

    private String mTitle;
    private String mLink;
    private ValueCallback<Uri> uploadFile;
    private boolean mShowSearch;

    public static void startActivity(BaseActivity activity,
                                     String title, String link) {
        if(ValidateUtils.checkNotNULL(activity)) {
            Intent intent = new Intent(activity, TbsWebviewActivity.class);
            intent.putExtra(KEY_EXTRA_TITLE, title);
            intent.putExtra(EXTRA_KEY_LINL, link);
            activity.startActivity(intent);
        }
    }

    public static void startActivity(BaseActivity activity, boolean showSearch,
                                     String title, String link) {
        if(ValidateUtils.checkNotNULL(activity)) {
            Intent intent = new Intent(activity, TbsWebviewActivity.class);
            intent.putExtra(KEY_EXTRA_TITLE, title);
            intent.putExtra(EXTRA_KEY_LINL, link);
            intent.putExtra(EXTRA_KEY_SHOW_SEARCH, showSearch);
            activity.startActivity(intent);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tbs_webview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void onTitileRightClick(View forwardView) {
        super.onTitileRightClick(forwardView);
        if(forwardView.getId() == R.id.img_right) {
            if(mShowSearch) {
                if(ValidateUtils.checkNotNULL(search_view)) {
                    search_view.showSearch();
                }
            }
        }

    }

    @Override
    protected void getIntentData(Intent intent) {
        super.getIntentData(intent);
        if(ValidateUtils.checkNotNULL(intent)) {
            mTitle = intent.getStringExtra(KEY_EXTRA_TITLE);
            mLink = intent.getStringExtra(EXTRA_KEY_LINL);
            mShowSearch = intent.getBooleanExtra(EXTRA_KEY_SHOW_SEARCH, false);
        }
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleText(mTitle);
        showTitleRightView(R.drawable.ic_action_action_search, mShowSearch);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        if (webview != null && webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }

    }

    private void initSearchView(final MaterialSearchView searchView, String[] suggestions) {
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setHint(mContext.getString(R.string.url_search_hint));
        if(ValidateUtils.checkNotNULL(suggestions)) {
            searchView.setSuggestions(suggestions);
        }
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!RegexUtils.isURL(query)) {
                    query = "http://" + query;
                }
                searchView.setQuery(query, false);
                mLink = query;
                ToastUtil.showShort(mContext, query);
                if(ValidateUtils.checkNotNULL(webview)) {
                    webview.loadUrl(mLink);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    search_view.setQuery(searchWrd, false);
                }
            }

            return;
        }
        TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (search_view.isSearchOpen()) {
            search_view.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initData() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void initView() {
        super.initView();
        initWebview();
        initSearchView(search_view, null);
    }

    private void initWebview() {

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                showWaitProgress();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideWaitProgress();
            }

        });

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setTitleText(s);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            // /////////////////////////////////////////////////////////
            //
            /**
             * 全屏播放配置
             */
           /* @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }*/

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        webview.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                TbsLog.d(TAG, "url: " + arg0);
                new AlertDialog.Builder(mContext)
                        .setTitle("allow to download？")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        ToastUtil.showLong(mContext,
                                                "fake message: i'll download...");
                                    }
                                })
                        .setNegativeButton("no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        ToastUtil.showLong(mContext,
                                                "fake message: refuse download...");
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        ToastUtil.showLong(mContext,
                                                "fake message: refuse download...");
                                    }
                                }).show();
            }
        });

        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        webview.loadUrl(mLink);
        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview != null && webview.canGoBack()) {
                webview.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || webview == null || intent.getData() == null)
            return;
        if(!TextUtils.isEmpty(intent.getStringExtra(KEY_EXTRA_TITLE))) {
            webview.loadUrl(intent.getStringExtra(KEY_EXTRA_TITLE));
        }
    }

    @Override
    protected void onDestroy() {
        if (webview != null)
            webview.destroy();
        super.onDestroy();
    }

}
