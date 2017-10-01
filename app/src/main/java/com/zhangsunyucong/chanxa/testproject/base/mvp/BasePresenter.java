package com.zhangsunyucong.chanxa.testproject.base.mvp;

import android.app.Activity;
import android.content.Context;

import com.zhangsunyucong.chanxa.testproject.App;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.common.ActivitysManager;
import com.zhangsunyucong.chanxa.testproject.common.ProgressDialogUtil;
import com.zhangsunyucong.chanxa.testproject.database.common.RSAUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jess on 16/4/28.
 */
public class BasePresenter<M extends IModel, V extends IView> {
    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected M mModel;
    @Inject
    protected V mView;

    private ProgressDialogUtil mProgressDialogUtil;

    public BasePresenter() {
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

    protected String getAESDecKey() {//App.getApp().getAESKey()
        return App.getApp().getAESDecKey();
    }

    protected static String encryptByServerPublicKey(String data) {
        try {
            return RSAUtils.encryptByServerPublicKey(data);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    protected void handlerError() {

    }

    private CompositeDisposable compositeDisposable;

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    protected Context getContext(){
        return (Context) mView;
    }

    protected void onDestroy() {
        dispose();
    }

}
