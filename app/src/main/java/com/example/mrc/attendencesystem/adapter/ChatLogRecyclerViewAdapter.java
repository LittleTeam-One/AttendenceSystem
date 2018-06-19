package com.example.mrc.attendencesystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.activity.ChatActivity;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.C on 2018/5/22.
 */

public class ChatLogRecyclerViewAdapter extends RecyclerView.Adapter<ChatLogRecyclerViewAdapter.ViewHolder>{
    private List<Map<Integer ,Object>> mData =new ArrayList<>();
    private Context mContext;
    public static final int VIEW_TYPE_OTHERS = 1;
    public static final int VIEW_TYPE_MINE = 2;
    public static final int VIEW_TYPE_SIGN = 3;
    public static final int COMMON_MESSAGE = 1;
    public static final int SIGN_MESSAGE = 2;
    private LayoutInflater mInflater;

    public ChatLogRecyclerViewAdapter(Context context , List<Map<Integer ,Object>> list){
        mContext = context;
        mData = list;
    }
    @Override
    public ChatLogRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChatLogRecyclerViewAdapter.ViewHolder vh = null;
        mInflater = LayoutInflater.from(mContext);
        switch (viewType){
            case VIEW_TYPE_OTHERS :
                vh = new HolderOne(mInflater.inflate(R.layout.layout_chat_log_others, parent, false));
                break;
            case VIEW_TYPE_MINE :
                vh = new HolderTwo(mInflater.inflate(R.layout.layout_chat_log_mine, parent, false));
                break;
            case VIEW_TYPE_SIGN :
                vh = new HolderThree(mInflater.inflate(R.layout.layout_group_sign, parent, false));
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatLogRecyclerViewAdapter.ViewHolder holder, int position) {
        if((Integer) mData.get(position).get(ChatActivity.CONTENT_TYPE) == COMMON_MESSAGE) {    //普通消息
            holder.mTvName.setText((String)mData.get(position).get(ChatActivity.FROM_ID));
            holder.mTvChatLog.setText((String)mData.get(position).get(ChatActivity.CONTENT));
        }else if((Integer) mData.get(position).get(ChatActivity.CONTENT_TYPE)  == SIGN_MESSAGE){        //签到消息
        }
    }

    @Override
    public int getItemViewType(int position) {
        if((Integer)mData.get(position).get(ChatActivity.CONTENT_TYPE) == SIGN_MESSAGE){
            return VIEW_TYPE_SIGN;

        }else {
            if(ChatActivity.phoneNumber.equals((String) mData.get(position).get(ChatActivity.FROM_ID))){
                return VIEW_TYPE_MINE;
            }else {
                return VIEW_TYPE_OTHERS;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName ,mTvChatLog;
        public ImageView mImgHeadIcon;
        public RelativeLayout mLlSign;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /*
    * 点击事件的接口，方法
    * */
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

   /* *//*
    * 长按事件的接口，方法
    * *//*
    private OnItemLongClickListener longListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longListener) {
        this.longListener = longListener;
    }*/

    //  删除数据
    public void removeData(int position) {
        mData.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class HolderOne extends ViewHolder {

        public HolderOne(final View viewHolder) {
            super(viewHolder);
            mTvName= (TextView) viewHolder.findViewById(R.id.tv_others_name);
            mTvChatLog= (TextView) viewHolder.findViewById(R.id.others_chat_log);
            mImgHeadIcon= (ImageView) viewHolder.findViewById(R.id.img_others_head_icon);
        }
    }

    public class HolderTwo extends ViewHolder{

        public HolderTwo(final View viewHolder) {
            super(viewHolder);
            mTvName =(TextView)viewHolder.findViewById(R.id.tv_my_name);
            mTvChatLog =(TextView)viewHolder.findViewById(R.id.my_chat_log);
            mImgHeadIcon =(ImageView) viewHolder.findViewById(R.id.img_my_head_icon);
        }
    }

    public class HolderThree extends ViewHolder{
        public HolderThree(final View viewHolder) {
            super(viewHolder);
            mLlSign = (RelativeLayout)viewHolder.findViewById(R.id.ll_sign);
            mLlSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
    }
}
