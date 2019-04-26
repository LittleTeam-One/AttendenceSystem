package com.example.mrc.campus_social.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrc.campus_social.R;

import java.util.List;

/**
 * Created by Mr.C on 2018/6/18.
 */

public class GroupNumberRecyclerViewAdapter extends RecyclerView.Adapter<GroupNumberRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    List<String> mGroupMemberItemList;
    public GroupNumberRecyclerViewAdapter(Context context ,List<String> groupMemberItemList){
        mContext = context;
        mGroupMemberItemList = groupMemberItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        mInflater = LayoutInflater.from(mContext);
        vh = new ViewHolder(mInflater.inflate(R.layout.layout_group_item, parent, false));
        return vh;
    }

    @Override
    public int getItemCount() {
        return mGroupMemberItemList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(mGroupMemberItemList.get(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
