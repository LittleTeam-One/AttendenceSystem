package com.example.mrc.campus_social.buildlayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mrc.campus_social.CampusSocialSystemApplication;
import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.activity.AddFriendsOrGroupsActivity;
import com.example.mrc.campus_social.activity.ChatActivity;
import com.example.mrc.campus_social.activity.CreateGroupsActivity;
import com.example.mrc.campus_social.activity.SearchFriendsAndGroupActivity;
import com.example.mrc.campus_social.adapter.FriendsRecyclerViewAdapter;
import com.example.mrc.campus_social.adapter.GroupsRecyclerViewAdapter;
import com.example.mrc.campus_social.clientandserver.ClientUtil;
import com.example.mrc.campus_social.entity.FriendsItem;
import com.example.mrc.campus_social.entity.Group;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.baseinterface.ILayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ContactsLayout extends BaseFrameLayoutCard implements ILayout, View.OnClickListener, FriendsRecyclerViewAdapter.OnItemClickListener, FriendsRecyclerViewAdapter.OnItemLongClickListener, GroupsRecyclerViewAdapter.OnItemClickListenerGroup, GroupsRecyclerViewAdapter.OnItemLongClickListenerGroup {
    static String mPhoneNumber;

    @BindView(R.id.ll_add_friends_and_groups)
    LinearLayout mTvAddFriendsAndGroups;
    @BindView(R.id.ll_create_group)
    LinearLayout mTvCreateGroup;
    @BindView(R.id.ll_friends)
    LinearLayout mLLFriends;
    @BindView(R.id.ll_groups)
    LinearLayout mLLGroups;
    @BindView(R.id.recyclerView_friends)
    RecyclerView mRecyclerViewFriends;
    @BindView(R.id.recyclerView_groups)
    RecyclerView mRecyclerViewGroups;

    private FriendsRecyclerViewAdapter mFriendsRecyclerViewAdapter;
    private GroupsRecyclerViewAdapter mGroupsRecyclerViewAdapter;
    private LinearLayoutManager mFriendsLinearLayoutManager ,mGroupsLinearLayoutManager;
    private static List<FriendsItem> mFriendsData =new ArrayList<>();
    private static List<Group> mGroupsData =new ArrayList<>();
    private SharedPreferences mSharePre;
    private CampusSocialSystemApplication mApplication;

    @Override
    public void getMessage(TranObject msg) {
        if (msg != null) {
            Boolean isOk = msg.isSuccess();
            Log.d("Client", "getMessage: "+ msg);
            switch (msg.getType()) {
                case GET_GROUPS:
                    if(isOk){
                        //splitList(msg.getGroupList());
                        mGroupsData = msg.getGroupList();
                        mGroupsRecyclerViewAdapter = new GroupsRecyclerViewAdapter(getContext() , mGroupsData);
                        mGroupsRecyclerViewAdapter.setOnItemClickListenerGroup(this);
                        mGroupsRecyclerViewAdapter.setOnItemLongClickListenerGroup(this);
                        mRecyclerViewGroups.setAdapter(mGroupsRecyclerViewAdapter);
                    }
                    else {
                        Log.d("Client:get_make_group:","error");
                    }
                    break;

                case GET_FRIENDSHIPS_LIST:
                    if(isOk){
                        mFriendsData = msg.getFriendsList();
                        mFriendsRecyclerViewAdapter = new FriendsRecyclerViewAdapter(getContext() , mFriendsData);
                        mFriendsRecyclerViewAdapter.setOnItemClickListener(this);
                        mFriendsRecyclerViewAdapter.setOnItemLongClickListener(this);
                        mRecyclerViewFriends.setAdapter(mFriendsRecyclerViewAdapter);
                    }
                    else {
                        Log.d("Client:delete_group:","error");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    void setListener(){
        mTvAddFriendsAndGroups.setOnClickListener(this);
        mTvCreateGroup.setOnClickListener(this);
        mLLFriends.setOnClickListener(this);
        mLLGroups.setOnClickListener(this);
    }

    /**
     * 子类直接调用这个方法关闭应用
     */
    @Override
    public void close() {
        super.close();
    }

    public ContactsLayout(@NonNull Context context) {
        this(context, null);
    }

    public ContactsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ContactsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.card_root_contacts, this);
        mSharePre = getContext().getSharedPreferences(CampusSocialSystemApplication.SHARED_PREF,0);
        mApplication = (CampusSocialSystemApplication) getContext().getApplicationContext();
    }

    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     *
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void changeView(Object object) {
        super.changeView(object);
    }

    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void initView() {
        super.initView();
        mFriendsLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewFriends.setLayoutManager(mFriendsLinearLayoutManager);

        mGroupsLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewGroups.setLayoutManager(mGroupsLinearLayoutManager);
        mGroupsRecyclerViewAdapter = new GroupsRecyclerViewAdapter(getContext() , mGroupsData);
        mRecyclerViewGroups.setAdapter(mGroupsRecyclerViewAdapter);
        setListener();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_add_friends_and_groups:
                Intent intentToAddFriendsAndGroups =new Intent(getContext() , AddFriendsOrGroupsActivity.class);
                getContext().startActivity(intentToAddFriendsAndGroups);
                break;
            case R.id.ll_create_group:
                Intent intentToCreateGroups = new Intent(getContext() , CreateGroupsActivity.class);
                getContext().startActivity(intentToCreateGroups);
                break;
            case R.id.ll_friends:
                if(mRecyclerViewFriends.getVisibility() == View.VISIBLE){
                    mRecyclerViewFriends.setVisibility(View.GONE);
                }else {
                    ClientUtil.getFriendShipsList(mSharePre.getString(CampusSocialSystemApplication.USER_PHONE,""),mApplication);
                    mRecyclerViewFriends.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_groups:
                if(mRecyclerViewGroups.getVisibility() == View.VISIBLE){
                    mRecyclerViewGroups.setVisibility(View.GONE);
                }else {
                    mRecyclerViewGroups.setVisibility(View.VISIBLE);
                    //初始化群组列表
                    ClientUtil.getGroups(mSharePre.getString(CampusSocialSystemApplication.USER_PHONE,""),mApplication);
                }
                break;
            case R.id.img_search_box:
                Intent intentToSearch =new Intent(getContext() , SearchFriendsAndGroupActivity.class);
                getContext().startActivity(intentToSearch);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Intent toChatIntent = new Intent(getContext() , ChatActivity.class);
        toChatIntent.putExtra("type", 2);
        toChatIntent.putExtra("friendId", mFriendsData.get(position).getUserId());
        toChatIntent.putExtra("friendName", mFriendsData.get(position).getUsername());
        getContext().startActivity(toChatIntent);
    }

    @Override
    public void onItemLongClick(View itemView, final int position) {
        String item = mFriendsData.get(position).getUsername();
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setMessage("是否删除联系人 " + item + " ？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFriendsRecyclerViewAdapter.removeData(position);
                        // TODO: 2019/3/28  未完成：从数据库删除联系人
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
    public void onItemClickGroup(View itemView, int position) {
        Intent toChatIntent = new Intent(getContext() , ChatActivity.class);
        Group groupClick = mGroupsData.get(position);
        Log.d("dddddd" ,mGroupsData.size()+"");
        int type = 1;      //类型是1表示群聊，2表示好友聊天
        toChatIntent.putExtra("type" ,type);
        toChatIntent.putExtra("groupId" ,groupClick.getGroupId());
        toChatIntent.putExtra("adminId",groupClick.getAdminId());
        toChatIntent.putExtra("groupName",groupClick.getGroupName());
        getContext().startActivity(toChatIntent);
    }

    @Override
    public void onItemLongClickGroup(View itemView, final int position) {
        String item = mGroupsData.get(position).getGroupName();
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
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
}
