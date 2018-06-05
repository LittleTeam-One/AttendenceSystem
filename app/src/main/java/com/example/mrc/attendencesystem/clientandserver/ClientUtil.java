package com.example.mrc.attendencesystem.clientandserver;

import android.util.Log;

import com.example.ecxkj.helloclass.Activity.application.MyApplication;
import com.example.ecxkj.helloclass.Activity.bean.Group;
import com.example.ecxkj.helloclass.Activity.bean.Message;
import com.example.ecxkj.helloclass.Activity.bean.User;
import com.example.ecxkj.helloclass.Activity.util.TranObject;
import com.example.ecxkj.helloclass.Activity.util.TranObjectType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * @author mqh
 *
 */
public class ClientUtil {

	public static void checkLogin(User user, MyApplication application)
    {
        Log.d("client", "checkLogin: ");
        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Log.d("client", "isClientStart: ");
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.LOGIN);
            User u = new User();
            u.setPhone(user.getPhone());
            u.setPassword(user.getPassword());
            o.setUser(u);
            String responseString = mGson.toJson(o);
            Log.d("client", "responseString: "+responseString);
            out.setMsg(responseString);
            Log.d("client", "setMeg: ");
        }

    }

    /**
     * mqh
     *
     * @param phone
     */
    public static void isRegisterOrNot(String phone, MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.REGISTER_TEST);
            User user = new User(phone);
            o.setUser(user);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }

    }

    /**
     * mqh
     *
     */
    public static void registerUser(String password, String phone, String nickName,
    MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.REGISTER);
            User user = new User(password,nickName,phone);
            o.setUser(user);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }

    }
    /**
     * mqh
     *
     */
    public static void resetPassword(String phone, String password, MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if (application.isClientStart()) {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.RESET_PASSWORD);
            User user = new User(phone, password);
            o.setUser(user);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     *
     */
    public static void getGroups(String stuId,MyApplication application)
    {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.GET_GROUPS);
            o.setFromUser(stuId);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }

    }
    /**
     *
     */
    public static void addGroup(String groupOwner,String groupName,MyApplication application)
    {
        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.ADD_GROUP);
            Group group = new Group(groupName,groupOwner);
            o.setGroup(group);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }

    }
    /**
     *删除群
     */
    public static void deleteGroup(String groupId,MyApplication application)
    {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.DELETE_GROUP);
            o.setFromUser(groupId);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }

    }
    /**
     *tuichu
     */
    public static void outGroup(String groupId,String stuId,MyApplication application)
    {
        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.ADD_GROUP);
            o.setFromUser(stuId);
            o.setToUser(groupId);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }

    }

    /**
     *搜索群
     */
    public static void searchGroup(String groupName,MyApplication application)
    {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SEARCH_GROUP);
            Group group =new Group();
            group.setGroupName(groupName);
            o.setGroup(group);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     *
     */
    public static void updateUser(User user, MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.UPDATE_USER);
            o.setUser(user);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     * 上传图片请求服务器
     */
    public static void updateImagePassword(String stuId, String imagePath, MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SET_IMAGEPATH);
            User user = new User();
            user.setId(stuId);
            user.setImgPath(imagePath);
            o.setUser(user);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     * 发送加群申请
     */
    public static void sendJoinRequest(String senderid, String receiverid,int type, int groupid,String msgcontent,
                                       MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SEND_JOIN_REQUEST);
            Message message = new Message();
            message.setGroup_id(groupid);
            message.setSender_id(senderid);
            message.setReceiver_id(receiverid);
            message.setType(type);
            message.setMes_content(msgcontent);
            o.setMessage(message);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     * 发送加入群的回应（若身份为群主）
     */
    public static void sendJoinResponse(String senderid, String receiverid,int response_type, int groupid,String msgcontent,
                                       MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SEND_JOIN_RESPONSE);
            Message message = new Message();
            message.setGroup_id(groupid);
            message.setSender_id(senderid);
            message.setReceiver_id(receiverid);
            message.setType(response_type);
            message.setMes_content(msgcontent);
            o.setMessage(message);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     * 获取个人签到记录
     */
    public static void getUserSignRecord(String senderid, String groupid, MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.GET_USER_SIGN_RECORD);
            o.setFromUser(senderid);
            o.setToUser(groupid);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     * 获取群签到记录
     */
    public static void getGroupSignRecord(String groupid, MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.GET_GROUP_SIGN_RECORD);
            o.setFromUser(groupid);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     * zy
     * 发起签到
     */
    public static void sendSignRequest(String senderid,int groupid,String groupName,String content,int type, MyApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SEND_SIGN_REQUEST);
            Message message = new Message();
            message.setGroup_id(groupid);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            message.setTime(new Date());
            message.setGroupName(groupName);
            message.setSender_id(senderid);
            message.setType(type); //消息类型
            message.setMes_content(content); //消息内容
            o.setMessage(message);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
}
