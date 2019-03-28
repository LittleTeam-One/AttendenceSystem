package com.example.mrc.attendencesystem.buildlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.adapter.MessageRecyclerViewAdapter;
import com.example.mrc.attendencesystem.entity.MessageItem;
import com.example.mrc.attendencesystem.entity.TranObject;
import com.example.mrc.attendencesystem.tool.BaseFrameLayoutCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatLayout extends BaseFrameLayoutCard implements MessageRecyclerViewAdapter.OnItemClickListener, MessageRecyclerViewAdapter.OnItemLongClickListener {

    @BindView(R.id.recyclerview_chat)
    RecyclerView mRecyclerView;

    List<MessageItem> mMessageData = new ArrayList<>();
    MessageRecyclerViewAdapter mMessageRecyclerViewAdapter;
    LinearLayoutManager mLayoutManager;

    public ChatLayout(@NonNull Context context) {
        this(context, null);
    }

    public ChatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChatLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
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
        init();
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

    @Override
    public void getMessage(TranObject msg) {

    }

    private void init(){
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
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
        mMessageRecyclerViewAdapter = new MessageRecyclerViewAdapter(getContext() , mMessageData);
        mMessageRecyclerViewAdapter.setOnItemClickListener(this);
        mMessageRecyclerViewAdapter.setOnItemLongClickListener(this);
        mRecyclerView.setAdapter(mMessageRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(View itemView, int position) {

    }

    @Override
    public void onItemLongClick(View itemView, int position) {

    }
}
