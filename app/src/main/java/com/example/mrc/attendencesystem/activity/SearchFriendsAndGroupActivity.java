package com.example.mrc.attendencesystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.mrc.attendencesystem.R;

public class SearchFriendsAndGroupActivity extends AppCompatActivity {
    com.arlib.floatingsearchview.FloatingSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends_and_group);
        findView();
        setListener();
    }
    void findView(){
        mSearchView = (com.arlib.floatingsearchview.FloatingSearchView)findViewById(R.id.search_view);
    }

    /**
     * 返回左边事件监听
     * @author  cqx
     * create at 2018/5/14 22:30
     */
    void setListener(){
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                /*Intent intent = new Intent(SearchFriendsAndGroupActivity.this ,MainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
    }
}
