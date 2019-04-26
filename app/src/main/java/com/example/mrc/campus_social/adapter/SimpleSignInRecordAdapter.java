package com.example.mrc.campus_social.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.entity.GroupSignInMessage;
import com.example.mrc.campus_social.provider.TimeTransform;

import java.util.List;

/**
 * Created by Mr.C on 2018/6/19.
 */

public class SimpleSignInRecordAdapter extends RecyclerView.Adapter<SimpleSignInRecordAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    List<GroupSignInMessage> mGroupSignData;

    public SimpleSignInRecordAdapter(Context context ,List<GroupSignInMessage> groupSignData){
        mContext = context;
        mGroupSignData = groupSignData;
    }

    @Override
    public SimpleSignInRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        mInflater = LayoutInflater.from(mContext);
        vh = new ViewHolder(mInflater.inflate(R.layout.layout_sign_record_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(SimpleSignInRecordAdapter.ViewHolder holder, int position) {
        GroupSignInMessage groupSignInMessage = mGroupSignData.get(position);
        holder.mTvName.setText(groupSignInMessage.getReceiverId());
        String time = TimeTransform.stampToTime(groupSignInMessage.getStartTime());
        holder.mTvTime.setText(time);
        if(groupSignInMessage.getResult() == 1){
            holder.mImgResult.setImageResource(R.drawable.ic_success);
        }else if(groupSignInMessage.getResult() == 0){
            holder.mImgResult.setImageResource(R.drawable.ic_fail);
        }
    }

    @Override
    public int getItemCount() {
        return mGroupSignData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgResult;
        TextView mTvTime;
        TextView mTvName;
        public ViewHolder(View itemView) {
            super(itemView);
            mImgResult = (ImageView) itemView.findViewById(R.id.img_result);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvName = (TextView)itemView.findViewById(R.id.tv_name);
        }
    }
}
