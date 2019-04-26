package com.example.mrc.campus_social.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.fragment.CommunityFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostItemRecyclerViewAdapter extends RecyclerView.Adapter<PostItemRecyclerViewAdapter.ViewHolder> {
    private OnItemClickListener listener;
    Context mContext;
    Map<Integer, CommunityFragment.Post> mNews = new HashMap<>();
    String mTag = null;

    public PostItemRecyclerViewAdapter(Context context, Map<Integer, CommunityFragment.Post> news) {
        this(null, context, news);
    }

    public PostItemRecyclerViewAdapter(String tag, Context context, Map<Integer, CommunityFragment.Post> news) {
        mTag = tag;
        mNews = news;
        mContext = context;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.card_item_community, null);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override  instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommunityFragment.Post p = mNews.get(position +1);
        holder.mTitle.setText(p.getTitle());
        holder.mSubTitle.setText(p.getSubTitle());
        holder.mName.setText(p.getName());
        holder.mLike.setText(p.getLike()+"");
        holder.mComment.setText(p.getComment()+"");
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mNews.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.sub_title)
        TextView mSubTitle;
        @BindView(R.id.tv_author)
        TextView mName;
        @BindView(R.id.tv_like)
        TextView mLike;
        @BindView(R.id.tv_comment)
        TextView mComment;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(mTag, view, position);
                        }
                    }
                }
            });
        }
    }

    /*
     * 点击事件的接口，方法
     * */
    public interface OnItemClickListener {
        void onItemClick(String tag, View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
