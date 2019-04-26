package com.example.mrc.campus_social.tool;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.mrc.campus_social.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentControl {
    //类加载时就初始化
    private static FragmentManager mFragmentManager = null;
    private static Context mContext = null;
    private static List<Fragment> mFragments = new ArrayList<>();
    private static View mRoot = null;
    private boolean mFull = false;
    private View mLine = null;
    private BottomNavigationBar mBottomNavigationBar = null;
    private	static final FragmentControl instance = new FragmentControl();

    private	FragmentControl(){}
    public static FragmentControl getInstance() {
        return instance;
    }
    public void init(Context context, FragmentManager fManager, View root) {
        mContext = context;
        mFragmentManager = fManager;
        mRoot = root;
    }

    public FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    public void setFragment(Fragment fm, boolean full, boolean save) {
        setFullScreen(full);
        setSaveFragment(save ,fm);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.tb, fm);
        //全屏状态的Fragment入栈
        if (full) {
            transaction.addToBackStack(null);
        }
        // 事务提交
        transaction.commit();
    }

    public void setSaveFragment(boolean save, Fragment fm) {
        //不保存即作为第一个Fragment
        if (!save) {
            mFragments.clear();
        }
        mFragments.add(fm);
    }

    //移除最后一个Fragment
    public void removeLastestFragment() {
        if (mFragments.size() > 1) {
            mFragments.remove(mFragments.size()-1);
        }
    }

    //获取List表中最后一个Fragment
    public Fragment getLastestFragment() {
        if (mFragments.size() <= 0) {
            return null;
        }
        return mFragments.get(mFragments.size()-1);
    }

    public void setFullScreen(boolean full) {
        mLine = (View)mRoot.findViewById(R.id.view_line);
        mBottomNavigationBar = (BottomNavigationBar)mRoot.findViewById(R.id.bottom_navigation_bar);
        if (full) {
            mLine.setVisibility(View.GONE);
            mBottomNavigationBar.setVisibility(View.GONE);
        } else {
            mLine.setVisibility(View.VISIBLE);
            mBottomNavigationBar.setVisibility(View.VISIBLE);
        }
    }


}
