package com.example.mrc.attendencesystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.entity.GroupsItem;

import java.util.List;

/**
 * Created by Mr.C on 2018/5/15.
 */

public class GroupsRecyclerViewAdapter extends RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    List<GroupsItem> mGroupsItemData;

    public GroupsRecyclerViewAdapter(Context context ,List<GroupsItem> groupsItemsData){
        mContext = context;
        mGroupsItemData = groupsItemsData;
    }

    @Override
    public GroupsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        mInflater = LayoutInflater.from(mContext);
        vh = new ViewHolder(mInflater.inflate(R.layout.layout_groups_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(GroupsRecyclerViewAdapter.ViewHolder holder, int position) {
        GroupsItem groupsItem = mGroupsItemData.get(position);
        //holder.mImageIcon.setImageDrawable();
        holder.mUserName.setText(groupsItem.getUsername());
        holder.mActiveTime.setText(groupsItem.getActiveTime());
    }


    @Override
    public int getItemCount() {
        return mGroupsItemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView mImageIcon;
        public TextView mUserName;
        public TextView mActiveTime;
        public ViewHolder(final View viewHolder) {
            super(viewHolder);
            mImageIcon = (ImageView) viewHolder.findViewById(R.id.img_icon);
            mUserName = (TextView) viewHolder.findViewById(R.id.tv_username);
            mActiveTime = (TextView) viewHolder.findViewById(R.id.tv_active_time);
        }
    }
}
