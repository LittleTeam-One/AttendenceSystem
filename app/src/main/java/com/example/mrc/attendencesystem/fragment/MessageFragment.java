package com.example.mrc.attendencesystem.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.activity.AddFriendsOrGroupsActivity;
import com.example.mrc.attendencesystem.activity.ChatActivity;
import com.example.mrc.attendencesystem.activity.MainActivity;
import com.example.mrc.attendencesystem.activity.SearchFriendsAndGroupActivity;
import com.example.mrc.attendencesystem.adapter.MessageRecyclerViewAdapter;
import com.example.mrc.attendencesystem.entity.MessageItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.C on 2018/5/7.
 */

public class MessageFragment extends Fragment implements MessageRecyclerViewAdapter.OnItemClickListener, MessageRecyclerViewAdapter.OnItemLongClickListener {
    static Context mContext;
    RecyclerView mMessageRecyclerView;
    ImageView mImgSearch ,mImgAdd;
    List<MessageItem> mMessageData = new ArrayList<>();
    MessageRecyclerViewAdapter mMessageRecyclerViewAdapter;
    LinearLayoutManager mLayoutManager;
    LinearLayout mLinearLayoutAddClick;
    TextView mTvAddFriends ,mTvAddGroups;
    public static final String IS_ADD_FRIENDS = "is_add_friends";


    public static MessageFragment newInstance(String param1 , Context context) {
        MessageFragment fragment = new MessageFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        Log.d("cqx1" ,param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        Bundle bundle = getArguments();
        String a = bundle.getString("agrs1","-1");
        Log.d("cqx2",a);
        findView(view);
        init();
        setListener();
        return view;
    }

    void findView(View view){
        mMessageRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_message);
        mImgSearch = (ImageView)view.findViewById(R.id.img_search_box);
        mImgAdd = (ImageView)view.findViewById(R.id.img_right_add);
        mLinearLayoutAddClick = (LinearLayout)view.findViewById(R.id.ll_add_click);
        mTvAddFriends = (TextView)view.findViewById(R.id.tv_right_add_friends);
        mTvAddGroups = (TextView)view.findViewById(R.id.tv_right_add_groups);
    }
    void init(){
        mLayoutManager = new LinearLayoutManager(mContext);
        mMessageRecyclerView.setLayoutManager(mLayoutManager);
        mMessageData.clear();
        MessageItem messageItem =new MessageItem();
        messageItem.setSender("二愣子");
        messageItem.setContent("我要减肥!!!  ");
        messageItem.setLastTime("2018-05-30");
        MessageItem messageItem2 =new MessageItem();
        messageItem.setSender("陈狗蛋");
        messageItem.setContent("急啊哈哈!!!  ");
        messageItem.setLastTime("2018-05-30");
        messageItem.setImgSrc("");
        mMessageData.add(messageItem2);
        mMessageRecyclerViewAdapter = new MessageRecyclerViewAdapter(mContext , mMessageData);
        mMessageRecyclerViewAdapter.setOnItemClickListener(this);
        mMessageRecyclerViewAdapter.setOnItemLongClickListener(this);
        mMessageRecyclerView.setAdapter(mMessageRecyclerViewAdapter);
    }

    void setListener(){
        mImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(mContext , SearchFriendsAndGroupActivity.class);
                startActivity(searchIntent);
            }
        });
        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLinearLayoutAddClick.getVisibility() == View.VISIBLE){
                    mLinearLayoutAddClick.setVisibility(View.GONE);
                }else {
                    mLinearLayoutAddClick.setVisibility(View.VISIBLE);
                    mLinearLayoutAddClick.bringToFront();//显示在最上层
                }
            }
        });

        mTvAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddFriendsIntent = new Intent(mContext , AddFriendsOrGroupsActivity.class);
                toAddFriendsIntent.putExtra(IS_ADD_FRIENDS ,true);
                startActivity(toAddFriendsIntent);
            }
        });

        mTvAddGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddFriendsIntent = new Intent(mContext , AddFriendsOrGroupsActivity.class);
                toAddFriendsIntent.putExtra(IS_ADD_FRIENDS ,false);
                startActivity(toAddFriendsIntent);
            }
        });
    }

    /**
     * 点击事件
     * @author  cqx
     * create at 2018/5/22 22:42
     */
    @Override
    public void onItemClick(View itemView, int position) {
        Intent toChatActivityIntent =new Intent(mContext , ChatActivity.class);
        startActivity(toChatActivityIntent);
    }

    /**
     * 长按事件
     * @author  cqx
     * create at 2018/5/22 22:43
     */
    @Override
    public void onItemLongClick(View itemView, final int position) {
        String item = mMessageData.get(position).getSender();
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        normalDialog.setMessage("是否移除与"+ item + "的聊天？");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMessageRecyclerViewAdapter.removeData(position);
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
