package com.zhangsunyucong.chanxa.testproject.ui.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.librarys.RippleView;
import com.example.librarys.rxdownload2.entity.DownloadStatus;
import com.example.librarys.utils.AppUtils;
import com.example.librarys.utils.ClipboardUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.Utils.LogRecord;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.common.DownloadConstants;
import com.zhangsunyucong.chanxa.testproject.manager.ACache;
import com.zhangsunyucong.chanxa.testproject.manager.ACacheConstant;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;
import com.zhangsunyucong.chanxa.testproject.manager.DownloadManager;
import com.zhangsunyucong.chanxa.testproject.ui.setting.inject.AboutModule;
import com.zhangsunyucong.chanxa.testproject.ui.setting.inject.DaggerAboutComponent;

import java.io.File;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@Route(path = ARouterConstant.PATH_ACTIVITY_ABOUT)
public class AboutActivity extends BaseActivity<AboutContract.Presenter>
        implements AboutContract.View {

    @BindView(R.id.tv_verson_info)
    TextView tv_verson_info;

    @BindView(R.id.tv_share)
    TextView tv_share;

    @BindView(R.id.rip_share)
    RippleView rip_share;
    @BindView(R.id.rip_update)
    RippleView rip_update;

    private boolean mIsDownloadApp = false;

    private Disposable mDownLoadDisposable;

    private int mRomoteVersionCode;
    private String mRomoteDownloadUrl;

    public static void startActivity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_ABOUT)
                .navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAboutComponent.builder()
                .aboutModule(new AboutModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        ACache.get(mContext).put(ACacheConstant.KEY_APP_DOWNLOAD_URL, "");
    }

    private void getAcacheData() {
        Object object = ACache.get(mContext)
                .getAsObject(ACacheConstant.KEY_VERSION_CODE);
        if(ValidateUtils.checkNotNULL(object)) {
            mRomoteVersionCode = (int)object;
        }
        mRomoteDownloadUrl = ACache.get(mContext)
                .getAsString(ACacheConstant.KEY_APP_DOWNLOAD_URL);
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleText(R.string.about);
        showTitleLeftView(true);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_verson_info.setText(
                mContext.getString(R.string.app_name_and_version)
                        + " "
                        +AppUtils.getAppVersionName(mContext));
    }

    @Override
    public void getUpdateInfoFail(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    @Override
    public void getUpdateInfoSuccess(UpdateEntity updateEntity) {
        if(ValidateUtils.checkNotNULL(updateEntity)) {
            ACache.get(mContext)
                    .put(ACacheConstant.KEY_VERSION_CODE,
                            updateEntity.versionCode);
            ACache.get(mContext)
                    .put(ACacheConstant.KEY_APP_DOWNLOAD_URL,
                            updateEntity.downloadUrl);
        } else {
            return;
        }
        //是检查更新
        if(mIsDownloadApp) {
            downLoadAppByUrl(updateEntity.versionCode, updateEntity.downloadUrl);
        }
        //是分享
        else {
            shareDownLoadUrl(updateEntity.downloadUrl);

        }
    }

    private void shareDownLoadUrl(String downloadUrl) {
        if(!TextUtils.isEmpty(downloadUrl)) {
            ClipboardUtils.copyText(this, downloadUrl);
            ToastUtil.showShort(this, R.string.clipboard_download_url_content_copy);
        }else {
            ToastUtil.showShort(this, R.string.clipboard_content_copy_empty);
        }
    }

    private void downLoadAppByUrl(int versionCode, String downloadUrl) {
        if(!TextUtils.isEmpty(downloadUrl)) {
            if(hasNewVersion(versionCode)) {

                File file = DownloadManager.getInstance().getFileByUrl(mContext, downloadUrl);

                if(ValidateUtils.checkNotNULL(file)) {
                    //新的版本已经下载没有安装，开启安装页面
                    return;
                }
                mDownLoadDisposable = DownloadManager.getInstance()
                        .downFileByUrl(
                                mContext,
                                DownloadConstants.APK_PATH,
                                downloadUrl
                        )
                        .subscribe(new Consumer<DownloadStatus>() {
                            @Override
                            public void accept(DownloadStatus downloadStatus) throws Exception {
                                //下载进度
                                LogRecord.d(downloadStatus.getPercent()+"");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                ToastUtil.showShort(mContext, "下载失败" + throwable.getMessage());
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                ToastUtil.showShort(mContext, "下载新版本成功");
                                //开启安装页面
                            }
                        });
            } else {
                ToastUtil.showShort(mContext, "已经是最新版本了");
            }
        }
    }

    private boolean hasNewVersion(int versionCode) {
        return (AppUtils.getAppVersionCode(mContext) < versionCode);
    }

    @Override
    protected void initListener() {
        super.initListener();
        rip_share.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mIsDownloadApp = false;
                getAcacheData();
                if(TextUtils.isEmpty(mRomoteDownloadUrl)) {
                    mPresenter.getUpdateInfo();
                } else {
                    shareDownLoadUrl(mRomoteDownloadUrl);
                }
            }
        });
        rip_update.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mIsDownloadApp = true;
                getAcacheData();
                if(TextUtils.isEmpty(mRomoteDownloadUrl)) {
                    mPresenter.getUpdateInfo();
                } else {
                    downLoadAppByUrl(mRomoteVersionCode, mRomoteDownloadUrl);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose(mDownLoadDisposable);
    }
}
