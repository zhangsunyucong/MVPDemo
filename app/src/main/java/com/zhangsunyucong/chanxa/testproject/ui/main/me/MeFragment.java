package com.zhangsunyucong.chanxa.testproject.ui.main.me;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.librarys.RippleView;
import com.example.librarys.anim.CircularAnim;
import com.example.librarys.utils.FileUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.TbsWebviewActivity;
import com.zhangsunyucong.chanxa.testproject.Utils.RxLifeCycleUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseFragment;
import com.zhangsunyucong.chanxa.testproject.common.DownloadConstants;
import com.zhangsunyucong.chanxa.testproject.dialog.AlertDialogUtils;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.AlbumActivity;
import com.zhangsunyucong.chanxa.testproject.ui.main.vitae.ViateActivity;
import com.zhangsunyucong.chanxa.testproject.ui.setting.AboutActivity;
import com.zhangsunyucong.chanxa.testproject.ui.setting.feedback.FeedbackActivity;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MeFragment extends BaseFragment {

    @BindView(R.id.rip_feedback)
    RippleView rip_feedback;
    @BindView(R.id.rip_about)
    RippleView rip_about;
    @BindView(R.id.rip_logout)
    RippleView rip_logout;
    @BindView(R.id.rip_photo)
    RippleView rip_photo;
    @BindView(R.id.rip_browser)
    RippleView rip_browser;
    @BindView(R.id.rip_clear_cache)
    RippleView rip_clear_cache;
    @BindView(R.id.rip_vitae)
    RippleView rip_vitae;


    private String mTitle;

    public MeFragment() {
    }

    public static MeFragment newInstance(String title) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void ComponentInject(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString("title");
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        super.initListener();
        rip_vitae.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                CircularAnim.fullActivity(mActivity, rippleView)
                        .colorOrImageRes(R.color.activity_anim)  //注释掉，因为该颜色已经在App.class 里配置为默认色
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                ViateActivity.startActivity();
                            }
                        });
            }
        });
        rip_browser.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                TbsWebviewActivity.startActivity(mActivity,
                        true, mActivity.getString(R.string.app_name),
                        "https://heyunjian.leanapp.cn");
            }
        });
        rip_photo.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlbumActivity.startActivity();
            }
        });
        rip_clear_cache.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                showClearCache();
            }
        });
        rip_feedback.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FeedbackActivity.startActicity();
            }
        });
        rip_about.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AboutActivity.startActivity();
            }
        });
        rip_logout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                logout();
            }
        });
    }


    /**
     * 退出登录
     */
    private void logout() {
        if(!ValidateUtils.checkNotNULL(mActivity)) {
            return;
        }
        AlertDialogUtils.show(mActivity, "确定退出登录？",
                new AlertDialogUtils.OnPositiveClickListener() {
                    @Override
                    public void onPositiveClick(DialogInterface dialogInterface, int i) {
                        AppUser.getAppUser().clearUser(mActivity);
                        LoginActivity.startLoginActivity(mActivity, true);
                        if(ValidateUtils.checkNotNULL(mActivity)) {
                            mActivity.finish();
                        }
                    }
                });
    }

    /**
     * 弹框让用户选择是否清除数据
     */
    private void showClearCache() {
        if(ValidateUtils.checkNotNULL(mActivity)) {
            AlertDialogUtils.show(mActivity, "确定删除所有数据？",
                    new AlertDialogUtils.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick(DialogInterface dialogInterface, int i) {
                           clearCache();
                        }
                    });

        }

    }

    private void clearCache() {
        Observable.just(DownloadConstants.DELETE_ALL_FILE_PATH)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String s) throws Exception {
                        return FileUtils.deleteDir(s);
                    }
                })
                .compose(RxLifeCycleUtils.<Boolean>getErrAndIOSchedulerTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if(s) {
                            ToastUtil.showShort(mActivity, "删除成功");
                        } else {
                            ToastUtil.showShort(mActivity, "删除失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(mActivity, "删除异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
