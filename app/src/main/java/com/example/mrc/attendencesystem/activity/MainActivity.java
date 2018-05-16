package com.example.mrc.attendencesystem.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();  //初始化
    }

    void findView(){
        /*mToolBar= (Toolbar) findViewById(R.id.main_toolbar);
        initToolbar();*/

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_message, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.ic_contacts, "联系人"))
                .addItem(new BottomNavigationItem(R.drawable.ic_settings, "设置"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mMessageFragment = MessageFragment.newInstance("消息" , this);
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
                    mMessageFragment = MessageFragment.newInstance("消息" ,this);
                }
                transaction.replace(R.id.tb, mMessageFragment);
                break;
            case 1:
                if (mContactsFragment == null) {
                    mContactsFragment = ContactsFragment.newInstance("联系人" ,this);
                }
                transaction.replace(R.id.tb, mContactsFragment);
                break;
            case 2:
                if (mSettingsFragment == null) {
                    mSettingsFragment = SettingsFragment.newInstance("设置" ,this);
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
