package com.example.mrc.campus_social.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.entity.Group;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Mr.C on 2018/5/15.
 */

public class GroupsRecyclerViewAdapter extends RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    List<Group> mGroupsItemData;

    public GroupsRecyclerViewAdapter(Context context ,List<Group> groupsItemsData){
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
        Group groupsItem = mGroupsItemData.get(position);
        //holder.mImageIcon.setImageDrawable();
        holder.mUserName.setText(groupsItem.getGroupName());
        Calendar calendar = Calendar.getInstance();
        String y = String.valueOf(calendar.get(Calendar.YEAR));
        String m = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String d = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if((calendar.get(Calendar.MONTH) + 1)<10){
            m = "0" +m;
        }
        if(calendar.get(Calendar.DAY_OF_MONTH)<10){
            d = "0" + d;
        }
        holder.mActiveTime.setText(y+"." +m +"." + d);
    }


    @Override
    public int getItemCount() {
        return mGroupsItemData.size();
    }

    /*
    * 点击事件的接口，方法
    * */
    private OnItemClickListenerGroup listener;

    public interface OnItemClickListenerGroup {
        void onItemClickGroup(View itemView, int position);
    }
    public void setOnItemClickListenerGroup(OnItemClickListenerGroup listener) {
        this.listener = listener;
    }

    /*
    * 长按事件的接口，方法
    * */
    private OnItemLongClickListenerGroup longListener;

    public interface OnItemLongClickListenerGroup{
        void onItemLongClickGroup(View itemView, int position);
    }
    public void setOnItemLongClickListenerGroup(OnItemLongClickListenerGroup longListener) {
        this.longListener = longListener;
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

            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickGroup(view, position);
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
                            longListener.onItemLongClickGroup(viewHolder, position);
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
        mGroupsItemData.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
