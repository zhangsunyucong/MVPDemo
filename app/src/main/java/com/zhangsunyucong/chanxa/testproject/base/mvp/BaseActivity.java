package com.zhangsunyucong.chanxa.testproject.base.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarys.rxdownload2.RxDownload;
import com.example.librarys.rxpermissions.RxPermissions;
import com.example.librarys.utils.FileUtils;
import com.example.librarys.utils.StatusBarUtil;
import com.example.librarys.utils.ValidateUtils;
import com.example.librarys.utils.ViewUtils;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.common.ActivitysManager;
import com.zhangsunyucong.chanxa.testproject.common.ProgressDialogUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

import static com.zhangsunyucong.chanxa.testproject.R.id.img_left;

public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity {

    public static final String INTENTTAG = "intentTag";
    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    private Unbinder mUnbinder;
    protected App mApplication;
    private boolean mIsSetupEventBus = false;
    private boolean mAjustSystemStatusBarFlag = true;
    protected RxPermissions mRxPermissions;

    private ProgressDialogUtil mProgressDialogUtil;

    protected final static String KEY_EXTRA_TITLE = "KEY_EXTRA_TITLE";

    protected TextView tv_title;
    protected ImageView btn_left;
    protected ImageView img_right;
    protected Button btn_right;

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitysManager.getInstance().addActivity(this);
        mApplication = (App) getApplication();
        mContext = this;
        getIntentData(getIntent());
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        setupActivityComponent(mApplication.getAppComponent());
//        ARouter.getInstance().inject(this);
        mRxPermissions = new RxPermissions(this);
        initSwipeBack();
        getRuntimePermission(mRxPermissions);
        findTitleBar();
        setAdapter();
        setStatusBar();
        initData();
        initTitleBar();
        initView();
        initListener();

       // ActivityManager.getInstance().
    }

    private void initSwipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeEdge(100)
                .setSwipeBackEnable(true)
                .setClosePercent(0.2f)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
    }

    protected void setNotSwipeBackEnable() {
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }

    protected void setAdapter() {}

    private void setStatusBar() {
        StatusBarUtil.setColor(this,
                mContext.getResources().getColor(R.color.colorPrimaryDark), 50);
    }

    protected void getIntentData(Intent intent) {
    }

    protected void initView() {
    }

    protected void getRuntimePermission(RxPermissions rxPermissions ) {

    }

    protected RxPermissions getRxPermissions() {
        if(mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        return mRxPermissions;
    }

    protected void initTitleBar() {
    }

    private void findTitleBar() {
        tv_title = ButterKnife.findById(this, R.id.tv_title);
        btn_left = ButterKnife.findById(this, img_left);
        img_right = ButterKnife.findById(this, R.id.img_right);
        btn_right = ButterKnife.findById(this, R.id.btn_right);
        if(ValidateUtils.checkNotNULL(btn_left)) {
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTitileLeftClick(view);
                }
            });

        }

        if(ValidateUtils.checkNotNULL(btn_right)) {
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTitileRightClick(view);
                }
            });
        }

        if(ValidateUtils.checkNotNULL(img_right)) {
            img_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTitileRightClick(view);
                }
            });
        }

    }

    protected void setDefaultFragment(int layoutId, Fragment defaultFragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(layoutId, defaultFragment);
        transaction.commit();
    }

    protected void setupEventBus() {
        mIsSetupEventBus = true;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    /**
     * 调整状态栏的背景，和白底黑字效果
     */
/*    public void ajustSystemStatusBar(int statusBarColor) {
        if(statusBarColor == 0) {
            return;
        }
        if(mAjustSystemStatusBarFlag) {
            if(StatusBarUtil.StatusBarLightMode(this) > 0) {
                StatusBarUtil.setStatusBarColor(this, statusBarColor);
                View view = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
                if(view != null) {
                    ViewGroup rootView = (ViewGroup) view;
                    rootView.setFitsSystemWindows(true);
                    rootView.setClipToPadding(true);
                }
            }
        }
    }*/

    public App getApp() {
        return mApplication;
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void setupActivityComponent(AppComponent appComponent);

    public void showWaitProgress() {

        Activity activity = ActivitysManager.getInstance().currentActivity();
        if(mProgressDialogUtil == null) {
            if(ValidateUtils.checkNotNULL(activity)) {
                mProgressDialogUtil = new ProgressDialogUtil(activity);
            }
        }

        mProgressDialogUtil.show();
    }

    protected void hideWaitProgress() {
        if(ValidateUtils.checkNotNULL(mProgressDialogUtil)) {
            mProgressDialogUtil.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
        ActivitysManager.getInstance().finishActivity(this);
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if(mIsSetupEventBus) {
            EventBus.getDefault().unregister(this);
        }
        this.mPresenter = null;
        this.mUnbinder = null;
        this.mApplication = null;
    }


    protected abstract int getLayoutId();

    protected abstract void initData();
    protected void initListener() {}

    /*
 * Activity的跳转-不带参数
 */
    public void startActivity(Class<?> cla) {
        Intent intent = new Intent();
        intent.setClass(this, cla);
        startActivity(intent);
    }

    public void startActivity(BaseActivity activity, Class<?> cla) {
        Intent intent = new Intent();
        intent.setClass(activity, cla);
        startActivity(intent);
    }

    /*
     * Activity的跳转-带参数
     */
    public void startActivity(Class<?> cla, Object obj) {
        Intent intent = new Intent();
        intent.setClass(this, cla);
        intent.putExtra(INTENTTAG, (Serializable) obj);
        startActivity(intent);
    }

    public void startActivityWithStringExtra(Class<?> cla, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(this, cla);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * 是否显示返回按钮
     * @param show  true则显示
     */
    protected void showTitleLeftView(boolean show) {
        if (btn_left != null) {
            if (show) {
                //btn_left.setText(backwardResid);
                ViewUtils.setViewVisibility(btn_left, View.VISIBLE);
            } else {
                btn_left.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示右边按钮
     * @param forwardResId  文字
     * @param show  true则显示
     */
    protected void showTitleRightTextView(int forwardResId, boolean show) {
        if (btn_right != null) {
            if (show) {
                ViewUtils.setViewVisibility(btn_right, View.VISIBLE);
                ViewUtils.setViewVisibility(img_right, View.GONE);
                btn_right.setText(forwardResId);
            } else {
                ViewUtils.setViewVisibility(btn_right, View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示提交按钮
     * @param forwardResId  文字
     * @param show  true则显示
     */
    protected void showTitleRightView(int forwardResId, boolean show) {
        if (btn_right != null) {
            ViewUtils.setViewVisibility(btn_right, View.GONE);
            if (show) {
                ViewUtils.setViewVisibility(img_right, View.VISIBLE);
                img_right.setImageResource(forwardResId);
            } else {
                ViewUtils.setViewVisibility(img_right, View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 返回按钮点击后触发
     * @param backwardView
     */
    protected void onTitileLeftClick(View backwardView) {

    }

    /**
     * 提交按钮点击后触发
     * @param forwardView
     */
    protected void onTitileRightClick(View forwardView) {

    }


    //设置标题内容
    protected void setTitleText(int titleId) {
        if(tv_title != null) {
            tv_title.setText(titleId);
        }
    }

    //设置标题内容
    protected void setTitleText(CharSequence title) {
        if(tv_title != null) {
            tv_title.setText(title);
        }
    }

    //设置标题文字颜色
    protected void setTitleTextColor(int textColor) {
        if(tv_title != null) {
            tv_title.setTextColor(textColor);

        }
    }

    protected boolean checkFileExistsByUrl(String url, Context context) {
        boolean exists = false;
        if(!TextUtils.isEmpty(url)) {
            File[] files = RxDownload.getInstance(context).getRealFiles(url);
            File file = null;
            if (files != null) {
                file = files[0];
            }
            exists = FileUtils.isFileExists(file);
        }
        return exists;
    }

    protected File getFileByUrl(String url, Context context) {
        if(!TextUtils.isEmpty(url)) {
            File[] files = RxDownload.getInstance(context).getRealFiles(url);
            File file = null;
            if (files != null) {
                file = files[0];
            }
            return file;
        }
        return null;
    }

    protected void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


}
