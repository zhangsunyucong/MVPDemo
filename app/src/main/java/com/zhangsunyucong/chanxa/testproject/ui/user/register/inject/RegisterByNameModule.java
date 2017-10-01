package com.zhangsunyucong.chanxa.testproject.ui.user.register.inject;

import com.zhangsunyucong.chanxa.testproject.base.scope.ActivityScope;
import com.zhangsunyucong.chanxa.testproject.ui.user.UserModel;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterByNameContract;
import com.zhangsunyucong.chanxa.testproject.ui.user.register.RegisterByNamePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hyj on 2017/8/9 0009.
 */

@Module
public class RegisterByNameModule {

    private RegisterByNameContract.View mView;

    public RegisterByNameModule(RegisterByNameContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    public RegisterByNameContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    public RegisterByNameContract.Presenter providePresenter(RegisterByNamePresenter presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public RegisterByNameContract.Model provideModel(UserModel userModel) {
        return userModel;
    }

}
