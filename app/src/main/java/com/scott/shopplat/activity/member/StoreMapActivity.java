package com.scott.shopplat.activity.member;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.scott.shopplat.R;
import com.scott.shopplat.activity.BaseActivity;
import com.scott.shopplat.utils.Logs;
import com.scott.shopplat.utils.SXUtils;

public class StoreMapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener,AMap.OnMapClickListener
        ,AMap.OnMarkerDragListener, AMap.OnMapLoadedListener {
    private MapView mapView;
    private AMap aMap;
    private TextView mLocationErrText;
    private Activity activity;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private MyLocationStyle myLocationStyle;
    public   LatLng locationNow;// 北京市经纬度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        activity = this;

//        //这里以ACCESS_COARSE_LOCATION为例
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            //申请WRITE_EXTERNAL_STORAGE权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                    1000);//自定义的code
//        }

        init();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(activity, "111", Toast.LENGTH_SHORT).show();
            } else
            {
                // Permission Denied
                Toast.makeText(activity, "被拒绝", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }
    /**
     * 初始化
     */
    private void init() {
        registerBack();
        setTitle("选择门店");

        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
//        mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
//        mGPSModeGroup.setVisibility(View.GONE);
//        mLocationErrText = (TextView)findViewById(R.id.location_errInfo_text);
//        mLocationErrText.setVisibility(View.GONE);

        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);

//        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false


        aMap.setOnMarkerDragListener(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        setupLocationStyle();

//        mapView.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
//
//            @Override
//            public void onMapStatusChangeStart(MapStatus mapStatus) {
//
//            }
//
//            @Override
//            public void onMapStatusChangeFinish(MapStatus mapStatus) {
//
//            }
//
//            @Override
//            public void onMapStatusChange(MapStatus mapStatus) {
//                mMarker.setPosition(mapStatus.target);
//            }
//        });
    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle(){
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.add_img));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
//        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
    }


    private void addMarkersToMap() {
        // 文字显示标注，可以设置显示内容，位置，字体大小颜色，背景色旋转角度
//        TextOptions textOptions = new TextOptions()
//                .position(locationNow)
//                .text("Text")
//                .fontColor(Color.BLACK)
//                .backgroundColor(Color.BLUE)
//                .fontSize(30)
//                .rotate(20)
//                .align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
//                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD);
//        aMap.addText(textOptions);

//
//        Marker marker = aMap.addMarker(new MarkerOptions()
//                .position(locationNow)
//                .title("当前位置")
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(R.mipmap.add_img))
//                .draggable(true));
//        marker.setRotateAngle(90);// 设置marker旋转90度
//        marker.setPositionByPixels(100, 100);
//        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if(location != null) {
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if(bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);
                locationNow= new LatLng(location.getLatitude(), location.getLongitude());
                addMarkersToMap();
                /*
                errorCode
                errorInfo
                locationType
                */
                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType );
            } else {
                Log.e("amap", "定位信息， bundle is null ");

            }

        } else {
            Log.e("amap", "定位失败");
        }
    }
    @Override
    public void onMapClick(LatLng latLng) {
//点击地图后清理图层插上图标，在将其移动到中心位置
        aMap.clear();
//        latitude = latLng.latitude;
//        longitude = latLng.longitude;
        Logs.i(latLng.latitude+"===="+latLng.longitude);
        MarkerOptions otMarkerOptions = new MarkerOptions();
        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.close));
        otMarkerOptions.position(latLng);
        aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));


    }
    @Override
    public void onMapLoaded() {

    }

    /**
     * 监听拖动marker时事件回调
     */
    @Override
    public void onMarkerDrag(Marker marker) {
        String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
                + marker.getPosition().latitude + ","
                + marker.getPosition().longitude + ")";
        SXUtils.getInstance(activity).ToastCenter(curDes);
//        markerText.setText(curDes);
    }

    /**
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        SXUtils.getInstance(activity).ToastCenter("停止拖动");
    }

    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
        SXUtils.getInstance(activity).ToastCenter("开始拖动");
    }





}
