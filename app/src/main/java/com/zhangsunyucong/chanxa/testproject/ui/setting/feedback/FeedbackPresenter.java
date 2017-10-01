package com.zhangsunyucong.chanxa.testproject.ui.setting.feedback;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.database.common.ErrorCodeUtils;
import com.zhangsunyucong.chanxa.testproject.database.common.StringResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/17 0017.
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContract.Model, FeedbackContract.View>
    implements FeedbackContract.Presenter {

    @Inject
    public FeedbackPresenter() {}

    @Override
    public void feedback(String clientType, String content, String userPhoneNum) {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.feedbackFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.feedback(clientType, content, userPhoneNum)
                .compose(RxLifeCycleUtils.<StringResult>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StringResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        showWaitProgress();
                    }

                    @Override
                    public void onNext(StringResult result) {
                        hideWaitProgress();
                        if(result.status == ErrorCodeUtils.SUCCUSS) {
                            mView.feedbackSuccess();
                        } else {
                            mView.feedbackFail(result.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitProgress();
                        mView.feedbackFail(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
