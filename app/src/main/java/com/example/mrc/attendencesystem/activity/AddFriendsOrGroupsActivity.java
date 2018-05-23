package com.example.mrc.attendencesystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.fragment.MessageFragment;

public class AddFriendsOrGroupsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, FloatingSearchView.OnHomeActionClickListener {
    RadioGroup mRg;
    RadioButton mRbAddFriends, mRbAddGroups;
    com.arlib.floatingsearchview.FloatingSearchView mSearchView;
    boolean mIsCheckAddFriends = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_or_groups);

        findView();
        init();
    }

    void findView(){
        mRg = (RadioGroup)findViewById(R.id.rg);
        mRbAddFriends = (RadioButton)findViewById(R.id.add_friends);
        mRbAddGroups = (RadioButton)findViewById(R.id.add_groups);
        mRg.setOnCheckedChangeListener(this);
        mSearchView = (com.arlib.floatingsearchview.FloatingSearchView)findViewById(R.id.search_view);
        mSearchView.setOnHomeActionClickListener(this);
    }

    void init(){
        Intent intent = getIntent();
        mIsCheckAddFriends = intent.getBooleanExtra(MessageFragment.IS_ADD_FRIENDS ,true);
        if(mIsCheckAddFriends){
            mRbAddFriends.setChecked(true);
            mRbAddGroups.setChecked(false);
        }else {
            mRbAddFriends.setChecked(false);
            mRbAddGroups.setChecked(true);
        }

    }

    /**
     * radioButton的监听事件
     * @author  cqx
     * create at 2018/5/23 10:31
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.add_friends :
                mIsCheckAddFriends = true;
                break;
            case R.id.add_groups :
                mIsCheckAddFriends = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void onHomeClicked() {
        finish();
    }
}
