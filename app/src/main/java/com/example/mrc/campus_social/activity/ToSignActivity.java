package com.example.mrc.campus_social.activity;

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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import com.example.mrc.campus_social.CampusSocialSystemApplication;
import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.clientandserver.ClientUtil;
import com.example.mrc.campus_social.entity.GroupMessage;
import com.example.mrc.campus_social.entity.GroupSignInMessage;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.provider.TimeTransform;

import java.util.ArrayList;
import java.util.List;

public class ToSignActivity extends BaseActivity implements SensorEventListener, View.OnClickListener {
    MapView mMapView;
    TextView mTvEndTime ,mTvNumber ,mTvCancel ,mTvOk ,mTvLat ,mTvLon ,mTvResult ,mTvDistance;
    ImageView mRefresh;

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

    String originatorId; //发起人id
    int messageId;
    int groupId;
    String adminId;
    String phoneNumber;
    boolean isFirstLoc = true; // 是否首次定位
    boolean mIsLegal ;  //是否在指定签到范围
    private MyLocationData locData;
    GroupSignInMessage adminGroupSignInMessage;
    private CampusSocialSystemApplication application;
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
        setContentView(R.layout.activity_to_sign);
        application = (CampusSocialSystemApplication)getApplicationContext();

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
        mTvNumber = (TextView) findViewById(R.id.number);
        mTvEndTime = (TextView) findViewById(R.id.end_time);
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mTvOk = (TextView) findViewById(R.id.tv_ok);
        mTvLat = (TextView) findViewById(R.id.lat);
        mTvLon = (TextView) findViewById(R.id.lon);
        mTvResult = (TextView) findViewById(R.id.result);
        mTvDistance = (TextView) findViewById(R.id.distance);
        mRefresh = (ImageView) findViewById(R.id.refresh);

        mTvCancel.setOnClickListener(this);
        mTvOk.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
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

        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessageId(messageId);
        groupMessage.setContentType(2);
        groupMessage.setGroupId(groupId);
        groupMessage.setFromId(phoneNumber);
        //获取所有记录
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_ok:
                GroupSignInMessage groupSignInMessage = new GroupSignInMessage();
                groupSignInMessage.setGroupId(groupId);
                groupSignInMessage.setRecordId(messageId);
                groupSignInMessage.setType(2);
                groupSignInMessage.setOriginatorId(originatorId);
                long timeStamp = System.currentTimeMillis();
                groupSignInMessage.setStartTime(timeStamp);
                groupSignInMessage.setEndTime(adminGroupSignInMessage.getEndTime());
                groupSignInMessage.setLatitude(adminGroupSignInMessage.getLatitude());
                groupSignInMessage.setLongitude(adminGroupSignInMessage.getLongitude());
                groupSignInMessage.setRegion(adminGroupSignInMessage.getRegion());
                groupSignInMessage.setReceiverId(phoneNumber);
                groupSignInMessage.setRlatitude(mCurrentLat);
                groupSignInMessage.setRlongitude(mCurrentLon);
                if(mIsLegal){
                    groupSignInMessage.setResult(1);  //1表示成功，0表示失败
                }else {
                    groupSignInMessage.setResult(0);  //1表示成功，0表示失败
                }
                ClientUtil.sendSignRequest(application ,groupSignInMessage);
                finish();
                break;
            case R.id.refresh:

                break;
            default:
                break;
        }
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
            mTvLat.setText(String.valueOf(location.getLatitude()));
            mTvLon.setText(String.valueOf(location.getLongitude()));
            mIsLegal = GetDistance(adminGroupSignInMessage.getLatitude() ,
                    adminGroupSignInMessage.getLongitude(),mCurrentLat ,mCurrentLon ,
                    adminGroupSignInMessage.getRegion());
            if(mIsLegal){
                mTvResult.setText("处于指定区域内");
            }else {
                mTvResult.setText("不在指定区域内");
            }
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
                Toast.makeText(ToSignActivity.this, "请求权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void setMarker(double lat ,double lon){
        mBaiduMap.clear();
        //设置坐标点
        LatLng point1 = new LatLng(lat, lon);

        View mView = LayoutInflater.from(ToSignActivity.this).inflate(R.layout.layout_baidu_map_item, null);
        BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory
                .fromView(mView);

        OverlayOptions adminLocation = new MarkerOptions().position(point1)
                .icon(bitmapDescriptor1).zIndex(15).draggable(true);
        //在地图上添加
        mBaiduMap.addOverlay(adminLocation);
    }

    /**
     * 根据经纬度查询距离
     * @return
     */
    private static boolean GetDistance(double lat1,double lng1, double lat2,  double lng2 ,int distance)
    {
        Log.d("ddddd" ,lat1+"");
        Log.d("ddddd" ,lng1+"");
        Log.d("ddddd" ,lat2+"");
        Log.d("ddddd" ,lng2+"");
        double s = Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1))
                * EARTH_RADIUS;

        //s = Math.round(s * 10000) / 10000;
        Log.d("ddddd" ,"distance:"+distance + "S:"+s);

        if((double)distance >= s){
            return true;
        }else {
            return false;
        }
    }
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }


    @Override
    public void getMessage(TranObject msg) {
        if(msg != null){
            switch (msg.getType()){
                /*获取一个签到的所有记录*/
                case GET_SINGLE_SIGNIN_RECORD:
                    if(msg.isSuccess()){
                        List<GroupSignInMessage> recordList = new ArrayList <>();
                        recordList = msg.getSignInfoslist();
                        int number = 0;
                        adminGroupSignInMessage = new GroupSignInMessage();
                        for(int i = 0;i<recordList.size();i++){
                            if(recordList.get(i).getType() == 2){
                                number ++;
                            }else if(recordList.get(i).getType() == 1){
                                adminGroupSignInMessage = recordList.get(i);
                            }
                        }
                        String endTime = TimeTransform.stampToTime(adminGroupSignInMessage.getEndTime());
                        originatorId = adminGroupSignInMessage.getOriginatorId();
                        setMarker(adminGroupSignInMessage.getLatitude() ,adminGroupSignInMessage.getLongitude());
                        mTvEndTime.setText(endTime);
                        mTvNumber.setText(String.valueOf(number));
                        mTvDistance.setText(adminGroupSignInMessage.getRegion() + "米");
                        //计算与发起签到人的距离

                    }
                    break;
                /*发送聊天记录给服务器端*/
                case SEND_GROUP_MESSAGE:
                    //从服务器端推送过来的消息
                    if(msg.isSuccess()){

                    }
                    break;
                default:
                    break;
            }
        }
    }
}
