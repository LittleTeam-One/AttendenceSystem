package com.example.mrc.attendencesystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.entity.FriendsItem;

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
        holder.mOnlineStatus.setText(friendsItem.getOnlineStatus());
    }


    @Override
    public int getItemCount() {
        return mFriendsListData.size();
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
        }
    }
}
