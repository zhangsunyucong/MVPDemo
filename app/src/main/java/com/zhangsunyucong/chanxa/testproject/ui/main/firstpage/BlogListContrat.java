package com.zhangsunyucong.chanxa.testproject.ui.main.firstpage;

import com.zhangsunyucong.chanxa.testproject.base.mvp.IModel;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IPresenter;
import com.zhangsunyucong.chanxa.testproject.base.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public interface BlogListContrat {

    interface View extends IView {
        void getBlogListSuccess(List<BlogCategory> blogCategoryList, List<BlogItem> list);
        void getBlogListFail(String msg);
    }

    interface Presenter extends IPresenter {
        void getBlogList(String userId, int page);
    }

    interface Model extends IModel {
        Observable<String> getBlogList(String userId, int page);
    }
}
