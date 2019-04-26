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
 * Created by Mr.C on 2018/6/18.
 */

public class GroupSignLogRecyclerViewAdapter extends RecyclerView.Adapter<GroupSignLogRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    List<GroupSignInMessage> mGroupSignInLogData;

    public GroupSignLogRecyclerViewAdapter(Context context ,List<GroupSignInMessage> groupSignInLogData){
        mContext = context;
        mGroupSignInLogData = groupSignInLogData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        mInflater = LayoutInflater.from(mContext);
        vh = new ViewHolder(mInflater.inflate(R.layout.layout_group_sign_log_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GroupSignInMessage groupSignInMessage = mGroupSignInLogData.get(position);
        String startTime = TimeTransform.stampToTime(groupSignInMessage.getStartTime());
        String endTime = TimeTransform.stampToTime(groupSignInMessage.getEndTime());
        long timeStamp = System.currentTimeMillis();  //当前时间
        holder.mTvStartTime.setText(startTime);
        holder.mTvEndTime.setText(endTime);
        if(timeStamp > groupSignInMessage.getEndTime()){
            holder.mImgStatus.setImageResource(R.drawable.ic_lose);
        }else {
            holder.mImgStatus.setImageResource(R.drawable.sign_remind);
        }
    }

    @Override
    public int getItemCount() {
        return mGroupSignInLogData.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvStartTime ,mTvEndTime;
        ImageView mImgStatus;
        public ViewHolder(final View itemView) {
            super(itemView);
            mImgStatus = (ImageView) itemView.findViewById(R.id.img_status);
            mTvStartTime = (TextView) itemView.findViewById(R.id.start_date_time);
            mTvEndTime = (TextView) itemView.findViewById(R.id.end_date_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }
}
