package com.example.mrc.attendencesystem;

public class AttendenceSystem {
    //保存一些配置信息或者全局变量
    public static final String hostname = "192.168.1.130";
    public static final int port = 5469;
    public static final int timeout = 20000;
    public static final String SERVER_IP = "39.106.42.190";// 服务器ip
    public static final int SERVER_PORT = 8088;// 服务器端口

    public static final int REGISTER_FAIL = 0;//注册失败
    public static final String ACTION = "com.helloclass.message";//消息广播action
    public static final String MSGKEY = "message";//消息的key
    public static final String IP_PORT = "ipPort";//保存ip、port的xml文件名
    public static final String SAVE_USER = "saveUser";//保存用户信息的xml文件名
    public static final String BACKKEY_ACTION="com.way.backKey";//返回键发送广播的action
    public static final String DBNAME = "hl.db";//数据库名称
}
