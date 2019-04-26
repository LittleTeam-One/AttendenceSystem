package com.example.mrc.campus_social;

import android.app.Activity;
import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.mrc.campus_social.clientandserver.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mr.C
 */

public class CampusSocialSystemApplication extends Application {
    public static final String SHARED_PREF = "shared_preference";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_REALNAME = "user_realname";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PHONE = "user_phone";
    private static Map<String, Activity> destroyMap = new HashMap<>();
    private boolean isClientStart;// 客户端连接是否启动
    private Client client;
    private String ip = "192.168.137.1";
    private int port = 5469;
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        client = new Client(ip,port);// 创建Client对象
    }

    public Client getClient() {
        return client;
    }


    public boolean isClientStart() {
        return isClientStart;
    }

    public void setClientStart(boolean isClientStart) {
        this.isClientStart = isClientStart;
    }

    private void registerSDK() {
        //注册百度地图Api
        /*SDKInitializer.initialize(getApplicationContext());*/

    }

    /**
     * 添加要销毁的activity到销毁队列
     *
     * @param activity
     * @param activityName
     */
    public static void addDestroyActivity(Activity activity, String activityName) {
        destroyMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     *
     * @param activityName
     */
    public static void destroyActivity(String activityName) {
        Set<String> keySet = destroyMap.keySet();
        for (String key : keySet) {
            destroyMap.get(key).finish();
        }
    }
}
