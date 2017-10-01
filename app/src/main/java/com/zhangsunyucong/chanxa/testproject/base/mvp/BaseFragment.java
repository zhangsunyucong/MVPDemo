package com.zhangsunyucong.chanxa.testproject.base.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.librarys.rxpermissions.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ValidateUtils;
import com.example.librarys.utils.ViewUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.common.ActivitysManager;
import com.zhangsunyucong.chanxa.testproject.common.ProgressDialogUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.zhangsunyucong.chanxa.testproject.R.id.img_left;

/**
 * Created by jess on 2015/12/8.
 */
public abstract class BaseFragment<P extends IPresenter> extends RxFragment {
    protected App mApplication;
    protected BaseActivity mActivity;
    protected View mRootView;
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;
    private boolean mIsSetupEventBus = false;

    protected Bundle mSavedInstanceState;

    protected RxPermissions mRxPermissions;
    private CompositeDisposable compositeDisposable;

    protected TextView tv_title;
    protected ImageView btn_left;
    protected ImageView img_right;
    protected Button btn_right;

    private ProgressDialogUtil mProgressDialogUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        getFragmentArg(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    protected View getLayoutView() {
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getApp();
        mActivity = (BaseActivity) getActivity();
        mRxPermissions = new RxPermissions(mActivity);
        getRuntimePermission(mRxPermissions);
        ComponentInject(getApp().getAppComponent());
        findTitleBar();
        setAdapter();
        initData();
        initTitleBar();
        initView();
        initListener();
    }

    protected void setAdapter() {}

    protected void getFragmentArg(Bundle bundle) {}

    protected void initTitleBar() {
    }

    protected void initListener() {
    }

    protected void setupEventBus() {
        mIsSetupEventBus = true;
        EventBus.getDefault().register(this);
    }

    protected void getRuntimePermission(RxPermissions rxPermissions ) {

    }

    protected RxPermissions getRxPermissions() {
        if(mRxPermissions == null) {
            mRxPermissions = new RxPermissions(getActivity());
        }
        return mRxPermissions;
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject(AppComponent appComponent);

    protected App getApp() {
        return App.getApp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if(mIsSetupEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void findTitleBar() {
        if(ValidateUtils.checkNotNULL(mRootView)) {
            tv_title = ButterKnife.findById(mRootView, R.id.tv_title);
            btn_left = ButterKnife.findById(mRootView, img_left);
            img_right = ButterKnife.findById(mRootView, R.id.img_right);
            btn_right = ButterKnife.findById(mRootView, R.id.btn_right);
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
    }

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



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ValidateUtils.checkNotNULL(compositeDisposable)) {
            compositeDisposable.dispose();
        }
        this.mPresenter = null;
        this.mActivity = null;
        this.mRootView = null;
        this.mUnbinder = null;
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        View view = this.getView();
        if(view != null) {
            view.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initData();
    protected void initView() {}

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    protected void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
