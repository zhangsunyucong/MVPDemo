package com.zhangsunyucong.chanxa.testproject.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseLazyLoadFragment<P extends IPresenter> extends BaseFragment{
	
	private int mBaseLayoutId;
	private boolean mIsVisible;
	private boolean mIsViewCreate;
	
	public BaseLazyLoadFragment(int layoutId){
		this.mBaseLayoutId = layoutId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mIsViewCreate = true;
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(mIsVisible){
			onFragmentFirstVisible();
		}
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(getUserVisibleHint()){
			mIsVisible = true;
			if(mIsViewCreate){
				onFragmentFirstVisible();
			}
		}else{
			mIsViewCreate = false;
			mIsVisible = false;
		}
	}
	
	protected void onFragmentFirstVisible(){
		initView();
	}
	
	abstract protected void initView();
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mIsViewCreate = false;
	}
}
