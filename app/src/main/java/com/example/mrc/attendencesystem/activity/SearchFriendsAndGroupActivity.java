package com.example.mrc.attendencesystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.mrc.attendencesystem.AttendenceSystemApplication;
import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.adapter.SearchGroupResultAdapter;
import com.example.mrc.attendencesystem.clientandserver.ClientUtil;
import com.example.mrc.attendencesystem.entity.Group;
import com.example.mrc.attendencesystem.entity.TranObject;

import java.util.ArrayList;

public class SearchFriendsAndGroupActivity extends BaseActivity {
    com.arlib.floatingsearchview.FloatingSearchView mSearchView;
    private RecyclerView mResultView;
    SearchGroupResultAdapter mSearchResultAdapter;
    private ArrayList<Group> mGroupList;
    private AttendenceSystemApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends_and_group);
        application = (AttendenceSystemApplication)getApplicationContext();
        findView();
        setListener();
        init();
    }
    void findView(){
        mSearchView = findViewById(R.id.search_view);
        mResultView = findViewById(R.id.search_result);
    }

    public void init(){
        mGroupList = new ArrayList <>();
        mResultView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mSearchResultAdapter = new SearchGroupResultAdapter(application,mGroupList);
        mResultView.setAdapter(mSearchResultAdapter);
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
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                String groupName = mSearchView.getQuery();
                ClientUtil.searchGroup(groupName,application);
            }
        });
    }

    @Override
    public void getMessage(TranObject msg) {
        if(msg != null){
            switch (msg.getType()){
                case SEARCH_GROUP:
                    if(msg.isSuccess()){
                        mGroupList = msg.getGroupList();
                        mSearchResultAdapter.notifyDataSetChanged();
                    }
                    break;
                case SEND_JOIN_REQUEST:
                    if(msg.isSuccess()){
                        Toast.makeText(application,"加群成功，已在群内",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
