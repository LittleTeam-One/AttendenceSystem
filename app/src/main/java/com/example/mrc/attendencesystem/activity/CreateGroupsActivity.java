package com.example.mrc.attendencesystem.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrc.attendencesystem.AttendenceSystemApplication;
import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.clientandserver.ClientUtil;
import com.example.mrc.attendencesystem.entity.Group;
import com.example.mrc.attendencesystem.entity.GroupMessage;
import com.example.mrc.attendencesystem.entity.TranObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateGroupsActivity extends BaseActivity{
    EditText mEtGroupName ,mEtGroupIntrouduce;
    Button mBtnCreateGroup;
    AttendenceSystemApplication application;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_groups);
        application = (AttendenceSystemApplication)getApplicationContext();
        sp = getSharedPreferences(AttendenceSystemApplication.SHARED_PREF,0);
        findView();

    }

    void findView(){
        mEtGroupName = (EditText)findViewById(R.id.et_group_name);
        mEtGroupIntrouduce = (EditText)findViewById(R.id.et_group_introduce);
        mBtnCreateGroup = (Button)findViewById(R.id.btn_create_group);

        mBtnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEtGroupName.getText() == null||"".equals(mEtGroupName.getText())){
                    Toast.makeText(CreateGroupsActivity.this ,"群组名称不能为空！" ,Toast.LENGTH_LONG).show();
                }else if(mEtGroupIntrouduce.getText() == null||"".equals(mEtGroupIntrouduce.getText())){
                    Toast.makeText(CreateGroupsActivity.this ,"群组描述不能为空！" ,Toast.LENGTH_LONG).show();
                }else {
                    Group group = new Group();
                    group.setGroupName(mEtGroupName.getText().toString());
                    group.setIntrodue(mEtGroupIntrouduce.getText().toString());
                    String phoneNumber = sp.getString(AttendenceSystemApplication.USER_PHONE ,"");
                    group.setAdminId(phoneNumber);
                    ClientUtil.createGroup(application ,group);
                }
            }
        });
    }

    @Override
    public void getMessage(TranObject msg) {
        if(msg != null) {
            switch (msg.getType()) {
                /*接收创建群组的结果*/
                case ADD_GROUP:
                    if (msg.isSuccess()) {
                        Toast.makeText(CreateGroupsActivity.this ,"创建群组成功！" ,Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        Toast.makeText(CreateGroupsActivity.this ,"创建群组失败！" ,Toast.LENGTH_LONG).show();
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }

    }
}
