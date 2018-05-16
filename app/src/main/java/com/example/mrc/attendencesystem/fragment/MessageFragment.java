package com.example.mrc.attendencesystem.fragment;


import android.app.Fragment;
import android.content.Context;
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

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.activity.SearchFriendsAndGroupActivity;
import com.example.mrc.attendencesystem.adapter.MessageRecyclerViewAdapter;
import com.example.mrc.attendencesystem.entity.MessageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.C on 2018/5/7.
 */

public class MessageFragment extends Fragment {
    static Context mContext;
    RecyclerView mMessageRecyclerView;
    ImageView mImgSearch;
    List<MessageItem> mMessageData = new ArrayList<>();
    MessageRecyclerViewAdapter mMessageRecyclerViewAdapter;
    LinearLayoutManager mLayoutManager;


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
    }
    void init(){
        mLayoutManager = new LinearLayoutManager(mContext);
        mMessageRecyclerView.setLayoutManager(mLayoutManager);
        mMessageData.clear();
        for(int i = 1;i <= 15 ;i++){
            MessageItem messageItem =new MessageItem();
            messageItem.setSender("周玉欣"+i);
            messageItem.setContent("我要减肥!!!  "+i);
            messageItem.setLastTime("2018-05-"+i);
            messageItem.setImgSrc("");
            mMessageData.add(messageItem);
        }
        mMessageRecyclerViewAdapter = new MessageRecyclerViewAdapter(mContext , mMessageData);
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
    }
}
