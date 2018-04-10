package com.example.mrc.attendencesystem;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends AppCompatActivity {

    private EditText mUsernameEdit;//手机号
    private EditText mEmailEdit;
    private AppCompatButton mSendVertiCodeBtn;//发送验证码按钮
    private AppCompatButton mRegisterBtn;//注册按钮
    private EditText mPhoneVertifiEdit;//输入验证码
    private EditText mPassword;//密码
    private EditText mAgainPassword;//重新输入密码
    private String mPhoneNumber;
    private boolean mCanRegister = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        mUsernameEdit = findViewById(R.id.username_editText);
        mSendVertiCodeBtn = findViewById(R.id.send_ic_btn);
        mRegisterBtn = findViewById(R.id.register_btn);
        mPhoneVertifiEdit = findViewById(R.id.email_ic_editText);
        mEmailEdit = findViewById(R.id.email_editText);
        mPassword = findViewById(R.id.password_editText);
        mAgainPassword = findViewById(R.id.password_again_editText);
        setListeners();
    }

    public void setListeners(){
        mSendVertiCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.registerEventHandler(new EventHandler(){
                    @Override
                    public void afterEvent(int i, int i1, Object o) {
                        super.afterEvent(i, i1, o);
                        if(i1 == SMSSDK.RESULT_COMPLETE){
                            mCanRegister = true;
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mUsernameEdit.setEnabled(false);
                                }
                            });
                        }
                    }
                });
                mPhoneNumber = mUsernameEdit.getText().toString();
                if(!TextUtils.isEmpty(mPhoneNumber) && isPhone(mPhoneNumber)) {
                    SMSSDK.getVerificationCode("86", mPhoneNumber);
                }else {
                    Toast.makeText(mContext,getString(R.string.username_prompt),Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEdit.getText().toString();
                String password = mPassword.getText().toString();
                String againPassword = mAgainPassword.getText().toString();
                validateInput(mPhoneNumber,email,password,againPassword);
                String phoneVertifiCode = mPhoneVertifiEdit.getText().toString();
                if(!TextUtils.isEmpty(phoneVertifiCode)){
                    SMSSDK.unregisterAllEventHandler();
                    SMSSDK.registerEventHandler(new EventHandler(){
                        @Override
                        public void afterEvent(int i, int i1, Object o) {
                            super.afterEvent(i, i1, o);
                            if(i1 == SMSSDK.RESULT_COMPLETE){
                                //验证码成功之后 此为子线程 需要在UI线程或Handler更新UI
                            }
                        }
                    });
                }else {
                    Toast.makeText(mContext,getString(R.string.please_input_vertifi_code),Toast.LENGTH_SHORT)
                            .show();
                }
                SMSSDK.submitVerificationCode("86",mPhoneNumber,phoneVertifiCode);
            }
        });
    }

    /**
     * 注册字段的正则验证
     * @param userName
     * @param email
     * @param passWord
     * @param passwordAgain
     * @return
     */
    private boolean validateInput(String userName, String email, String passWord, String passwordAgain) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(RegisterActivity.this,getString(R.string.username_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this,getString(R.string.email_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(RegisterActivity.this,getString(R.string.password_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(passwordAgain)) {
            Toast.makeText(RegisterActivity.this,getString(R.string.password_again_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordAgain.equals(passWord)) {
            Toast.makeText(RegisterActivity.this,getString(R.string.password_again_is_not_equal_to_password),Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否为正确的手机号
     * @param phone 邮箱
     * @return 返回true为正确手机号格式
     */
    public boolean isPhone(String phone) {
        if (phone == null && phone.equals("")) return false;
        Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");//复杂匹配
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
