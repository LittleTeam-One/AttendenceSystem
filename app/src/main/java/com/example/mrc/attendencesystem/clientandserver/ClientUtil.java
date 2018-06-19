package com.example.mrc.attendencesystem.clientandserver;

import android.util.Log;

import com.example.mrc.attendencesystem.AttendenceSystemApplication;
import com.example.mrc.attendencesystem.entity.Group;
import com.example.mrc.attendencesystem.entity.GroupMessage;
import com.example.mrc.attendencesystem.entity.GroupRequest;
import com.example.mrc.attendencesystem.entity.GroupSignInMessage;
import com.example.mrc.attendencesystem.entity.TranObject;
import com.example.mrc.attendencesystem.entity.TranObjectType;
import com.example.mrc.attendencesystem.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 
 * @author mqh
 *
 */
public class ClientUtil {

	public static void checkLogin(User user, AttendenceSystemApplication application)
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
            u.setPhoneNumber(user.getPhoneNumber());
            u.setPassword(user.getPassword());
            o.setUser(u);
            o.setFromUser(user.getPhoneNumber());
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
    /*public static void isRegisterOrNot(String phone, MyApplication application) {

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

    *//**
     * mqh
     *
     *//*
    public static void registerUser(String password, String phone, String nickName,
    AttendenceSystemApplication application) {

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
    *//**
     * mqh
     *
     *//*
    public static void resetPassword(String phone, String password, AttendenceSystemApplication application) {

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

    */
    /**
     *
     */
    public static void getGroups(String stuId,AttendenceSystemApplication application)
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
    /*
    public static void addGroup(String groupOwner,String groupName,AttendenceSystemApplication application)
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
    *//**
     *删除群
     *//*
    public static void deleteGroup(String groupId,AttendenceSystemApplication application)
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
    *//**
     *tuichu
     *//*
    public static void outGroup(String groupId,String stuId,AttendenceSystemApplication application)
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

    *//**
     *搜索群
     */
    public static void searchGroup(String groupName,AttendenceSystemApplication application)
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
     *//*
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
    *//**
     * mqh
     * 上传图片请求服务器
     *//*
    public static void updateImagePassword(String stuId, String imagePath, AttendenceSystemApplication application) {

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
    *//**
     * mqh
     * 发送加群申请
     */
    public static void sendJoinRequest(Group group,
                                       AttendenceSystemApplication application) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SEND_JOIN_REQUEST);
            o.setGroup(group);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     * 发送加入群的回应（若身份为群主）
     *//*
    public static void sendJoinResponse(String senderid, String receiverid,int response_type, int groupid,String msgcontent,
                                        AttendenceSystemApplication application) {

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
    */

    /**
     * mqh
     * 获取群聊天记录
     */
    public static void getGroupChatRecord(String phoneNumber, int groupid, AttendenceSystemApplication application ,int page) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.GET_GROUP_MESSAGE);
            o.setToUser(String.valueOf(groupid));
            GroupRequest groupRequest =new GroupRequest();
            groupRequest.setGroupId(groupid);
            groupRequest.setCurrentPage(page);
            o.setRequest(groupRequest);
            o.setFromUser(phoneNumber);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     * mqh
     * 发送群聊天记录
     */
    public static void sendGroupChatRecord(AttendenceSystemApplication application , GroupMessage groupMessage, GroupSignInMessage groupSignInMessage) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SEND_GROUP_MESSAGE);
            o.setSendGroupMessage(groupMessage);
            o.setSignInfo(groupSignInMessage);
            o.setFromUser(groupMessage.getFromId());
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }


    /**
     * mqh
     ** 获取单一的签到记录
     */
    public static void getSingleSignRecord(AttendenceSystemApplication application , GroupMessage groupMessage) {
        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.GET_SINGLE_SIGNIN_RECORD);
            o.setSendGroupMessage(groupMessage);
            o.setFromUser(groupMessage.getFromId());
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }
    /**
     * mqh
     * 获取群签到记录
     */
    public static void getGroupSignRecord( AttendenceSystemApplication application, Group group, String phoneNumber) {
        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.GET_GROUP_SIGN_RECORD);
            o.setGroup(group);
            o.setFromUser(phoneNumber);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     * zy
     * 签到数据传到服务端
     */
    public static void sendSignRequest(AttendenceSystemApplication application ,GroupSignInMessage groupSignInMessage) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.USER_SIGN_IN);
            o.setSignInfo(groupSignInMessage);
            o.setFromUser(groupSignInMessage.getReceiverId());
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     * zy
     * 签到数据传到服务端
     */

    public static void setSignIn(AttendenceSystemApplication application , GroupMessage groupMessage ,GroupSignInMessage groupSignInMessage) {

        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.SEND_GROUP_MESSAGE);
            o.setSendGroupMessage(groupMessage);
            o.setSignInfo(groupSignInMessage);
            o.setFromUser(groupMessage.getFromId());
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     * zy
     * 获取群成员
     */
    public static void getGroupMember(AttendenceSystemApplication application ,Group group ,String phoneNumber) {
        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()  //格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式化输出
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.GET_GROUP_MEMBERS);
            o.setFromUser(phoneNumber);
            o.setGroup(group);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     *注册用户
     */
    public static void registerUser(AttendenceSystemApplication application,
                                    User user) {
        Gson mGson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if(application.isClientStart())
        {
            Client client = application.getClient();
            ClientOutputThread out = client.getClientOutputThread();
            TranObject o = new TranObject(TranObjectType.REGISTER);
            o.setUser(user);
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }
    }

    /**
     *创建群组
     */
    public static void createGroup(AttendenceSystemApplication application ,Group group)
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
            o.setGroup(group);
            o.setFromUser(group.getAdminId());
            String responseString = mGson.toJson(o);
            out.setMsg(responseString);
        }

    }
}
