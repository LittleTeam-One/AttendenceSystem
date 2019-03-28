package com.example.mrc.attendencesystem.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.ButtonBarLayout;
import android.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.mrc.attendencesystem.R;

import com.example.mrc.attendencesystem.fragment.ContactsFragment;
import com.example.mrc.attendencesystem.fragment.MessageFragment;
import com.example.mrc.attendencesystem.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    BottomNavigationBar bottomNavigationBar;
    //int lastSelectedPosition = 0;
    //private String TAG = MainActivity.class.getSimpleName();
    private MessageFragment mMessageFragment;
    private ContactsFragment mContactsFragment;
    private SettingsFragment mSettingsFragment;
    private Toolbar mToolBar;
    public static String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        mPhoneNumber = intent.getStringExtra("phoneNumber");
        setContentView(R.layout.activity_main);
        findView();  //初始化
    }

    void findView(){
        /*mToolBar= (Toolbar) findViewById(R.id.main_toolbar);
        initToolbar();*/

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_chat, "畅聊"))
                .addItem(new BottomNavigationItem(R.drawable.ic_community, "社区"))
                .addItem(new BottomNavigationItem(R.drawable.ic_mood, "树洞"))
                .addItem(new BottomNavigationItem(R.drawable.ic_my, "我的"))
                .setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.bottom_tab_active)
                .setMode(BottomNavigationBar.MODE_CLASSIC)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mMessageFragment = MessageFragment.newInstance("畅聊" , this);
        transaction.replace(R.id.tb , mMessageFragment);
        transaction.commit();
    }


    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mMessageFragment == null) {
                    mMessageFragment = MessageFragment.newInstance("畅聊" ,this);
                }
                transaction.replace(R.id.tb, mMessageFragment);
                break;
            case 1:
                if (mContactsFragment == null) {
                    mContactsFragment = ContactsFragment.newInstance("社区" ,this ,mPhoneNumber);
                }
                transaction.replace(R.id.tb ,mContactsFragment);
                break;
            case 2:
                if (mSettingsFragment == null) {
                    mSettingsFragment = SettingsFragment.newInstance("树洞" ,this);
                }
                transaction.replace(R.id.tb, mSettingsFragment);
                break;

            case 3:
                if (mSettingsFragment == null) {
                    mSettingsFragment = SettingsFragment.newInstance("我的" ,this);
                }
                transaction.replace(R.id.tb, mSettingsFragment);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
