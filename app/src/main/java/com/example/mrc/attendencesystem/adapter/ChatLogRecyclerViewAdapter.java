package com.example.mrc.attendencesystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.attendencesystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.C on 2018/5/22.
 */

public class ChatLogRecyclerViewAdapter extends RecyclerView.Adapter<ChatLogRecyclerViewAdapter.ViewHolder>{
    private List<String> mData =new ArrayList<>();
    private Context mContext;
    public static final int VIEW_TYPE_OTHERS = 1;
    public static final int VIEW_TYPE_MINE = 2;
    private LayoutInflater mInflater;

    public ChatLogRecyclerViewAdapter(Context context , List<String> list){
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
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatLogRecyclerViewAdapter.ViewHolder holder, int position) {
        if(mData.get(position).length()==1){
            if("1".equals(mData.get(position))){
                holder.mTvChatLog.setText("我要减肥！");
            }else {
                holder.mTvChatLog.setText("你不需要减肥！");
            }
        }else {
            holder.mTvChatLog.setText(mData.get(position).substring(1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if("1".equals(mData.get(position).substring(0, 1))){
            return VIEW_TYPE_OTHERS;
        }else {
            return VIEW_TYPE_MINE;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName ,mTvChatLog;
        public ImageView mImgHeadIcon;
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

    /*
    * 长按事件的接口，方法
    * */
    private OnItemLongClickListener longListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longListener) {
        this.longListener = longListener;
    }

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

            mTvChatLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(view, position);
                        }
                    }
                }
            });

            mTvChatLog.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (longListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            longListener.onItemLongClick(viewHolder, position);
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public class HolderTwo extends ViewHolder{

        public HolderTwo(final View viewHolder) {
            super(viewHolder);
            mTvName =(TextView)viewHolder.findViewById(R.id.tv_my_name);
            mTvChatLog =(TextView)viewHolder.findViewById(R.id.my_chat_log);
            mImgHeadIcon =(ImageView) viewHolder.findViewById(R.id.img_my_head_icon);

            mTvChatLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(view, position);
                        }
                    }
                }
            });
            mTvChatLog.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (longListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            longListener.onItemLongClick(viewHolder, position);
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
