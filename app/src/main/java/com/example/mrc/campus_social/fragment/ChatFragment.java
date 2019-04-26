package com.example.mrc.campus_social.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.buildlayout.ChatLayout;
import com.example.mrc.campus_social.buildlayout.ContactsLayout;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.tool.FragmentControl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_title_messages)
    TextView mTvMessages;
    @BindView(R.id.tv_title_contacts)
    TextView mTvContacts;
    @BindView(R.id.img_left_search)
    ImageView mImgLeftSearch;
    @BindView(R.id.img_right_add)
    ImageView mImgRightAdd;
    @BindView(R.id.layout_chat)
    ChatLayout mChatLayout;
    @BindView(R.id.layout_contacts)
    ContactsLayout mContactsLayout;

    private static boolean mFirstPager = true;

    public static ChatFragment newInstance(String param1 , Context context) {
        ChatFragment fragment = new ChatFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        Log.d("cqx1" ,param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ChatFragment() {

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        mTvMessages.setOnClickListener(this);
        mTvContacts.setOnClickListener(this);
        disPlayPager(mFirstPager);
        return view;
    }

    private void disPlayPager(boolean first) {
        if (first) {
            mTvMessages.setTextColor(getResources().getColor(R.color.white));
            mTvContacts.setTextColor(getResources().getColor(R.color.font_color_light_blue));
            mChatLayout.setVisibility(View.VISIBLE);
            mContactsLayout.setVisibility(View.GONE);
        } else {
            mTvMessages.setTextColor(getResources().getColor(R.color.font_color_light_blue));
            mTvContacts.setTextColor(getResources().getColor(R.color.white));
            mChatLayout.setVisibility(View.GONE);
            mContactsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        FragmentControl.getInstance().setFullScreen(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 注销广播
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 子类直接调用这个方法关闭应用
     */
    @Override
    public void close() {
        super.close();
    }

    @Override
    public void getMessage(TranObject msg) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_messages :
                if (!mFirstPager) {
                    mFirstPager = true;
                    disPlayPager(mFirstPager);
                }
                break;
            case R.id.tv_title_contacts :
                if (mFirstPager) {
                    mFirstPager = false;
                    disPlayPager(mFirstPager);
                }
                break;
            default :
                break;
        }
    }
}
