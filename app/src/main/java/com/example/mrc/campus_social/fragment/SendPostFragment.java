package com.example.mrc.campus_social.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.adapter.PostTypeAdapter;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.provider.ModelType;
import com.example.mrc.campus_social.tool.FragmentControl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendPostFragment extends BaseFragment implements PostTypeAdapter.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.send_post)
    ImageView mSendPost;
    @BindView(R.id.type)
    RecyclerView mType;
    @BindView(R.id.title)
    EditText mTitle;
    @BindView(R.id.sub_title)
    EditText mSubTitle;

    PostTypeAdapter mPostTypeAdapter;
    static boolean mIsCommunity = true;
    GridLayoutManager mGridLayoutManager;
    Map<Integer, String> mTypeData = new HashMap<>();
    static String mSelected;
    int mSelectInt = 0;


    public static SendPostFragment newInstance(String param1 , Context context) {
        SendPostFragment fragment = new SendPostFragment();
        mContext = context;
        String []arr = param1.split( ":" );
        if (arr[0].equals(ModelType.TYPE_MOOD)) {
            mIsCommunity = false;
        } else {
            mIsCommunity = true;
        }
        mSelected = arr[1];
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        Log.d("cqx1" ,param1);
        fragment.setArguments(args);
        return fragment;
    }

    public SendPostFragment() {
        super();
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
        View view = inflater.inflate(R.layout.fragment_send_post, container, false);
        ButterKnife.bind(this, view);
        initRecycler();
        mBack.setOnClickListener(this);
        return view;
    }

    private void initRecycler() {
        if (mIsCommunity) {
            mTypeData = ModelType.MODEL_STRING;
        } else {
            mTypeData = ModelType.POST_STRING;
        }
        mPostTypeAdapter = new PostTypeAdapter(mSelectInt, "id", mContext, mTypeData);
        mPostTypeAdapter.setOnItemClickListener(this);
        mType.setAdapter(mPostTypeAdapter);
        int spanCount = 4;
        if (!mIsCommunity) {
            spanCount = 2;
            if(mSelected.equals(ModelType.MOOD_ALL_POST)) {
                mSelectInt = 1;
            }
        } else {
            for (int i = 0 ;i< ModelType.MODEL_STRING.size();i++) {
                if (mSelected.equals(ModelType.MODEL_STRING.get(i))) {
                    mSelectInt = i;
                    break;
                }
            }
        }
        mGridLayoutManager = new GridLayoutManager(mContext, spanCount, LinearLayoutManager.VERTICAL, false);
        mType.setLayoutManager(mGridLayoutManager);
        Log.d("cqxCCC" ,mSelectInt + "");
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

    @Override
    public void onItemClick(String tag, View itemView, int position) {
        mSelectInt = position;
        for (int i = 0;i < mType.getChildCount();i++) {
            View viewClick = mType.getChildAt(i);
            TextView textView = (TextView)viewClick.findViewById(R.id.title);
            if (i == position) {
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.post_type_background_down));
            } else {
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.post_type_background));
            }
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            FragmentControl.getInstance().getmFragmentManager().popBackStack();
        }
    }
}
