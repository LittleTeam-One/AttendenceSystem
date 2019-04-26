package com.example.mrc.campus_social.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetSignActivity extends BaseActivity implements SensorEventListener, View.OnClickListener {
    MapView mMapView;
    TextView mTvDate ,mTvTime ,mTvOk ,mTvLat ,mTvLon;
    EditText mEtDistance;

    static int mYear;
    static int mMonth;
    static int mDay;
    static String mWeek;
    static int mHour;
    static int mMinute;

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
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;

    int groupId;
    String adminId;
    String phoneNumber;
    GroupSignInMessage adminGroupSignInMessage;
    private CampusSocialSystemApplication application;
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
        setContentView(R.layout.activity_set_sign);
        application = (CampusSocialSystemApplication)getApplicationContext();
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        groupId = intent.getIntExtra("groupId" ,-1);
        adminId = intent.getStringExtra("adminId");

        findView();
        init();
    }

    void findView(){
        mMapView = (MapView) findViewById(R.id.map_view);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvLat = (TextView) findViewById(R.id.lat);
        mTvLon = (TextView) findViewById(R.id.lon);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvOk = (TextView) findViewById(R.id.tv_ok);
        mEtDistance = (EditText) findViewById(R.id.et_distance);

        mTvDate.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mTvOk.setOnClickListener(this);
        mEtDistance.setOnClickListener(this);
    }
    void init(){
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DATE);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mWeek = getWeek(mYear ,mMonth ,mDay);
        mTvDate.setText(getDateString(mYear ,mMonth ,mDay ,mWeek));
        mTvTime.setText(getTimeString(mHour ,mMinute));

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
            case R.id.tv_date:
                AlertDatePickerDialog();  //设置日期
                break;
            case R.id.tv_time:
                AlertTimePickerDialog();  //设置时间
                break;
            case R.id.tv_ok:
                Log.d("tttttt" ,phoneNumber);
                GroupMessage groupMessage = new GroupMessage();
                groupMessage.setGroupId(groupId);
                groupMessage.setFromId(phoneNumber);
                groupMessage.setContentType(2);
                GroupSignInMessage groupSignInMessage = new GroupSignInMessage();
                groupSignInMessage.setOriginatorId(phoneNumber);
                groupSignInMessage.setType(1);
                groupSignInMessage.setGroupId(groupId);
                long timeStamp = System.currentTimeMillis();  //设置开始时间
                groupSignInMessage.setStartTime(timeStamp);
                long endTime = toTimeStamp(mYear ,mMonth ,mDay ,mHour ,mMinute);
                groupSignInMessage.setEndTime(endTime);
                groupSignInMessage.setLatitude(mCurrentLat);
                groupSignInMessage.setLongitude(mCurrentLon);
                groupSignInMessage.setRegion(Integer.parseInt(mEtDistance.getText().toString())*100);
                ClientUtil.setSignIn(application ,groupMessage ,groupSignInMessage);
                finish();
                break;
            default:
                break;

        }
    }

    public static long toTimeStamp(int year,int month,int days,int hour,int mintue){
        long timeStamp = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR ,year);
        calendar.add(Calendar.MONTH ,month);
        calendar.add(Calendar.DAY_OF_MONTH ,days);
        calendar.add(Calendar.HOUR_OF_DAY ,hour);
        calendar.add(Calendar.MINUTE ,mintue);
        java.util.Date date =calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Timestamp ts = Timestamp.valueOf(time);
        timeStamp = ts.getTime();
        return timeStamp;
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
                Toast.makeText(SetSignActivity.this, "请求权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void setMarker(double lat ,double lon){
        mBaiduMap.clear();
        //设置坐标点
        LatLng point1 = new LatLng(lon, lat);

        View mView = LayoutInflater.from(SetSignActivity.this).inflate(R.layout.layout_baidu_map_item, null);
        BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory
                .fromView(mView);

        OverlayOptions adminLocation = new MarkerOptions().position(point1)
                .icon(bitmapDescriptor1).zIndex(15).draggable(true);
        //在地图上添加
        mBaiduMap.addOverlay(adminLocation);
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

    public void AlertDatePickerDialog(){
        //通过values和values-v21下的MyDatePickerDialogTheme改变颜色，
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                //将时间转化为日期
                mWeek = getWeek(year ,month ,day);
                mYear = year;
                mMonth = month;
                mDay = day;
                mWeek = getWeek(mYear ,mMonth ,mDay);
                mTvDate.setText(getDateString(mYear ,mMonth ,mDay ,mWeek));
                //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog=new DatePickerDialog(SetSignActivity.this , AlertDialog.THEME_DEVICE_DEFAULT_DARK ,listener ,mYear,mMonth,mDay);
        if (android.os.Build.VERSION.SDK_INT>=11){
            dialog.getDatePicker().setMinDate(new Date().getTime());  //设置日历最小的可选时间
        }
        dialog.show();
    }

    public void AlertTimePickerDialog(){
        //通过values和values-v21下的MyDatePickerDialogTheme改变颜色，
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                mTvTime.setText(getTimeString(mHour ,mMinute));
            }
        };

        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        TimePickerDialog dialog=new TimePickerDialog(SetSignActivity.this , AlertDialog.THEME_DEVICE_DEFAULT_DARK ,listener ,mHour,mMinute,true);
        dialog.show();
    }

        /** 将日期转化为周期
         * @author  cqx
         * create at 2018/5/27 19:34
         */
    public String getWeek(int year ,int month ,int day){
        Calendar setDate = Calendar.getInstance();
        setDate.set(year ,month ,day);
        int weekCode = setDate.get(Calendar.DAY_OF_WEEK);
        String weekString = null;
        switch (weekCode){
            case 1 :
                weekString = "星期天";
                break;
            case 2 :
                weekString = "星期一";
                break;
            case 3 :
                weekString = "星期二";
                break;
            case 4 :
                weekString = "星期三";
                break;
            case 5 :
                weekString = "星期四";
                break;
            case 6 :
                weekString = "星期五";
                break;
            case 7 :
                weekString = "星期六";
                break;
        }
        return weekString;
    }

    String getDateString(int year ,int months ,int day ,String week){
        String result;
        String monthsString;
        String dayString;

        if((months+1)<10){
            monthsString = "0" + String.valueOf(months);
        }else {
            monthsString = String.valueOf(months);
        }
        if(day<10){
            dayString = "0" + String.valueOf(day);
        }else {
            dayString = String.valueOf(day);
        }
        result = String.valueOf(year) + "年"+ monthsString  + "月" + dayString + "日  " + week;
         return result;
    }

    String getTimeString(int hour ,int minute){
        String result;
        String hourString;
        String minuteString;
        if(hour<10){
            hourString = "0" + String.valueOf(hour);
        }else {
            hourString = String.valueOf(hour);
        }
        if(minute<10){
            minuteString = "0" + String.valueOf(minute);
        }else {
            minuteString = String.valueOf(minute);
        }
        result = hourString + ":" + minuteString;
        return result;
    }

    @Override
    public void getMessage(TranObject msg) {

    }
}
