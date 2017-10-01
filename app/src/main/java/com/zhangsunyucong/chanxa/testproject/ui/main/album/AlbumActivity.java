package com.zhangsunyucong.chanxa.testproject.ui.main.album;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.librarys.photopicker.PhotoPicker;
import com.example.librarys.recyclerview.CommonAdapter;
import com.example.librarys.recyclerview.MultiItemTypeAdapter;
import com.example.librarys.recyclerview.base.ViewHolder;
import com.example.librarys.rxpermissions.RxPermissions;
import com.example.librarys.rxupload.model.entities.UploadEntities;
import com.example.librarys.rxupload.view.UploadView;
import com.example.librarys.utils.FileUtils;
import com.example.librarys.utils.ImageUtils;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.database.upload.UploadPresenterImpl;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;
import com.zhangsunyucong.chanxa.testproject.manager.PhotoPickerConstants;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.inject.AlbumModule;
import com.zhangsunyucong.chanxa.testproject.ui.main.album.inject.DaggerAlbumConpenent;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;
import com.zhangsunyucong.chanxa.testproject.widget.recycleview.CardScaleHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import shortbread.Shortcut;

/**
 * Created by zhangsunyucong on 2017/9/17.
 */

@Shortcut(id = "albums", icon = R.mipmap.category_selected, shortLabel = "albums")
@Route(path = ARouterConstant.PATH_ACTIVITY_ALBUM)
public class AlbumActivity extends BaseActivity<AlbumConstract.Presenter> implements AlbumConstract.View {

    final int MAX = 1;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.blurView)
    ImageView mBlurView;

    private CommonAdapter<String> mAdapter;

    private List<String> mList = new ArrayList<>();
    private CardScaleHelper mCardScaleHelper = null;
    private Runnable mBlurRunnable;
    private int mLastPos = -1;

    private  UploadPresenterImpl mUploadPresenterImpl;

    public static void startActivity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_ALBUM)
                .navigation();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAlbumConpenent
                .builder()
                .appComponent(appComponent)
                .albumModule(new AlbumModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_album;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();

        setTitleText("照片墙");
        showTitleRightTextView(R.string.add, true);
    }

    @Override
    protected void onTitileRightClick(View forwardView) {
        super.onTitileRightClick(forwardView);
        PhotoPicker.builder()
                .setPhotoCount(MAX)
                .setShowCamera(true)
                .setPreviewEnabled(false)
                .setSelected(selectedPhotos)
                .start(AlbumActivity.this, PhotoPickerConstants.REQUEST_CODE_ME);
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void setAdapter() {
        super.setAdapter();
        initRecyclerView();
    }

    @Override
    public void deletePhotoFail(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    @Override
    public void deletePhotoSuccess(AlbumEntity albumEntity, int position) {
        if(position >= 0 && position < mList.size()) {
            mList.remove(position);
            mAdapter.setDatas(mList);
            mAdapter.notifyItemRemoved(position);
            notifyBackgroundChange();
        }
    }

    @Override
    public void getAlbumInfoFail(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    @Override
    public void getAlbumIInfoSuccess(AlbumEntity albumEntitys) {

        if(ValidateUtils.checkNotNULL(albumEntitys)) {
            List<String> photoList = albumEntitys.photoDetails;
            if(ValidateUtils.checkNotNULL(photoList) && !photoList.isEmpty()) {
                mList.addAll(photoList);
                mAdapter.setDatas(mList);
                mAdapter.notifyDataSetChanged();
            }

        }

    }

    @Override
    public void addAlbumInfoFail(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    @Override
    public void addAlbumISuccess(AlbumEntity albumEntity) {
        if(!TextUtils.isEmpty(albumEntity.newTempUrl)) {
            mList.add(albumEntity.newTempUrl);
            mAdapter.setDatas(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void getRuntimePermission(RxPermissions rxPermissions) {
        super.getRuntimePermission(rxPermissions);
        if(ValidateUtils.checkNotNULL(rxPermissions)) {
            rxPermissions
                    .request(Manifest.permission.CAMERA)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if(aBoolean) {

                            }
                        }
                    });
        }
    }

    @Override
    protected void initData() {

        mList.add("http://ac-5ubk617r.clouddn.com/e7e569786f63e2373dc0.jpg");
        mList.add("http://ac-5ubk617r.clouddn.com/4a634e0d62d9cffad20b.jpg");
        mList.add("http://ac-5ubk617r.clouddn.com/343de8c9ae4ad5e11526.jpg");
        mPresenter.getAlbumInfo(AppUser.getAppUser().getuserLoginResult().objectId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mUploadPresenterImpl =
                new UploadPresenterImpl(new UploadView() {
                    @Override
                    public void uploadStart() {
                        showWaitProgress();
                    }

                    @Override
                    public void uploading(long bytesWritten, long contentLength) {

                    }

                    @Override
                    public void uploadSuccess(UploadEntities entities) {
                        hideWaitProgress();
                        if(ValidateUtils.checkNotNULL(entities)) {
                            AlbumEntity albumEntity = new AlbumEntity();
                            albumEntity.newTempUrl = entities.url;
                            mPresenter.addAlbum(
                                    AppUser.getAppUser().getuserLoginResult().objectId,
                                    albumEntity
                            );
                        }

                    }

                    @Override
                    public void uploadFail(String error) {
                        hideWaitProgress();
                    }

                    @Override
                    public void uploadComplete() {

                    }
                });
    }

    private void initRecyclerView() {

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new CommonAdapter<String>(mContext, R.layout.view_card_item, mList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                final ImageView imageView = holder.getView(R.id.imageView);

                if(ValidateUtils.checkNotNULL(imageView) && ValidateUtils.checkNotNULL(mContext)) {
                    Glide.with(App.getApp())
                            .load(mList.get(position))
                            .into(imageView);
                }

            }
        };

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtil.showShort(mContext, position+"");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position >= 0 && position < 3) {
                    ToastUtil.showShort(mContext, "前三张是默认演示图片，不能删除");
                    return true;
                }
                AlbumEntity albumEntity = new AlbumEntity();
                albumEntity.newTempUrl = mAdapter.getDatas().get(position);
                mPresenter.deletePhoto(
                        AppUser.getAppUser().getuserLoginResult().objectId,
                        albumEntity,
                        position
                        );

                return true;
            }
        });

        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        if(!mList.isEmpty()) {
            mCardScaleHelper.setCurrentItemPos(0);
        }
        mCardScaleHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    notifyBackgroundChange();
                }
            }
        });

        notifyBackgroundChange();
    }

    private void notifyBackgroundChange() {
        if (mLastPos == mCardScaleHelper.getCurrentItemPos()) return;
        mLastPos = mCardScaleHelper.getCurrentItemPos();
        if(mLastPos >= 0 && mLastPos < mList.size() && ValidateUtils.checkNotNULL(mContext)) {
            Glide.with(App.getApp())
                    .load(mList.get(mLastPos))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(final Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            if(ValidateUtils.checkNotNULL(mContext) && ValidateUtils.checkNotNULL(mBlurView)) {
                                mBlurView.setImageBitmap(ImageUtils.fastBlur(mContext, bitmap, 0.5f, 2));
                            }

                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == PhotoPickerConstants.REQUEST_CODE_ME) {
                List<String> photos = null;
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if(ValidateUtils.checkNotNULL(photos) && photos.size() > 0) {
                        File file = FileUtils.getFileByPath(photos.get(0));
                        if(file.exists()) {
                            mUploadPresenterImpl.uploadFile(file, bindUntilEvent(ActivityEvent.DESTROY));
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
