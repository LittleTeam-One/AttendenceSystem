package com.example.mrc.campus_social.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrc.campus_social.CampusSocialSystemApplication;
import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.adapter.GroupNumberRecyclerViewAdapter;
import com.example.mrc.campus_social.adapter.GroupSignLogRecyclerViewAdapter;
import com.example.mrc.campus_social.clientandserver.ClientUtil;
import com.example.mrc.campus_social.entity.Group;
import com.example.mrc.campus_social.entity.GroupSignInMessage;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.entity.User;

import java.util.ArrayList;
import java.util.List;

public class GroupInformationActivity extends BaseActivity implements View.OnClickListener,
        GroupSignLogRecyclerViewAdapter.OnItemClickListener{
    TextView mGroupName ,mTvSetSign ,mTvDescription ,
            mTvGroupMember ,mTvGroupSignLog ,mTvGroupApply;
    RecyclerView mRecyclerViewMember ,mRecyclerViewSignLog;
    LinearLayout mLlApply;
    static String phoneNumber;
    static int groupId;
    static String adminId;
    static String groupName;
    List<String> mGroupMemberItemList;
    List<GroupSignInMessage> mGroupSignInLogList;

    boolean openNumber ,openSignLog;
    GroupNumberRecyclerViewAdapter mGroupNumberRecyclerViewAdapter;
    GroupSignLogRecyclerViewAdapter mGroupSignLogRecyclerViewAdapter;
    CampusSocialSystemApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_information);
        application = (CampusSocialSystemApplication)getApplicationContext();
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        groupId = intent.getIntExtra("groupId" ,-1);
        adminId = intent.getStringExtra("adminId");
        groupName = intent.getStringExtra("groupName");
        /*Log.d("gggg" ,phoneNumber);
        Log.d("gggg" ,adminId);*/
        findView();
        init();
    }

    void findView(){
        mGroupName = (TextView) findViewById(R.id.tv_group_name);
        mTvSetSign = (TextView) findViewById(R.id.tv_set_sign);
        mTvDescription = (TextView) findViewById(R.id.tv_description);
        mTvGroupMember = (TextView) findViewById(R.id.tv_group_number);
        mTvGroupSignLog = (TextView) findViewById(R.id.tv_group_sign_log);
        mTvGroupApply = (TextView) findViewById(R.id.tv_group_apply);
        mLlApply = (LinearLayout) findViewById(R.id.ll_apply);
        mRecyclerViewMember = (RecyclerView) findViewById(R.id.recyclerView_member);
        mRecyclerViewSignLog = (RecyclerView) findViewById(R.id.recyclerView_sign_log);

        mTvSetSign.setOnClickListener(this);
        mTvGroupMember.setOnClickListener(this);
        mTvGroupSignLog.setOnClickListener(this);
    }

    void init(){
        mGroupName.setText(groupName);
        mRecyclerViewMember.setVisibility(View.GONE);
        openNumber = false;
        mRecyclerViewSignLog.setVisibility(View.GONE);
        openSignLog = false;
        //if(phoneNumber.equals(adminId)){
        if(adminId.equals(phoneNumber)){   //作为管理员
            mTvSetSign.setVisibility(View.VISIBLE);
            mLlApply.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewMember.setLayoutManager(linearLayoutManager1);
        mGroupMemberItemList = new ArrayList <>();
        mGroupNumberRecyclerViewAdapter = new GroupNumberRecyclerViewAdapter(GroupInformationActivity.this ,mGroupMemberItemList);
        mRecyclerViewMember.setAdapter(mGroupNumberRecyclerViewAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewSignLog.setLayoutManager(linearLayoutManager2);
        mGroupSignInLogList = new ArrayList <>();
        mGroupSignLogRecyclerViewAdapter = new GroupSignLogRecyclerViewAdapter(GroupInformationActivity.this ,mGroupSignInLogList);
        //mGroupSignLogRecyclerViewAdapter.setOnItemClickListener(this);
        mRecyclerViewSignLog.setAdapter(mGroupSignLogRecyclerViewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_set_sign:
                Intent intentToInformation = new Intent(GroupInformationActivity.this ,SetSignActivity.class);
                intentToInformation.putExtra("phoneNumber" ,phoneNumber);
                intentToInformation.putExtra("groupId" ,groupId);
                intentToInformation.putExtra("adminId" ,adminId);
                startActivity(intentToInformation);
                break;
            case R.id.tv_group_number:
                if(openNumber){
                    mRecyclerViewMember.setVisibility(View.GONE);
                    openNumber = false;
                }else {
                    mRecyclerViewMember.setVisibility(View.VISIBLE);
                    Group group = new Group();
                    group.setGroupId(groupId);
                    ClientUtil.getGroupMember(application ,group ,phoneNumber);
                    openNumber = true;
                }
                break;
            case R.id.tv_group_sign_log:
                if(openSignLog){
                    mRecyclerViewSignLog.setVisibility(View.GONE);
                    openSignLog = false;
                }else {
                    mRecyclerViewSignLog.setVisibility(View.VISIBLE);
                    Group group = new Group();
                    group.setGroupId(groupId);
                    ClientUtil.getGroupSignRecord(application ,group ,phoneNumber);
                    openSignLog = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
        if(adminId.equals(phoneNumber)){
            Intent intent = new Intent(GroupInformationActivity.this ,GetSignMessageActivity.class);
            intent.putExtra("groupId" ,groupId);
            intent.putExtra("phoneNumber" ,phoneNumber);
            intent.putExtra("adminId" ,adminId);
            intent.putExtra("messageId" ,mGroupSignInLogList.get(position).getRecordId());
            startActivity(intent);
        }else {
            long timeStamp = System.currentTimeMillis();  //当前时间
            ImageView imgStatus = (ImageView)itemView.findViewById(R.id.img_status);
            if(timeStamp > mGroupSignInLogList.get(position).getEndTime()){
                Intent intent = new Intent(GroupInformationActivity.this ,ToSignActivity.class);
                intent.putExtra("groupId" ,groupId);
                intent.putExtra("phoneNumber" ,phoneNumber);
                intent.putExtra("adminId" ,adminId);
                intent.putExtra("messageId" ,mGroupSignInLogList.get(position).getRecordId());
                startActivity(intent);
            }else {
                Toast.makeText(GroupInformationActivity.this ,"该签到已经失效！" ,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void getMessage(TranObject msg) {
        if(msg != null) {
            switch (msg.getType()) {
                /*获取群的成员*/
                case GET_GROUP_MEMBERS:
                    if (msg.isSuccess()) {
                        mGroupMemberItemList.clear();
                        List <User> groupItemList = msg.getGroupUsers();
                        for (int i = 0; i < groupItemList.size(); i++) {
                            mGroupMemberItemList.add(groupItemList.get(i).getPhoneNumber());
                        }
                        mGroupNumberRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    break;
                /*获取群的所有的发起签到*/
                case GET_GROUP_SIGN_RECORD:
                    if (msg.isSuccess()) {
                        mGroupSignInLogList.clear();
                        mGroupSignInLogList = msg.getSignInfoslist();
                        mGroupSignLogRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
