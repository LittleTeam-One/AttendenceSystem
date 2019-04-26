package com.example.mrc.campus_social.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.mrc.campus_social.CampusSocialSystemApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.example.mrc.campus_social.provider.CampusSocialSystem;
import com.example.mrc.campus_social.clientandserver.Client;
import com.example.mrc.campus_social.clientandserver.ClientInputThread;
import com.example.mrc.campus_social.clientandserver.ClientOutputThread;
import com.example.mrc.campus_social.clientandserver.MessageListener;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.entity.TranObjectType;
import com.example.mrc.campus_social.entity.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

public class SocketConnectService extends Service {
    private static final int MSG = 1000;
    private CampusSocialSystemApplication application;
    //private SharePreferenceUtil util;
    private Client client;
    private boolean isStart = false;// 是否与服务器连接上
    private Context mContext = this;
    private long lastSendTime;//最后一次发送数据的时间
    private Client.ClientThread clientThread;
    private SharedPreferences mSharedPreferences;
    long checkDelay = 10;
    long keepAliveDelay = 30000;
    long lastReceiveTime;//最近一次接受服务器发送数据的时间


    public SocketConnectService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = (CampusSocialSystemApplication) this.getApplicationContext();
        mSharedPreferences = getSharedPreferences(CampusSocialSystemApplication.SHARED_PREF,0);
        client = application.getClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("client", "onStartCommand: GetMessageService");
        new Thread(new Runnable() {
            @Override
            public void run() {

                isStart = client.start();
                application.setClientStart(isStart);
                Log.d("client", "onStartCommand: isStart" +isStart);
                if (isStart) {
                    lastSendTime = System.currentTimeMillis();
                    ClientInputThread in = client.getClientInputThread();
                    in.setMessageListener(new MessageListener() {
                        @Override
                        public void Message(String msg) {
                            Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()  //格式化输出（序列化）
                                    .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                                    .create();
                            // Log.d("client", "onStartCommand:msg " +msg);
                            JsonReader jsonReader = new JsonReader(new StringReader(msg));//其中jsonContext为String类型的Json数据
                            jsonReader.setLenient(true);
                            System.out.println(msg);
                            System.out.println(msg.length());
                            TranObject responseObject = gson.fromJson(jsonReader,TranObject.class);
                            /*if(responseObject!=null && responseObject.getType() != TranObjectType.HEART_TEST)
                            {
                                Log.d("client", "onStartCommand:msg " +msg);
                            }*/
                            Log.d("client", "onStartCommand:msg " +msg);
                            Intent broadCast = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(CampusSocialSystem.MSGKEY, responseObject);
                            broadCast.putExtras(bundle);
                            broadCast.setAction(CampusSocialSystem.ACTION);
                            sendBroadcast(broadCast);// 把收到的消息已广播的形式发送出去
                            //Log.d("Client", "run: time" + (System.currentTimeMillis() - lastSendTime));
                            /*if(System.currentTimeMillis() - lastSendTime >keepAliveDelay)
                            {
                                //Log.d("client", "run: time" + (System.currentTimeMillis() - lastSendTime));
                                //Log.d("client", "run: 开始发送心跳包数据");
                                TranObject tranObject =  new TranObject(TranObjectType.HEART_TEST);
                                String userId = mSharedPreferences.getString(CampusSocialSystemApplication.USER_ID,null);
                                // Log.d("client", "run: userId" + userId);
                                tranObject.setFromUser(userId);
                                //得到写线程发送心跳包
                                ClientOutputThread clientOutputThread = client.getClientOutputThread();
                                clientOutputThread.setMsg(gson.toJson(tranObject));
                                //Log.d("client", "run: receive"+gson.toJson(tranObject));
                                lastSendTime = System.currentTimeMillis();//修改最后发送的时间
                            }else {
                                try {
                                    Thread.sleep(checkDelay);
                                } catch (InterruptedException e) {
                                    // TODO: handle exception
                                    e.printStackTrace();
                                    //client.setIsStart(false);
                                }

                            }
                            //接受服务器发送的心跳数据
                            if(responseObject!=null || responseObject.getType()==TranObjectType.HEART_TEST)
                            {
                                lastReceiveTime = System.currentTimeMillis();
                            }
                            //如果收到服务器发送的最后一次心跳检测的数据的时间差超过
                            if(System.currentTimeMillis()-lastReceiveTime> 100000)
                            {
                                //则说明服务器挂了
                                Log.d("client","服务器异常");
                                Toast.makeText(getApplicationContext(),"服务器异常",Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    });

                }

            }
        }).start();
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    // 在服务被摧毁时，做一些事情
    public void onDestroy() {
        super.onDestroy();

        // 给服务器发送下线消息
        if (isStart) {
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.LOGOUT);
            User u = new User();
            //u.setId(Integer.parseInt(util.getId()));
            JSONObject logoutObject = new JSONObject();
            try {
                logoutObject.put("logoutId", u.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //o.setUser(logoutObject);
            out.setMsg(o.toString());
            // 发送完之后，关闭client
            out.setStart(false);
            client.getClientInputThread().setStart(false);
        }
        // Intent intent = new Intent(this, GetMsgService.class);
        // startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
