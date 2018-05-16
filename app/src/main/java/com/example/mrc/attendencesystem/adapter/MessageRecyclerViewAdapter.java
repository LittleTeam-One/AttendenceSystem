package com.example.mrc.attendencesystem.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.entity.MessageItem;

import java.util.List;

import static com.example.mrc.attendencesystem.R.drawable.ic_head_icon;

/**
 * Created by Mr.C on 2018/5/10.
 */

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    List<MessageItem> mMessageData;

    public MessageRecyclerViewAdapter(Context context , List<MessageItem> mMessageData) {
        this.mContext=context;
        this.mMessageData= mMessageData;
    }

    @Override
    public MessageRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder  vh = null;
        mInflater = LayoutInflater.from(mContext);
        vh = new ViewHolder(mInflater.inflate(R.layout.message_list_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(MessageRecyclerViewAdapter.ViewHolder holder, int position) {
        MessageItem message = mMessageData.get(position);
        //holder.mImageIcon.setImageDrawable();
        holder.mUserName.setText(message.getSender());
        holder.mTime.setText(message.getLastTime());
        holder.mChat.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return mMessageData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView mImageIcon;
        public TextView mUserName;
        public TextView mTime;
        public TextView mChat;
        public ViewHolder(final View viewHolder) {
            super(viewHolder);
            mImageIcon = (ImageView) viewHolder.findViewById(R.id.img_icon);
            mUserName = (TextView) viewHolder.findViewById(R.id.tv_username);
            mTime = (TextView) viewHolder.findViewById(R.id.tv_time);
            mChat = (TextView) viewHolder.findViewById(R.id.tv_chat);
        }
    }
}