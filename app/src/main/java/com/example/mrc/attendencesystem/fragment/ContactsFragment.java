package com.example.mrc.attendencesystem.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.activity.AddFriendsOrGroupsActivity;
import com.example.mrc.attendencesystem.activity.ChatActivity;
import com.example.mrc.attendencesystem.activity.CreateGroupsActivity;
import com.example.mrc.attendencesystem.activity.SearchFriendsAndGroupActivity;
import com.example.mrc.attendencesystem.adapter.FriendsRecyclerViewAdapter;
import com.example.mrc.attendencesystem.adapter.GroupsRecyclerViewAdapter;
import com.example.mrc.attendencesystem.entity.FriendsItem;
import com.example.mrc.attendencesystem.entity.GroupsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.C on 2018/5/7.
 */

public class ContactsFragment extends Fragment implements View.OnClickListener,
        FriendsRecyclerViewAdapter.OnItemClickListener, FriendsRecyclerViewAdapter.OnItemLongClickListener,
        GroupsRecyclerViewAdapter.OnItemClickListenerGroup, GroupsRecyclerViewAdapter.OnItemLongClickListenerGroup{
    static Context mContext;
    private LinearLayout mTvAddFriendsAndGroups ,mTvCreateGroup;
    private LinearLayout mLLFriends ,mLLGroups;
    private RecyclerView mRecyclerViewFriends ,mRecyclerViewGroups;
    private ImageView mImgSearchBox;
    private FriendsRecyclerViewAdapter mFriendsRecyclerViewAdapter;
    private GroupsRecyclerViewAdapter mGroupsRecyclerViewAdapter;
    private LinearLayoutManager mFriendsLinearLayoutManager ,mGroupsLinearLayoutManager;
    private List<FriendsItem> mFriendsData =new ArrayList<>();
    private List<GroupsItem> mGroupsData =new ArrayList<>();

    public static ContactsFragment newInstance(String param1 , Context context) {
        ContactsFragment fragment = new ContactsFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ContactsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        Bundle bundle = getArguments();
        findView(view);
        init();
        setListener();
        return view;
    }

    void findView(View view){
        mTvAddFriendsAndGroups = (LinearLayout) view.findViewById(R.id.ll_add_friends_and_groups);
        mTvCreateGroup = (LinearLayout) view.findViewById(R.id.ll_create_group);
        mLLFriends =(LinearLayout) view.findViewById(R.id.ll_friends);
        mLLGroups =(LinearLayout) view.findViewById(R.id.ll_groups);
        mRecyclerViewFriends =(RecyclerView)view.findViewById(R.id.recyclerView_friends);
        mRecyclerViewGroups =(RecyclerView)view.findViewById(R.id.recyclerView_groups);
        mImgSearchBox =(ImageView)view.findViewById(R.id.img_search_box);
    }

    void init(){
        mFriendsLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewFriends.setLayoutManager(mFriendsLinearLayoutManager);

        mGroupsLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewGroups.setLayoutManager(mGroupsLinearLayoutManager);
        mFriendsData.clear();
        for(int i = 1;i <= 15 ;i++){
            FriendsItem friendsItem =new FriendsItem();
            friendsItem.setUsername("周玉欣"+i);
            friendsItem.setOnlineStatus("在线");
            friendsItem.setImgSrc("");
            mFriendsData.add(friendsItem);
        }

        mGroupsData.clear();
        for(int i = 1;i <= 15 ;i++){
            GroupsItem groupsItem =new GroupsItem();
            groupsItem.setUsername("周玉欣"+i);
            groupsItem.setActiveTime("星期一");
            groupsItem.setImgSrc("");
            mGroupsData.add(groupsItem);
        }
        mGroupsRecyclerViewAdapter = new GroupsRecyclerViewAdapter(mContext , mGroupsData);
        mRecyclerViewGroups.setAdapter(mGroupsRecyclerViewAdapter);
    }

    void setListener(){
        mTvAddFriendsAndGroups.setOnClickListener(this);
        mTvCreateGroup.setOnClickListener(this);
        mLLFriends.setOnClickListener(this);
        mLLGroups.setOnClickListener(this);
        mRecyclerViewFriends.setOnClickListener(this);
        mRecyclerViewGroups.setOnClickListener(this);
        mImgSearchBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_add_friends_and_groups:
                Intent intentToAddFriendsAndGroups =new Intent(mContext , AddFriendsOrGroupsActivity.class);
                startActivity(intentToAddFriendsAndGroups);
                break;
            case R.id.ll_create_group:
                Intent intentToCreateGroups =new Intent(mContext , CreateGroupsActivity.class);
                startActivity(intentToCreateGroups);
                break;
            case R.id.ll_friends:
                if(mRecyclerViewFriends.getVisibility() == View.VISIBLE){
                    mRecyclerViewFriends.setVisibility(View.GONE);
                }else {
                    mRecyclerViewFriends.setVisibility(View.VISIBLE);
                    //初始化好友列表
                    mFriendsRecyclerViewAdapter = new FriendsRecyclerViewAdapter(mContext , mFriendsData);
                    mFriendsRecyclerViewAdapter.setOnItemClickListener(this);
                    mFriendsRecyclerViewAdapter.setOnItemLongClickListener(this);
                    mRecyclerViewFriends.setAdapter(mFriendsRecyclerViewAdapter);
                }
                break;
            case R.id.ll_groups:
                if(mRecyclerViewGroups.getVisibility() == View.VISIBLE){
                    mRecyclerViewGroups.setVisibility(View.GONE);
                }else {
                    mRecyclerViewGroups.setVisibility(View.VISIBLE);
                    //初始化群组列表
                    mGroupsRecyclerViewAdapter = new GroupsRecyclerViewAdapter(mContext , mGroupsData);
                    mGroupsRecyclerViewAdapter.setOnItemClickListenerGroup(this);
                    mGroupsRecyclerViewAdapter.setOnItemLongClickListenerGroup(this);
                    mRecyclerViewGroups.setAdapter(mGroupsRecyclerViewAdapter);
                }
                break;
            /*case R.id.recyclerView_friends:

                break;
            case R.id.recyclerView_groups:

                break;*/
            case R.id.img_search_box:
                Intent intentToSearch =new Intent(mContext , SearchFriendsAndGroupActivity.class);
                startActivity(intentToSearch);
                break;
            default:
                break;
        }
    }

    /**
     * 联系人列表的点击事件监听
     * @author  cqx
     * create at 2018/5/23 0:58
     */
    @Override
    public void onItemClickGroup(View itemView, int position) {
        Intent toChatIntent = new Intent(mContext , ChatActivity.class);
        startActivity(toChatIntent);
    }

    /**
     * 联系人列表的长按事件监听
     * @author  cqx
     * create at 2018/5/23 0:59
     */
    @Override
    public void onItemLongClickGroup(View itemView, final int position) {
        String item = mFriendsData.get(position).getUsername();
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        normalDialog.setMessage("是否删除聊天群 " + item + " ？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGroupsRecyclerViewAdapter.removeData(position);
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

    /**
     * 群组列表的点击事件监听
     * @author  cqx
     * create at 2018/5/23 1:12
     */
    @Override
    public void onItemClick(View itemView, int position) {
        Intent toChatIntent = new Intent(mContext , ChatActivity.class);
        startActivity(toChatIntent);
    }

    /**
     * 群组列表的长按事件监听
     * @author  cqx
     * create at 2018/5/23 1:13
     */
    @Override
    public void onItemLongClick(View itemView, final int position) {
        String item = mGroupsData.get(position).getUsername();
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        normalDialog.setMessage("是否删除联系人 " + item + " ？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFriendsRecyclerViewAdapter.removeData(position);
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
}
