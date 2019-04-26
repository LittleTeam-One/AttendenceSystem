package com.example.mrc.campus_social.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrc.campus_social.R;

/**
 * Created by Mr.C on 2018/5/7.
 */

public class MyFragment extends Fragment {
    static Context mContext;

    public static MyFragment newInstance(String param1 , Context context) {
        MyFragment fragment = new MyFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }
}
