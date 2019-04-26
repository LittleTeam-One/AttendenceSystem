package com.example.mrc.campus_social.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrc.campus_social.CampusSocialSystemApplication;
import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.clientandserver.ClientUtil;
import com.example.mrc.campus_social.entity.Group;

import java.util.ArrayList;

/**
 * Created by Mr.C on 2018/6/19.
 */

public class SearchGroupResultAdapter extends RecyclerView.Adapter<SearchGroupResultAdapter.ViewHolder> {

    private ArrayList<Group> mGroupList;
    private CampusSocialSystemApplication application;

    public SearchGroupResultAdapter(CampusSocialSystemApplication context, ArrayList<Group> mGroupList) {
        application = context;
        this.mGroupList = mGroupList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_group_item, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.groupName.setText(mGroupList.get(position).getGroupName());
        holder.joinGroup.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mGroupList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView groupName;
        Button joinGroup;
        public ViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            joinGroup = itemView.findViewById(R.id.join_group);
            joinGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClientUtil.sendJoinRequest(mGroupList.get((Integer)joinGroup.getTag()),application);
                }
            });
        }
    }

    private OnItemClickListener listener;

    private interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
