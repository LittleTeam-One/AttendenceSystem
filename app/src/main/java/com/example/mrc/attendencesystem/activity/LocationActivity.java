package com.example.mrc.attendencesystem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.mrc.attendencesystem.R;

public class LocationActivity extends AppCompatActivity {
    MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);

        findView();
        init();
    }

    void findView(){
        mMapView = (MapView)findViewById(R.id.map_view);
    }

    void init(){
        mBaiduMap = mMapView.getMap();
    }
}
