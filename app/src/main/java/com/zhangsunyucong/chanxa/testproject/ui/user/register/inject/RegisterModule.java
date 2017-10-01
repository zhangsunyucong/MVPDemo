package com.zhangsunyucong.chanxa.testproject.ui.user.register.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.UserModel;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/8 0008.
 */

@Module
public class RegisterModule {

    private RegisterContract.View mView;

    public RegisterModule(RegisterContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    RegisterContract.Model provideModel(UserModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RegisterContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    RegisterContract.Presenter providePrensenter(RegisterPresenter presenter) {
        return presenter;
    }

}
