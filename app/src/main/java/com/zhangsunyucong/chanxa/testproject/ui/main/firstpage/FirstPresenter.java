package com.zhangsunyucong.chanxa.testproject.ui.main.firstpage;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseArrayResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public class FirstPresenter extends BasePresenter<FirstContract.Model, FirstContract.View>
 implements FirstContract.Presenter {

    @Inject
    public FirstPresenter() {}

    @Override
    public void getBloggerInfo() {

        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.getBloggerInfoFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }

        mModel.getBloggerInfo()
                .compose(RxLifeCycleUtils.<BaseArrayResult<Blogger>>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseArrayResult<Blogger>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseArrayResult<Blogger> s) {
                        mView.getBloggerInfoSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getBloggerInfoFail(e.getLocalizedMessage());
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
