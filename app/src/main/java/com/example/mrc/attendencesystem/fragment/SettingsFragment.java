package com.example.mrc.attendencesystem.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.activity.LocationActivity;

/**
 * Created by Mr.C on 2018/5/7.
 */

public class SettingsFragment extends Fragment {
    static Context mContext;
    private Button mBtnMap;

    public static SettingsFragment newInstance(String param1 , Context context) {
        SettingsFragment fragment = new SettingsFragment();
        mContext = context;
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Bundle bundle = getArguments();
        mBtnMap = (Button)view.findViewById(R.id.btn_to_map);
        mBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext , LocationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
