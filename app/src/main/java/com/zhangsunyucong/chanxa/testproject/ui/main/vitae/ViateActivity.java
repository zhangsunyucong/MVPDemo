package com.zhangsunyucong.chanxa.testproject.ui.main.vitae;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.librarys.materialsearchview.MaterialSearchView;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.event.VitaeSerchEvent;
import com.zhangsunyucong.chanxa.testproject.manager.ARouterConstant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

@Route(path = ARouterConstant.PATH_ACTIVITY_VITAE)
public class ViateActivity extends BaseActivity {

    @BindView(R.id.search_view)
    MaterialSearchView search_view;

    public static void startActivity() {
        ARouter.getInstance()
                .build(ARouterConstant.PATH_ACTIVITY_VITAE)
                .navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_viate;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleText("简历");
    }

    @Override
    protected void onTitileLeftClick(View backwardView) {
        super.onTitileLeftClick(backwardView);
        finish();
    }

    @Override
    protected void onTitileRightClick(View forwardView) {
        super.onTitileRightClick(forwardView);
        search_view.showSearch();
    }

    @Override
    protected void initView() {
        super.initView();
        showTitleRightView(R.drawable.ic_action_action_search, true);
        initSearchView(search_view, null);
        setDefaultFragment(R.id.frame_layout,
                VitaeFragment.newInstance("简历"));
    }

    private void initSearchView(MaterialSearchView searchView, String[] suggestions) {
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setHint(mContext.getString(R.string.vitae_search_hint));
        if(ValidateUtils.checkNotNULL(suggestions)) {
            searchView.setSuggestions(suggestions);
        }
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EventBus.getDefault().post(new VitaeSerchEvent(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    search_view.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (search_view.isSearchOpen()) {
            search_view.closeSearch();
        } else {
           super.onBackPressed();
        }
    }
}
