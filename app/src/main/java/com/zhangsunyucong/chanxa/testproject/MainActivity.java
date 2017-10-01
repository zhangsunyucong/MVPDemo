package com.zhangsunyucong.chanxa.testproject;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.example.librarys.materialsearchview.MaterialSearchView;
import com.example.librarys.photopicker.PhotoPicker;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseActivity;
import com.zhangsunyucong.chanxa.testproject.event.MePhotoEvent;
import com.zhangsunyucong.chanxa.testproject.event.SearEvent;
import com.zhangsunyucong.chanxa.testproject.manager.PhotoPickerConstants;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.FirstFragment;
import com.zhangsunyucong.chanxa.testproject.ui.main.map.MapFragment;
import com.zhangsunyucong.chanxa.testproject.ui.main.me.MeFragment;
import com.zhangsunyucong.chanxa.testproject.ui.main.music.MusicFragment;
import com.zhangsunyucong.chanxa.testproject.ui.user.AppUser;
import com.zhangsunyucong.chanxa.testproject.ui.user.login.LoginActivity;
import com.zhangsunyucong.chanxa.testproject.widget.alphatab.AlphaTabsIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @BindView(R.id.search_view)
    MaterialSearchView search_view;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    public static void startMainActivity(BaseActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        setNotSwipeBackEnable();
       // setupEventBus();
        setTitleText("首 页");
        showTitleLeftView(false);
        showTitleRightView(R.drawable.ic_action_action_search, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!AppUser.getAppUser().isLogin(MainActivity.this)) {
            LoginActivity.startLoginActivity(MainActivity.this, true);
            finish();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        initSearchView();
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mainAdapter);
        viewpager.addOnPageChangeListener(mainAdapter);
        viewpager.setOffscreenPageLimit(3);
        alphaIndicator.setViewPager(viewpager);
    }

    @Override
    protected void onTitileRightClick(View forwardView) {
        super.onTitileRightClick(forwardView);
        search_view.showSearch();
    }

    private void initSearchView() {
        search_view.setVoiceSearch(false);
        search_view.setCursorDrawable(R.drawable.custom_cursor);
        search_view.setEllipsize(true);
        search_view.setHint(mContext.getString(R.string.map_search_hint));
        search_view.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        search_view.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EventBus.getDefault().post(new SearEvent(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        search_view.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
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
        if(resultCode == RESULT_OK) {
            if (requestCode == MaterialSearchView.REQUEST_VOICE ) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (matches != null && matches.size() > 0) {
                    String searchWrd = matches.get(0);
                    if (!TextUtils.isEmpty(searchWrd)) {
                        search_view.setQuery(searchWrd, false);
                    }
                }

            } else if(requestCode == PhotoPickerConstants.REQUEST_CODE_ME) {
                List<String> photos = null;
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    EventBus.getDefault().post(new MePhotoEvent(photos));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (search_view.isSearchOpen()) {
            search_view.closeSearch();
        } else {
            startHomeLaucher();
        }
    }

    private void startHomeLaucher() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
        private int[] titleIds = {
                R.string.main_tab_first_page,
                R.string.main_tab_music,
                R.string.main_tab_map,
                R.string.main_tab_me
        };
        private List<String> titles = new ArrayList<>();

        public MainAdapter(FragmentManager fm) {
            super(fm);
            for(int i = 0; i < titleIds.length; i++) {
                titles.add(mContext.getString(titleIds[i]));
            }
            fragments.add(FirstFragment.newInstance(titles.get(0)));
            fragments.add(MusicFragment.newInstance(titles.get(1)));
            fragments.add(MapFragment.newInstance(titles.get(2)));
            fragments.add(MeFragment.newInstance(titles.get(3)));
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setTitleText(titles.get(position));
            showTitleRightView(R.drawable.ic_action_action_search, false);
            if (0 == position) {
            } else if (2 == position) {
                showTitleRightView(R.drawable.ic_action_action_search, true);
            } else if (3 == position) {
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
