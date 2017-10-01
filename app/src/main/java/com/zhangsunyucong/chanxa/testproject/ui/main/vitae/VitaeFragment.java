package com.zhangsunyucong.chanxa.testproject.ui.main.vitae;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.librarys.rxdownload2.entity.DownloadStatus;
import com.example.librarys.utils.RegexUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseFragment;
import com.zhangsunyucong.chanxa.testproject.common.DownloadConstants;
import com.zhangsunyucong.chanxa.testproject.database.bean.VitaeInfo;
import com.zhangsunyucong.chanxa.testproject.event.VitaeSerchEvent;
import com.zhangsunyucong.chanxa.testproject.manager.ACache;
import com.zhangsunyucong.chanxa.testproject.manager.ACacheConstant;
import com.zhangsunyucong.chanxa.testproject.manager.DownloadManager;
import com.zhangsunyucong.chanxa.testproject.manager.PermissionManager;
import com.zhangsunyucong.chanxa.testproject.ui.main.inject.DaggerVitaeComponent;
import com.zhangsunyucong.chanxa.testproject.ui.main.inject.VitaeModule;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


public class VitaeFragment extends BaseFragment<VitaeContract.Presenter>
        implements OnPageChangeListener, OnLoadCompleteListener, VitaeContract.View {

    private String mTitle;
    @BindView(R.id.pdf_view)
    PDFView pdf_view;

    private Disposable mDownLoadDisposable;

    /**pdf*/
    Integer pageNumber = 0;

    public static final String SAMPLE_FILE = "vitae.pdf";

    public VitaeFragment() {
        // Required empty public constructor
    }

    public static VitaeFragment newInstance(String title) {
        VitaeFragment fragment = new VitaeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void ComponentInject(AppComponent appComponent) {
        DaggerVitaeComponent
                .builder()
                .vitaeModule(new VitaeModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString("title");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vitae;
    }

    @Override
    protected void initListener() {
        super.initListener();
        setupEventBus();
    }

    @Override
    protected void initData() {
        String romoteUrl = ACache.get(mActivity).getAsString(ACacheConstant.KEY_VITAE_URL);
        if(TextUtils.isEmpty(romoteUrl)) {
            mPresenter.getVitaeInfo("何允俭");
        } else {
            loadVitaeByUrl(romoteUrl);
        }

    }

    @Override
    public void getVitaeInfoFail(String msg) {
        ToastUtil.showShort(mActivity, msg);
    }

    @Override
    public void getVitaeInfoSuccess(final VitaeInfo vitaeInfo) {
        ACache.get(mActivity).put(ACacheConstant.KEY_VITAE_URL, vitaeInfo.romoteUrl);
        loadVitaeByUrl(vitaeInfo.romoteUrl);
    }

    private void loadVitaeByUrl(final String romoteUrl) {
        if(!TextUtils.isEmpty(romoteUrl)) {
            File file = DownloadManager.getInstance()
                    .getFileByUrl(mActivity, romoteUrl);
            if(ValidateUtils.checkNotNULL(file)) {
                showPDF(file);
            } else {
                getRxPermissions()
                        .request(PermissionManager.STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if(aBoolean) {
                                    mDownLoadDisposable = downFileByUrl(mActivity, romoteUrl);
                                }
                            }
                        });
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(VitaeSerchEvent event) {
        if(ValidateUtils.checkNotNULL(event)
                && !TextUtils.isEmpty(event.queryKey)) {
            String url = "";RegexUtils.isURL("http://www.baidu.com");
            if(!RegexUtils.isURL(event.queryKey)) {
                url = "http:\\" + event.queryKey;
            }

            VitaeInfo vitaeInfo = new VitaeInfo();
            vitaeInfo.romoteUrl = url;
            vitaeInfo.userId = AppUser.getAppUser().objectId;
            vitaeInfo.userName = AppUser.getAppUser().userName;
            getVitaeInfoSuccess(vitaeInfo);
        }
    }

    public Disposable downFileByUrl(final Context context, final String url) {
        File file = DownloadManager.getInstance()
                .getFileByUrl(context, url);
        if(ValidateUtils.checkNotNULL(file)) {
            return null;
        }
        return DownloadManager.getInstance()
                .downFileByUrl(
                        mActivity,
                        DownloadConstants.IMAGIE_PATH,
                        url
                )
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(DownloadStatus downloadStatus) throws Exception {
                        //下载进度
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.showShort(context, throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //下载成功
                        ToastUtil.showShort(context, "下载成功");
                        showPDF(DownloadManager.getInstance().getFileByUrl(mActivity, url));
                    }
                });

    }

    private void showPDF(File file) {
        pdf_view.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange(VitaeFragment.this)
                .enableAnnotationRendering(true)
                .onLoad(VitaeFragment.this)
                .scrollHandle(new DefaultScrollHandle(getActivity()))
                .load();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose(mDownLoadDisposable);
    }

    @Override
    public void loadComplete(int nbPages) {
        printBookmarksTree(pdf_view.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
}
