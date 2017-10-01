package com.zhangsunyucong.chanxa.testproject.ui.main.firstpage;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.Utils.LogRecord;
import com.example.librarys.utils.NetworkUtils;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BasePresenter;
import com.zhangsunyucong.chanxa.testproject.jsoup.Config;
import com.zhangsunyucong.chanxa.testproject.jsoup.JsoupUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public class BlogListPresenter extends BasePresenter<BlogListContrat.Model, BlogListContrat.View>
    implements BlogListContrat.Presenter {

    private List<BlogCategory> mBlogCategoryList;

    @Inject
    public BlogListPresenter() {}

    @Override
    public void getBlogList(String userId, int page) {
        if(!NetworkUtils.isAvailableByPing(App.getApp())) {
            mView.getBlogListFail(App.getApp().getString(R.string.network_not_connect));
            return;
        }
        mModel.getBlogList(userId, page)
                .compose(RxLifeCycleUtils.<String>bindToLifecycle(mView))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String s) {
                        hideWaitProgress();
                        mBlogCategoryList = new ArrayList<>();
                        List<BlogItem> list = JsoupUtils
                                .getBlogList(Config.BLOG_CATEGORY_ALL,
                                        s, mBlogCategoryList);
                        mView.getBlogListSuccess(mBlogCategoryList, list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitProgress();
                        LogRecord.d(e.getMessage());
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
