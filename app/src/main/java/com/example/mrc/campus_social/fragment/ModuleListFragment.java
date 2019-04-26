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
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.provider.ModelType;
import com.example.mrc.campus_social.tool.FragmentControl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModuleListFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.send_post)
    ImageView mSendPost;
    @BindView(R.id.title)
    TextView mTitle;

    static String mTag;

    public static ModuleListFragment newInstance(String param1 , Context context) {
        ModuleListFragment fragment = new ModuleListFragment();
        mContext = context;
        mTag = param1;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        Log.d("cqx1" ,param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ModuleListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_module_list, container, false);
        ButterKnife.bind(this ,view);
        mBack.setOnClickListener(this);
        mSendPost.setOnClickListener(this);
        mTitle.setText(mTag);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
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
            case R.id.back :
                FragmentControl.getInstance().getmFragmentManager().popBackStack();
                break;
            case R.id.send_post :
                SendPostFragment sendPostFragment = SendPostFragment.newInstance(ModelType.TYPE_MODULES + ":" + mTag, mContext);
                FragmentControl.getInstance().setFragment(sendPostFragment, true, true);
                break;

            default:
                break;
        }
    }
}
