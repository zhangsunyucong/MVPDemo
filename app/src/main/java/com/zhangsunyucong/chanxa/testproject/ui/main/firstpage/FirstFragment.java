package com.zhangsunyucong.chanxa.testproject.ui.main.firstpage;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.librarys.recyclerview.CommonAdapter;
import com.example.librarys.recyclerview.MultiItemTypeAdapter;
import com.example.librarys.recyclerview.base.ViewHolder;
import com.example.librarys.springview.container.DefaultHeader;
import com.example.librarys.springview.widget.SpringView;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseFragment;
import com.zhangsunyucong.chanxa.testproject.database.common.BaseArrayResult;
import com.zhangsunyucong.chanxa.testproject.ui.main.inject.DaggerFirstComponent;
import com.zhangsunyucong.chanxa.testproject.ui.main.inject.FirstModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FirstFragment extends BaseFragment<FirstContract.Presenter>
 implements FirstContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.springview)
    SpringView springview;

    private CommonAdapter<Blogger> mAdapter;
    private List<Blogger> mDatas = new ArrayList<>();

    private String mTitle;

    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance(String title) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void ComponentInject(AppComponent appComponent) {
        DaggerFirstComponent
                .builder()
                .firstModule(new FirstModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initData() {
        mPresenter.getBloggerInfo();
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CommonAdapter<Blogger>(mActivity,
                R.layout.layout_rv_bloger, mDatas) {
            @Override
            protected void convert(ViewHolder holder, Blogger blogger, int position) {
                    holder.setText(R.id.tv_title, blogger.cnName + "  " + blogger.blogType);
                    holder.setText(R.id.tv_content, blogger.motto);
            }
        };
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BlogListActivity.startBlogListActivity(mActivity, mDatas.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        initSpringView();
    }

    private void initSpringView() {
        springview.setHeader(new DefaultHeader(mActivity));
        //springview.setFooter(new DefaultFooter(mActivity));
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBloggerInfo();
            }

            @Override
            public void onLoadmore() {
            }
        });
    }

    @Override
    public void getBloggerInfoFail(String msg) {
        if(ValidateUtils.checkNotNULL(springview)) {
            springview.onFinishFreshAndLoad();
        }
        ToastUtil.showShort(mActivity, msg);
    }

    @Override
    public void getBloggerInfoSuccess(BaseArrayResult<Blogger> result) {
        if(ValidateUtils.checkNotNULL(springview)) {
            springview.onFinishFreshAndLoad();
        }
        mDatas.clear();
        mDatas.addAll(result.data);
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString("title");
        }
    }


}
