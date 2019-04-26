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
import com.example.mrc.campus_social.buildlayout.MoodAllLayout;
import com.example.mrc.campus_social.buildlayout.MoodMineLayout;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.provider.ModelType;
import com.example.mrc.campus_social.tool.FragmentControl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoodFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_title_mine)
    TextView mTvMine;
    @BindView(R.id.tv_title_all)
    TextView mTvAll;
    @BindView(R.id.img_left_send_post)
    ImageView mImgLeftSendPost;
    @BindView(R.id.img_right_comment)
    ImageView mImgRightComment;
    @BindView(R.id.layout_mine)
    MoodMineLayout mMineLayout;
    @BindView(R.id.layout_all)
    MoodAllLayout mAllLayout;

    private static boolean mFirstPager = true;

    public static MoodFragment newInstance(String param1 , Context context) {
        MoodFragment fragment = new MoodFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        Log.d("cqx1" ,param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MoodFragment() {

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
        View view = inflater.inflate(R.layout.fragment_mood, container, false);
        ButterKnife.bind(this, view);
        mTvMine.setOnClickListener(this);
        mTvAll.setOnClickListener(this);
        mImgLeftSendPost.setOnClickListener(this);
        mImgRightComment.setOnClickListener(this);
        disPlayPager(mFirstPager);
        return view;
    }

    private void disPlayPager(boolean first) {
        if (first) {
            mTvMine.setTextColor(getResources().getColor(R.color.white));
            mTvAll.setTextColor(getResources().getColor(R.color.font_color_light_blue));
            mMineLayout.setVisibility(View.VISIBLE);
            mAllLayout.setVisibility(View.GONE);
        } else {
            mTvMine.setTextColor(getResources().getColor(R.color.font_color_light_blue));
            mTvAll.setTextColor(getResources().getColor(R.color.white));
            mMineLayout.setVisibility(View.GONE);
            mAllLayout.setVisibility(View.VISIBLE);
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
            case R.id.tv_title_mine:
                if (!mFirstPager) {
                    mFirstPager = true;
                    disPlayPager(mFirstPager);
                }
                break;
            case R.id.tv_title_all :
                if (mFirstPager) {
                    mFirstPager = false;
                    disPlayPager(mFirstPager);
                }
                break;
            case R.id.img_left_send_post :
                String tag = ModelType.POST_STRING.get(0);
                if (!mFirstPager) {
                    tag = ModelType.POST_STRING.get(1);
                }
                SendPostFragment sendPostFragment = SendPostFragment.newInstance(ModelType.TYPE_MOOD + ":" + tag, mContext);
                FragmentControl.getInstance().setFragment(sendPostFragment, true, true);
                break;
            case R.id.img_right_comment :

                break;
            default :
                break;
        }
    }
}
