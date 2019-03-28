package com.example.mrc.attendencesystem.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.mrc.attendencesystem.AttendenceSystemApplication;
import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.adapter.SimpleSignInRecordAdapter;
import com.example.mrc.attendencesystem.clientandserver.ClientUtil;
import com.example.mrc.attendencesystem.entity.GroupMessage;
import com.example.mrc.attendencesystem.entity.GroupSignInMessage;
import com.example.mrc.attendencesystem.entity.TranObject;
import com.example.mrc.attendencesystem.provider.TimeTransform;

import java.util.ArrayList;
import java.util.List;

public class GetSignMessageActivity extends BaseActivity implements SensorEventListener{
    MapView mMapView;
    TextView mTvEndTime ,mTvNumber;
    RecyclerView mSignRecordRecyclerView;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    BaiduMap mBaiduMap;

    int messageId;
    int groupId;
    String adminId;
    String phoneNumber;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    GroupSignInMessage adminGroupSignInMessage;
    SimpleSignInRecordAdapter mSimpleSignInRecordAdapter;
    List<GroupSignInMessage> mGroupSignRecordList;
    private AttendenceSystemApplication application;
    //地球半径
    private static final double EARTH_RADIUS = 6378.137;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在申请之前，我们要判断一下是否为安卓6.0机型
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }
        //解决地图打开无显示的问题
        if (!"generic".equalsIgnoreCase(Build.BRAND)) {
            SDKInitializer.initialize(getApplicationContext());
        }
        setContentView(R.layout.activity_get_sign_message);
        application = (AttendenceSystemApplication)getApplicationContext();

        Intent intent =getIntent();
        messageId = intent.getIntExtra("messageId" ,-1);
        groupId = intent.getIntExtra("groupId" ,-1);
        adminId = intent.getStringExtra("adminId");
        phoneNumber = intent.getStringExtra("phoneNumber");
        findView();
        init();
    }

    void findView(){
        mMapView = (MapView) findViewById(R.id.map_view);
        mTvEndTime = (TextView) findViewById(R.id.tv_end_time);
        mTvNumber = (TextView) findViewById(R.id.tv_number);
        mSignRecordRecyclerView = (RecyclerView) findViewById(R.id.rv_sign_record);
    }

    void init(){
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setIndoorEnable(true);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mGroupSignRecordList = new ArrayList <>();
        mSimpleSignInRecordAdapter = new SimpleSignInRecordAdapter(GetSignMessageActivity.this ,mGroupSignRecordList);
        mSignRecordRecyclerView.setAdapter(mSimpleSignInRecordAdapter);
        //获取所有记录
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessageId(messageId);
        groupMessage.setContentType(2);
        groupMessage.setGroupId(groupId);
        groupMessage.setFromId(phoneNumber);
        ClientUtil.getSingleSignRecord(application ,groupMessage);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener((SensorEventListener) this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //第一次被拒绝后，第二次访问时，向用户说明为什么需要此权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(this, "开启后使用定位功能", Toast.LENGTH_SHORT).show();
            }
            //若权限没有开启，则请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }
    }

    //当用户选择接受或者拒绝时，申请权限会执行一个回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PackageManager.PERMISSION_GRANTED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //请求权限成功,做相应的事情 } else {
                //请求失败则提醒用户
                Toast.makeText(GetSignMessageActivity.this, "请求权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void setMarker(double lat ,double lon){

        //设置坐标点
        LatLng point1 = new LatLng(lat, lon);

        View mView = LayoutInflater.from(GetSignMessageActivity.this).inflate(R.layout.layout_baidu_map_item, null);
        BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory
                .fromView(mView);

        OverlayOptions adminLocation = new MarkerOptions().position(point1)
                .icon(bitmapDescriptor1).zIndex(15).draggable(true);
        //在地图上添加
        mBaiduMap.addOverlay(adminLocation);
    }


    @Override
    public void getMessage(TranObject msg) {
        if(msg != null){
            switch (msg.getType()){
                /*获取一个签到的所有记录*/
                case GET_SINGLE_SIGNIN_RECORD:
                    if(msg.isSuccess()){
                        List<GroupSignInMessage> recordList = new ArrayList<>();
                        recordList = msg.getSignInfoslist();
                        int number = 0;
                        adminGroupSignInMessage = new GroupSignInMessage();
                        mBaiduMap.clear();
                        for(int i = 0;i<recordList.size();i++){
                            if(recordList.get(i).getType() == 2){
                                number ++;
                                mGroupSignRecordList.add(0 ,recordList.get(i));
                                setMarker(recordList.get(i).getLatitude() ,recordList.get(i).getLongitude());
                            }else if(recordList.get(i).getType() == 1){
                                adminGroupSignInMessage = recordList.get(i);
                                setMarker(adminGroupSignInMessage.getLatitude() ,adminGroupSignInMessage.getLongitude());
                            }
                        }
                        mSimpleSignInRecordAdapter.notifyDataSetChanged();
                        String endTime = TimeTransform.stampToTime(adminGroupSignInMessage.getEndTime());
                        mTvEndTime.setText(endTime);
                        mTvNumber.setText(String.valueOf(number));
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
