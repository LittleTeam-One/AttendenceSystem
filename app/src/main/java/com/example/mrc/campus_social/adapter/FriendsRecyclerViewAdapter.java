package com.example.mrc.campus_social.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.entity.FriendsItem;

import java.util.List;

/**
 * Created by Mr.C on 2018/5/15.
 */

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder>{
    List<FriendsItem> mFriendsListData;
    Context mContext;
    private LayoutInflater mInflater;
    
    public FriendsRecyclerViewAdapter(Context context , List<FriendsItem> friendsListData){
        mFriendsListData = friendsListData;
        mContext = context;
    }


    @Override
    public FriendsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        mInflater = LayoutInflater.from(mContext);
        vh = new ViewHolder(mInflater.inflate(R.layout.layout_friends_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendsRecyclerViewAdapter.ViewHolder holder, int position) {
        FriendsItem friendsItem = mFriendsListData.get(position);
        holder.mUserName.setText(friendsItem.getUsername());
        String status = "离线";
        if (friendsItem.getOnlineStatus() == 1) {
            status = "在线";
        }
        holder.mOnlineStatus.setText(status);
    }


    @Override
    public int getItemCount() {
        return mFriendsListData.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView mImageIcon;
        public TextView mUserName;
        public TextView mOnlineStatus;
        public ViewHolder(final View viewHolder) {
            super(viewHolder);
            mImageIcon = (ImageView) viewHolder.findViewById(R.id.img_icon);
            mUserName = (TextView) viewHolder.findViewById(R.id.tv_username);
            mOnlineStatus = (TextView) viewHolder.findViewById(R.id.tv_online_status);

            viewHolder.setOnClickListener(new View.OnClickListener() {
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
            viewHolder.setOnLongClickListener(new View.OnLongClickListener() {
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

    //  删除数据
    public void removeData(int position) {
        mFriendsListData.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
