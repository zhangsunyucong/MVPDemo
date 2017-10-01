package com.zhangsunyucong.chanxa.testproject.ui.main.map;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.librarys.recyclerview.CommonAdapter;
import com.example.librarys.recyclerview.MultiItemTypeAdapter;
import com.example.librarys.recyclerview.base.ViewHolder;
import com.example.librarys.slidinguppanel.SlidingUpPanelLayout;
import com.example.librarys.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.example.librarys.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.zhangsunyucong.chanxa.testproject.R;
import com.example.librarys.utils.ToastUtil;
import com.example.librarys.utils.ValidateUtils;
import com.zhangsunyucong.chanxa.testproject.base.AppComponent;
import com.zhangsunyucong.chanxa.testproject.base.mvp.BaseFragment;
import com.zhangsunyucong.chanxa.testproject.event.SearEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MapFragment extends BaseFragment
        implements CompoundButton.OnCheckedChangeListener,
            LocationSource, AMapLocationListener {

    private String mTitle;

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.btn_mapchange)
    ToggleButton btn_mapchange;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout mLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private List<String> your_array_list;
    private CommonAdapter<String> mAdapter;

    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;

    public MapFragment() {}

    @Override
    protected void ComponentInject(AppComponent appComponent) {}

    @Override
    protected void initData() {
        your_array_list = Arrays.asList(
                "北京",
                "上海",
                "南宁",
                "柳州",
                "玉林",
                "深圳",
                "广州",
                "东莞",
                "香港",
                "澳门",
                "天津",
                "旧金山",
                "武汉",
                "重庆",
                "惠州",
                "梧州"

        );
    }

    public static MapFragment newInstance(String title) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString("title");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        initPanalLayout();
        mapView.onCreate(mSavedInstanceState);
        if (aMap==null)
        {
            aMap=mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
    }

    private void initPanalLayout() {
        recycler_view.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CommonAdapter<String>(mActivity,
                R.layout.layout_map_list_item, your_array_list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_city, s);
            }
        };
        recycler_view.setAdapter(mAdapter);

        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtil.showShort(mActivity, your_array_list.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();
        setupEventBus();
        btn_mapchange.setOnCheckedChangeListener(this);
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if(!ValidateUtils.checkNotNULL(locationClient)){
            locationClient = new AMapLocationClient(getActivity());
            clientOption = new AMapLocationClientOption();
            locationClient.setLocationListener(this);
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度定位
            clientOption.setOnceLocationLatest(true);//设置单次精确定位
            locationClient.setLocationOption(clientOption);
            locationClient.startLocation();
        }
    }
    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if(ValidateUtils.checkNotNULL(locationClient)){
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (ValidateUtils.checkNotNULL(mListener)
                && ValidateUtils.checkNotNULL(mListener)) {
            if (ValidateUtils.checkNotNULL(aMapLocation)
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        }
        else {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }
    }

    /**
     * 必须重写以下方法
     */
    @Override
    public void onResume(){
        super.onResume();
        //initview(savedInstanceState,view);
        mapView.onResume();
        /*if(locationClient != null) {
            locationClient.startLocation();
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null) {
            mapView.onDestroy();
        }
        if(locationClient!=null){
            locationClient.onDestroy();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearEvent event) {
        if(ValidateUtils.checkNotNULL(event)
                && !TextUtils.isEmpty(event.queryKey)) {
            ToastUtil.showShort(mActivity, event.queryKey);
        }
    }
}
