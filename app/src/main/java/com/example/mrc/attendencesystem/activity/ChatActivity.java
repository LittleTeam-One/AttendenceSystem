package com.example.mrc.attendencesystem.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrc.attendencesystem.AttendenceSystemApplication;
import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.adapter.ChatLogRecyclerViewAdapter;
import com.example.mrc.attendencesystem.clientandserver.ClientUtil;
import com.example.mrc.attendencesystem.entity.GroupMessage;
import com.example.mrc.attendencesystem.entity.GroupSignInMessage;
import com.example.mrc.attendencesystem.entity.TranObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends BaseActivity implements View.OnClickListener, TextWatcher,
        ChatLogRecyclerViewAdapter.OnItemClickListener{
    ImageView mImgTurnBack ,mImgInformation ,mImgLocation ,mImgSetLocation;
    TextView mTvName ;
    EditText mEtSendMessage;
    RecyclerView mChatLogRecyclerView;
    Button mBtnSendMessage;
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ChatLogRecyclerViewAdapter mChatLogRecyclerViewAdapter;
    public static Context mContext;
    private SharedPreferences sp;
    private RelativeLayout root_view;
    private int screenHeight = 0;
    private int keyHeight = 0;
    private static int groupid;
    private static String adminId;
    private static String groupName;
    private static int type;
    private AttendenceSystemApplication application;
    public static String phoneNumber ;
    List<Map<Integer,Object>> mData = new ArrayList<>();
    public final static int FROM_ID = 1;
    public final static int CONTENT_TYPE = 2;
    public final static int CONTENT = 3;
    public final static int MESSAGE_ID = 4;
    public static boolean reStart =false;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mContext =this;
        application = (AttendenceSystemApplication)getApplicationContext();
        sp = mContext.getSharedPreferences(AttendenceSystemApplication.SHARED_PREF,0);
        phoneNumber = sp.getString(AttendenceSystemApplication.USER_PHONE,"");
        Intent intent = getIntent();
        type = intent.getIntExtra("type" ,-1);
        findView();

        if(type == 1){
            groupid = intent.getIntExtra("groupId" ,-1);
            adminId = intent.getStringExtra("adminId");
            groupName = intent.getStringExtra("groupName");
        }else if(type == 2){

        }else if(type == -1){
            finish();
        }

        init();
    }

    void findView(){
        mImgTurnBack = (ImageView)findViewById(R.id.img_turn_back);
        mImgInformation = (ImageView) findViewById(R.id.img_information);
        /*mImgLocation = (ImageView)findViewById(R.id.img_location);
        mImgSetLocation = (ImageView)findViewById(R.id.img_set_location);*/
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.sr_layout);
        mTvName = (TextView)findViewById(R.id.tv_name);
        mEtSendMessage = (EditText)findViewById(R.id.et_send_message);
        mChatLogRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_chat_log);
        mBtnSendMessage = (Button)findViewById(R.id.btn_send_message);

        root_view = (RelativeLayout) findViewById(R.id.root_view);
    }

    void init(){
        mImgTurnBack.setOnClickListener(this);
        mImgInformation.setOnClickListener(this);
        /*mImgLocation.setOnClickListener(this);
        mImgSetLocation.setOnClickListener(this);*/

        mEtSendMessage.addTextChangedListener(this);
        mBtnSendMessage.setOnClickListener(this);
        mBtnSendMessage.setClickable(false);
        mLayoutManager = new LinearLayoutManager(mContext ,LinearLayoutManager.VERTICAL, false);
        mChatLogRecyclerView.setLayoutManager(mLayoutManager);
        if(type == 1){   /*群聊*/
            mTvName.setText(groupName);
            mImgInformation.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_imformation));
            if(mData== null || mData.size() <=0){
                ClientUtil.getGroupChatRecord(phoneNumber, groupid,application ,-1);
            }else {
                ClientUtil.getGroupChatRecord(phoneNumber ,groupid,application ,(Integer) mData.get(mData.size()-1).get(4));
            }

        }else if(type == 2){
            mImgInformation.setImageResource(R.drawable.icon_information);
        }

        mChatLogRecyclerViewAdapter = new ChatLogRecyclerViewAdapter(mContext , mData);
        mChatLogRecyclerViewAdapter.setOnItemClickListener(this);
        //mChatLogRecyclerViewAdapter.setOnItemLongClickListener(this);
        mChatLogRecyclerView.setAdapter(mChatLogRecyclerViewAdapter);
        scrollToLastItem();
        initListener();
        initOtherData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(type == 1){   /*群聊*/
            mTvName.setText(groupName);
            reStart =true;
            mImgInformation.setImageDrawable(getResources().getDrawable(R.drawable.ic_group_imformation));
            if(mData== null || mData.size() <=0){
                ClientUtil.getGroupChatRecord(phoneNumber, groupid,application ,-1);
            }else {
                ClientUtil.getGroupChatRecord(phoneNumber ,groupid,application ,(Integer) mData.get(mData.size()-1).get(4));
            }

        }else if(type == 2){
            mImgInformation.setImageResource(R.drawable.icon_information);
        }
    }

    /**
     * 页面中各种实现按钮功能的点击事件监听
     * @author  cqx
     * create at 2018/5/23 0:04
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_turn_back :
                Intent toMainActivityIntent =new Intent(ChatActivity.this ,MainActivity.class);
                startActivity(toMainActivityIntent);
                break;
            case R.id.img_information :
                Intent intentToInformation = new Intent(ChatActivity.this ,GroupInformationActivity.class);
                intentToInformation.putExtra("phoneNumber" ,phoneNumber);
                intentToInformation.putExtra("groupId" ,groupid);
                intentToInformation.putExtra("adminId" ,adminId);
                intentToInformation.putExtra("groupName" ,groupName);
                startActivity(intentToInformation);
                break;
            /*case R.id.img_location :
                Intent toLocationIntent = new Intent(ChatActivity.this ,LocationActivity.class);
                startActivity(toLocationIntent);
                break;
            case R.id.img_set_location :
                Intent toSetLocationIntent = new Intent(ChatActivity.this ,LocationActivity.class);
                startActivity(toSetLocationIntent);
                break;*/
            case R.id.btn_send_message :
                if(type == 1){   /*群聊*/
                    String content = mEtSendMessage.getText().toString();
                    GroupMessage groupMessage = new GroupMessage();
                    groupMessage.setGroupId(groupid);
                    groupMessage.setFromId(phoneNumber);
                    groupMessage.setContentType(1);
                    groupMessage.setContent(content);
                    GroupSignInMessage groupSignInMessage = new GroupSignInMessage();
                    ClientUtil.sendGroupChatRecord(application ,groupMessage, groupSignInMessage);
                    //保存聊天
                    Map<Integer ,Object> map = new HashMap <Integer, Object>();
                    map.put(1,phoneNumber);
                    map.put(2,1);
                    map.put(3,content);
                    map.put(4,((Integer)mData.get(mData.size()-1).get(4)+1));
                    mData.add(map);
                }else if(type == 2){

                }
                //mData.add("2" + mEtSendMessage.getText());
                mEtSendMessage.setText(null);
                scrollToLastItem();
                break;
            default:break;
        }
    }

    /**
     * 内容编辑框的内容改变监听器 beforeTextChanged   onTextChanged   afterTextChanged
     * @author  cqx
     * create at 2018/5/22 20:53
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(mEtSendMessage.getText() == null || (mEtSendMessage.getText().toString()).length() == 0){
            mBtnSendMessage.setClickable(false);
        }else {
            mBtnSendMessage.setClickable(true);
        }
    }
    
    /**
     * 键盘弹出和落下的监听事件，解决键盘弹出会遮盖把底部聊天内容的问题
     * @author  cqx
     * create at 2018/5/23 0:02
     */
    private void initListener() {
        root_view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    scrollToLastItem();
                    //Toast.makeText(ChatActivity.this,"键盘弹起", Toast.LENGTH_SHORT).show();
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    //Toast.makeText(ChatActivity.this,"键盘落下",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mData == null || mData.size() <=0){
                    ClientUtil.getGroupChatRecord(phoneNumber, groupid,application ,-1);
                }else {
                    ClientUtil.getGroupChatRecord(phoneNumber ,groupid,application ,(Integer) mData.get(mData.size()-1).get(4));
                }
            }
        });
    }
    private void initOtherData() {
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
    }

    /**
     * RecyclerView滑动到最后一个item
     * @author  cqx
     * create at 2018/5/22 22:27
     */
    public void scrollToLastItem(){
        int position = mData.size() - 1;
        mLayoutManager.scrollToPositionWithOffset(position, 0);
        mLayoutManager.setStackFromEnd(true);
    }

    /**
     * 聊天内容的点击事件监听
     * @author  cqx
     * create at 2018/5/23 0:18
     */
    @Override
    public void onItemClick(View itemView, int position) {
        //只有签到才能点击进入签到
        if(adminId.equals(phoneNumber)){
            Intent intent1 = new Intent(ChatActivity.this ,GetSignMessageActivity.class);
            intent1.putExtra("phoneNumber" ,phoneNumber);
            intent1.putExtra("groupId" ,groupid);
            intent1.putExtra("adminId" ,adminId);
            int messageId = (Integer) mData.get(position).get(4);
            intent1.putExtra("messageId" ,messageId);
            startActivity(intent1);
        }else {
            Intent intent2 = new Intent(ChatActivity.this ,ToSignActivity.class);
            intent2.putExtra("phoneNumber" ,phoneNumber);
            intent2.putExtra("groupId" ,groupid);
            intent2.putExtra("adminId" ,adminId);
            int messageId = (Integer) mData.get(position).get(4);
            intent2.putExtra("messageId" ,messageId);
            startActivity(intent2);
        }
    }

    /**
     * 聊天内容的长按时间监听
     * @author  cqx
     * create at 2018/5/23 0:19
     *//*
    @Override
    public void onItemLongClick(View itemView, final int position) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ChatActivity.this);
        normalDialog.setMessage("是否删除该的聊天记录？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChatLogRecyclerViewAdapter.removeData(position);
                    }
                });
        normalDialog.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }*/

    @Override
    public void getMessage(TranObject msg) {
        if(msg != null){
            switch (msg.getType()){
                /*获取聊天记录*/
                case GET_GROUP_MESSAGE:
                    if(msg.isSuccess()){
                        ArrayList<GroupMessage> groupMessageArrayList = msg.getGroupMessageArrayList();
                        if(reStart){
                            mData.clear();
                            reStart = false;
                        }
                        for(int i = groupMessageArrayList.size()-1 ;i>=0;i--){
                            Map<Integer ,Object> map = new HashMap <Integer, Object>();
                            map.put(1,groupMessageArrayList.get(i).getFromId());
                            map.put(2,groupMessageArrayList.get(i).getContentType());
                            map.put(3,groupMessageArrayList.get(i).getContent());
                            map.put(4,groupMessageArrayList.get(i).getMessageId());
                            mData.add(map);
                        }
                        mChatLogRecyclerViewAdapter.notifyDataSetChanged();
                        scrollToLastItem();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    break;
                /*发送聊天记录给服务器端*/
                case SEND_GROUP_MESSAGE:
                    //从服务器端推送过来的消息
                    if(msg.isSuccess()){
                        if(msg.getSendGroupMessage().getGroupId() == groupid){
                            GroupMessage groupMessage = msg.getSendGroupMessage();
                            Map<Integer ,Object> map = new HashMap <Integer, Object>();
                            map.put(1,groupMessage.getFromId());
                            map.put(2,groupMessage.getContentType());
                            map.put(3,groupMessage.getContent());
                            map.put(4,groupMessage.getMessageId());
                            mData.add(map);
                            mChatLogRecyclerViewAdapter.notifyDataSetChanged();
                            scrollToLastItem();
                        }else {

                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
