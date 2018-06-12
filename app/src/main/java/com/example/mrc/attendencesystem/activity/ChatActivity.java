package com.example.mrc.attendencesystem.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.mrc.attendencesystem.entity.Group;
import com.example.mrc.attendencesystem.entity.TranObject;
import com.example.mrc.attendencesystem.fragment.ContactsFragment;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity implements View.OnClickListener, TextWatcher,
        ChatLogRecyclerViewAdapter.OnItemClickListener, ChatLogRecyclerViewAdapter.OnItemLongClickListener{
    ImageView mImgTurnBack ,mImgInformation ,mImgLocation ,mImgSetLocation;
    TextView mTvName ;
    EditText mEtSendMessage;
    RecyclerView mChatLogRecyclerView;
    Button mBtnSendMessage;
    LinearLayoutManager mLayoutManager;
    ChatLogRecyclerViewAdapter mChatLogRecyclerViewAdapter;
    public static Context mContext;

    private RelativeLayout root_view;
    private int screenHeight = 0;
    private int keyHeight = 0;
    private Group group;
    private static int type;
    private AttendenceSystemApplication application;

    List<String> mData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        application = (AttendenceSystemApplication)getApplicationContext();
        Intent intent = getIntent();
        type = intent.getIntExtra("type" ,-1);
        if(type == 1){
            group = ContactsFragment.groupClick;
        }else if(type == 2){

        }else if(type == -1){
            finish();
        }
        mContext =this;
        findView();
        init();
    }

    void findView(){
        mImgTurnBack = (ImageView)findViewById(R.id.img_turn_back);
        mImgInformation = (ImageView) findViewById(R.id.img_information);
        mImgLocation = (ImageView)findViewById(R.id.img_location);
        mImgSetLocation = (ImageView)findViewById(R.id.img_set_location);

        mTvName = (TextView)findViewById(R.id.tv_name);
        mEtSendMessage = (EditText)findViewById(R.id.et_send_message);
        mChatLogRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_chat_log);
        mBtnSendMessage = (Button)findViewById(R.id.btn_send_message);

        root_view = (RelativeLayout) findViewById(R.id.root_view);
    }

    void init(){
        mImgTurnBack.setOnClickListener(this);
        mImgInformation.setOnClickListener(this);
        mImgLocation.setOnClickListener(this);
        mImgSetLocation.setOnClickListener(this);

        //mTvName = (TextView)findViewById(R.id.tv_name);
        mEtSendMessage.addTextChangedListener(this);
        //mChatLogRecyclerView.;
        mBtnSendMessage.setOnClickListener(this);
        mBtnSendMessage.setClickable(false);
        mLayoutManager = new LinearLayoutManager(mContext);
        mChatLogRecyclerView.setLayoutManager(mLayoutManager);
        if(type == 1){
            ClientUtil.getGroupChatRecord(group.getGroupId(),application);
        }else if(type == 2){

        }

        /*mData.clear();
        for(int i = 0;i<15;i++){
            if(i%2 ==0)
                mData.add(String.valueOf(1));
            else
                mData.add(String.valueOf(2));
        }
        mChatLogRecyclerViewAdapter = new ChatLogRecyclerViewAdapter(mContext , mData);
        mChatLogRecyclerViewAdapter.setOnItemClickListener(this);
        mChatLogRecyclerViewAdapter.setOnItemLongClickListener(this);
        mChatLogRecyclerView.setAdapter(mChatLogRecyclerViewAdapter);*/
        scrollToLastItem();
        initListener();
        initOtherData();
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

                break;
            case R.id.img_location :
                Intent toLocationIntent = new Intent(ChatActivity.this ,LocationActivity.class);
                startActivity(toLocationIntent);
                break;
            case R.id.img_set_location :
                Intent toSetLocationIntent = new Intent(ChatActivity.this ,LocationActivity.class);
                startActivity(toSetLocationIntent);
                break;
            case R.id.btn_send_message :
                mData.add("2" + mEtSendMessage.getText());
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
    }

    /**
     * 聊天内容的长按时间监听
     * @author  cqx
     * create at 2018/5/23 0:19
     */
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
    }

    @Override
    public void getMessage(TranObject msg) {
        if(msg != null){

        }
    }
}
