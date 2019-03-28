package com.example.mrc.attendencesystem.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private EditText mUsernameEdit;//手机号
    /*private EditText mEmailEdit;*/
    private AppCompatButton mSendVertiCodeBtn;//发送验证码按钮
    private AppCompatButton mRegisterBtn;//注册按钮
    private EditText mPhoneVertifiEdit;//输入验证码
    private EditText mPassword;//密码
    private EditText mAgainPassword;//重新输入密码
    private EditText mStudentNumber;
    private String mPhoneNumber;
    private boolean mCanRegister = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mUsernameEdit = findViewById(R.id.username_editText);
        mSendVertiCodeBtn = findViewById(R.id.send_ic_btn);
        mRegisterBtn = findViewById(R.id.register_btn);
        mPhoneVertifiEdit = findViewById(R.id.email_ic_editText);
        /*mEmailEdit = findViewById(R.id.email_editText);*/
        mPassword = findViewById(R.id.password_editText);
        mAgainPassword = findViewById(R.id.password_again_editText);
        mStudentNumber = findViewById(R.id.student_number_editText);
        setListeners();
    }

    public void setListeners(){
        mSendVertiCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*SMSSDK.registerEventHandler(new EventHandler(){
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
            }*/
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String email = mEmailEdit.getText().toString();*/
                String studentNumber = mStudentNumber.getText().toString();
                String password = mPassword.getText().toString();
                String againPassword = mAgainPassword.getText().toString();
                mPhoneNumber = mUsernameEdit.getText().toString();
                boolean inputIsOk = validateInput(mPhoneNumber,password,againPassword ,studentNumber);
                String phoneVertifiCode = mPhoneVertifiEdit.getText().toString();
                verifyServer();   //与服务端进行验证
                /*if(!TextUtils.isEmpty(phoneVertifiCode) && inputIsOk){
                    SMSSDK.unregisterAllEventHandler();
                    SMSSDK.registerEventHandler(new EventHandler(){
                        @Override
                        public void afterEvent(int i, int i1, Object o) {
                            super.afterEvent(i, i1, o);
                            if(i1 == SMSSDK.RESULT_COMPLETE){
                                //验证码成功之后 此为子线程 需要在UI线程或Handler更新UI
                                verifyServer();   //与服务端进行验证
                            }
                        }
                    });
                }else {
                    Toast.makeText(mContext,getString(R.string.please_input_vertifi_code),Toast.LENGTH_SHORT)
                            .show();
                }
                SMSSDK.submitVerificationCode("86",mPhoneNumber,phoneVertifiCode);*/
            }
        });
    }

    /**
     * 注册字段的正则验证
     * @param userName
     * @param passWord
     * @param passwordAgain
     * @return
     */
    private boolean validateInput(String userName, String passWord, String passwordAgain ,String studentNumber) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(RegisterActivity.this,getString(R.string.username_can_not_be_null),Toast.LENGTH_SHORT).show();
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
        } else if(TextUtils.isEmpty(studentNumber)){
            Toast.makeText(RegisterActivity.this,getString(R.string.student_number_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else{
            return true;
        }
    }

    /**
     * 判断是否为正确的手机号
     * @param phone 手机号
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
        //SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void verifyServer(){
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                User user=new User();
                user.setId(-1);
                user.setPhoneNumber(mUsernameEdit.getText().toString());
                user.setPassword(mPassword.getText().toString());
                user.setUserName("");
                user.setGender(-1);
                user.setCreateTime("");
                user.setUpdateTime("");
                user.setAge(-1);
                user.setStudentId(mStudentNumber.getText().toString());
                user.setOperation("register");
                //boolean b = new AttendenceSystemClient(RegisterActivity.this).sendRegisterInfo(user);
                if(true){
                    //注册成功跳转到登陆
                    Handler handlerThree=new Handler(Looper.getMainLooper());
                    handlerThree.post(new Runnable(){
                        public void run(){
                            Toast.makeText(RegisterActivity.this, "恭喜你，注册成功 ！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }else {
                    Handler handlerThree=new Handler(Looper.getMainLooper());
                    handlerThree.post(new Runnable(){
                        public void run(){
                            Toast.makeText(RegisterActivity.this, "注册失败，请重新注册！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    mPhoneVertifiEdit.setText(null);
                }
            }
        });
        thread.start();
    }
}
