package com.example.mrc.campus_social.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.fragment.ChatFragment;
import com.example.mrc.campus_social.fragment.CommunityFragment;
import com.example.mrc.campus_social.fragment.MoodFragment;
import com.example.mrc.campus_social.fragment.MyFragment;
import com.example.mrc.campus_social.tool.FragmentControl;

public class MainActivity extends FragmentActivity implements BottomNavigationBar.OnTabSelectedListener {
    BottomNavigationBar bottomNavigationBar;
    private MyFragment mMyFragment;
    private ChatFragment mChatFragment;
    private CommunityFragment mCommunityFragment;
    private MoodFragment mMoodFragment;
    private Toolbar mToolBar;
    public static String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        mPhoneNumber = intent.getStringExtra("phoneNumber");
        setContentView(R.layout.activity_main);
        FragmentManager fm = this.getFragmentManager();
        FragmentControl.getInstance().init(this , fm, this.getWindow().getDecorView());
        findView();  //初始化
    }

    void findView(){
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_chat, "畅聊"))
                .addItem(new BottomNavigationItem(R.drawable.ic_community, "社区"))
                .addItem(new BottomNavigationItem(R.drawable.ic_mood, "心情"))
                .addItem(new BottomNavigationItem(R.drawable.ic_my, "我的"))
                .setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.bottom_tab_active)
                .setMode(BottomNavigationBar.MODE_CLASSIC)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        mChatFragment = ChatFragment.newInstance("畅聊", this);
        FragmentControl.getInstance().setFragment(mChatFragment, false, false);
    }


    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if (mChatFragment == null) {
                    mChatFragment = ChatFragment.newInstance("畅聊" ,this);
                }
                FragmentControl.getInstance().setFragment(mChatFragment, false, false);
                break;
            case 1:
                if (mCommunityFragment == null) {
                    mCommunityFragment = mCommunityFragment.newInstance("社区" ,this);
                }
                FragmentControl.getInstance().setFragment(mCommunityFragment, false, false);
                break;
            case 2:
                if (mMoodFragment == null) {
                    mMoodFragment = MoodFragment.newInstance("心情" ,this);
                }
                FragmentControl.getInstance().setFragment(mMoodFragment, false, false);
                break;

            case 3:
                if (mMyFragment == null) {
                    mMyFragment = MyFragment.newInstance("我的" ,this);
                }
                FragmentControl.getInstance().setFragment(mMyFragment, false, false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
