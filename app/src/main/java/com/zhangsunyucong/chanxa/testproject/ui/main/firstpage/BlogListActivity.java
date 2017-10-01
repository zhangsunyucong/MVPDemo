package com.zhangsunyucong.chanxa.testproject.ui.main.firstpage;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.librarys.recyclerview.CommonAdapter;
import com.example.librarys.recyclerview.MultiItemTypeAdapter;
import com.example.librarys.recyclerview.base.ViewHolder;
import com.example.librarys.springview.container.DefaultFooter;
import com.example.librarys.springview.container.DefaultHeader;
import com.example.librarys.springview.widget.SpringView;
import com.zhangsunyucong.chanxa.testproject.R;
import com.zhangsunyucong.chanxa.testproject.TbsWebviewActivity;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.ui.main.inject.BlogListModule;
import com.zhangsunyucong.chanxa.testproject.ui.main.inject.DaggerBlogListComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BlogListActivity extends BaseActivity<BlogListContrat.Presenter>
        implements BlogListContrat.View {

    private String mUserId;
    private int mPage = 1;
    private static final String KEY_EXTRA_USER_ID = "userId";
    private Blogger mBlogger;

    private CommonAdapter<BlogItem> mAdapter;
    private List<BlogItem> mDatas = new ArrayList<>();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.springview)
    SpringView springview;

    public static void startBlogListActivity(BaseActivity activity, Blogger blogger) {
        if(ValidateUtils.checkNotNULL(activity)) {
            Intent intent = new Intent(activity, BlogListActivity.class);
            intent.putExtra(KEY_EXTRA_USER_ID, blogger);
            activity.startActivity(intent);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBlogListComponent
                .builder()
                .blogListModule(new BlogListModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    protected void initData() {
        if(ValidateUtils.checkNotNULL(mBlogger)) {
            showWaitProgress();
            mPresenter.getBlogList(mBlogger.enName, mPage);
        }
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void getIntentData(Intent intent) {
        super.getIntentData(intent);
        if(ValidateUtils.checkNotNULL(intent)) {
            mBlogger = intent.getParcelableExtra(KEY_EXTRA_USER_ID);
        }
    }

    @Override
    public void getBlogListSuccess(List<BlogCategory> blogCategoryList, List<BlogItem> list) {
        hideWaitProgress();
        if(ValidateUtils.checkNotNULL(springview)) {
            springview.onFinishFreshAndLoad();
        }
        if(ValidateUtils.checkNotNULL(list) && list.size() <= 0) {
            ToastUtil.showShort(mContext, "没有更多了~");
            return;
        }
        if(mPage == 1) {
            mDatas.clear();
        }
        mDatas.addAll(list);
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        if(ValidateUtils.checkNotNULL(mBlogger)) {
            setTitleText(mBlogger.cnName);
        }

    }

    @Override
    protected void initView() {
        super.initView();
        initSpringView();
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommonAdapter<BlogItem>(mContext,
                R.layout.layout_blog_list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, BlogItem blogItem, int position) {
                holder.setText(R.id.tv_title, blogItem.title);
                holder.setText(R.id.tv_content, blogItem.content);
                holder.setText(R.id.tv_date, blogItem.date);
            }
        };
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position >= 0 && position < mDatas.size()) {
                    TbsWebviewActivity
                            .startActivity(BlogListActivity.this,
                                    mDatas.get(position).title,
                                    mDatas.get(position).link);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initSpringView() {
        springview.setHeader(new DefaultHeader(this));
        springview.setFooter(new DefaultFooter(this));
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.getBlogList(mBlogger.enName, mPage);
            }

            @Override
            public void onLoadmore() {
                mPage = mPage + 1;
                mPresenter.getBlogList(mBlogger.enName, mPage);
            }
        });
    }

    @Override
    public void getBlogListFail(String msg) {
        hideWaitProgress();
        if(ValidateUtils.checkNotNULL(springview)) {
            springview.onFinishFreshAndLoad();
        }
        ToastUtil.showShort(mContext, msg);
    }
}
