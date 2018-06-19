package com.example.mrc.attendencesystem.entity;

/**
 * 传输对象类型
 * 
 * @author mqh
 * 
 */
public enum TranObjectType {
	REGISTER, // 注册
	LOGIN, // 用户登录
	REGISTER_TEST,//检测账号是否已存在
	RESET_PASSWORD,//重设密码
	UPDATE_USER,//更新用户信息
	SET_IMAGEPATH,//设置头像路径
	LOGOUT, // 用户退出登录
	GET_GROUPS,//获取与用户有关的所有组
	ADD_GROUP,//添加组
	DELETE_GROUP,//删除组
	SEND_JOIN_REQUEST,//发送请求加入组
	GET_JOIN_REQUEST,//获取加入组的请求
	SEND_JOIN_RESPONSE,//发送加入组的的回应消息
	GET_JOIN_RESPONSE,//获取加入组的回应消息
	OUT_GROUP,//退出组
	HEART_TEST,//心跳检测
	SEARCH_GROUP,//搜索群
	SEND_SIGN_REQUEST,//推送发起签到
	GET_SIGN_REQUEST,//接受签到请求
	SEND_SIGN_RESPONSE,//发送签到的回应消息
	GET_SIGN_RESPONSE,//获取签到的回应消息
	GET_USER_SIGN_RECORD,//获取单个用户的签到记录
	GET_GROUP_SIGN_RECORD,//获取群的签到记录
	GET_GROUP_MESSAGE,
	SEND_GROUP_MESSAGE,  //普通消息或者发起签到的消息
	GET_SINGLE_SIGNIN_RECORD,  //获取单一的签到记录
	USER_SIGN_IN, //用户签到
	GET_GROUP_ITEM,
}
