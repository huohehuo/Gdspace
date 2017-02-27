package com.example.lins.gdspace;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;
import com.example.lins.gdspace.base.App;
import com.example.lins.gdspace.bean.MainBean;
import com.example.lins.gdspace.databinding.ActivityMainBinding;
import com.example.lins.gdspace.service.MySubscribe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AMapLocationListener,
        LocationSource, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener,
        AMap.OnMarkerDragListener, AMap.OnMapLoadedListener, AMap.InfoWindowAdapter {
    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
    private ActivityMainBinding binding;
    private AMap aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    LocationSource.OnLocationChangedListener mListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    boolean chek = false;
    //添加Marker
    private MarkerOptions markerOption;
    private LatLng latlng = new LatLng(39.761, 116.434);
    private Marker marker;
    private Marker marker2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        binding.mapGd.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = binding.mapGd.getMap();
            setUpMap();
            //addMarker();// 往地图上添加marker
            setMarker();
        }

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addMarker();
                //addMarkersToMap();
                setMarker();
            }
        });
    }

//    /**
//    * 在地图上添加marker
//    */
//    private void addMarker() {
//        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
//                .fromResource(R.drawable.here))
//                .position(latlng)
//                .title("标题")
//                .snippet("详细信息")
//                .draggable(true);
//        marker = aMap.addMarker(markerOption);
//        marker.showInfoWindow();
//    }

    private void setMarker(){
        App.getService().getShow(new MySubscribe<MainBean>() {
            @Override
            public void onNext(MainBean mainBean) {
                super.onNext(mainBean);

                addDataMarker(mainBean);
            }
        });
    }
    /**
     * 在地图上添加marker
     */
    private void addMarker() {
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(CHENGDU).title("成都市")
                .snippet("成都市:30.679879, 104.064855").draggable(true));

//        markerOption = new MarkerOptions();
//        markerOption.position(XIAN);
//        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
//        markerOption.draggable(true);
//        markerOption.icon(BitmapDescriptorFactory
//                .fromResource(R.drawable.here));
//        marker2 = aMap.addMarker(markerOption);
//        marker2.showInfoWindow();
        //drawMarkers();// 添加10个带有系统默认icon的marker
    }
    private void addDataMarker(MainBean data){
        for (int i = 0;i<data.getBody().getDatalist().size();i++){
        Toast.makeText(MainActivity.this, "get:"+data.getBody().getDatalist().get(i).getLat(), Toast.LENGTH_SHORT).show();
            double dlon = Double.valueOf(data.getBody().getDatalist().get(i).getLon());
            double dlat = Double.valueOf(data.getBody().getDatalist().get(i).getLat());
            Marker marker =aMap.addMarker(new MarkerOptions().anchor(0.5f,0.5f)
            .position(new LatLng(dlon,dlat))
                    .title(data.getBody().getDatalist().get(i).getTitle())
                    .snippet(data.getBody().getDatalist().get(i).getWorld())
                    .draggable(true));
            marker.showInfoWindow();
        }

    }

    private void setUpMap() {
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        //addMarker();// 往地图上添加marker
    }

    /**
     * 在地图上添加marker
     */
    private void drawMarkers() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.here))
                .position(latlng)
                .title("测试")
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {

        if (!chek){
                marker.showInfoWindow();
                Toast.makeText(this, "你点击了：" + marker.getTitle(), Toast.LENGTH_SHORT).show();
                chek = true;
        }else{
            marker.hideInfoWindow();
            Toast.makeText(this, "隐藏", Toast.LENGTH_SHORT).show();
            chek = false;
        }
    }
    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(XIAN).include(CHENGDU)
//                .include(latlng).include(ZHENGZHOU).include(BEIJING).build();
//        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    /**
     * 监听拖动marker时事件回调
     */
    @Override
    public void onMarkerDrag(Marker marker) {

    }
    /**
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this, marker.getTitle() + "拖动位置："
                + marker.getPosition().latitude + ","
                + marker.getPosition().longitude, Toast.LENGTH_SHORT).show();

    }


    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.addr));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    //声明定位回调监听器
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //暂停绘制地图
        binding.mapGd.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        binding.mapGd.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存地图当前的状态
        binding.mapGd.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapGd.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(this);
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();

            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            mLocationClient.setLocationOption(mLocationOption);
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);

            //获取最近3s内精度最高的一次定位结果：
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            //mLocationOption.setOnceLocationLatest(true);
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            // mLocationOption.setInterval(1000);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            mLocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }
}
