package com.example.mrc.attendencesystem.activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrc.attendencesystem.R;
import com.example.mrc.attendencesystem.clientandserver.AttendenceSystemClient;
import com.example.mrc.attendencesystem.clientandserver.ManageClientConServer;
import com.example.mrc.attendencesystem.entity.MessageInfo;
import com.example.mrc.attendencesystem.entity.MessageType;
import com.example.mrc.attendencesystem.entity.UserInfo;
import com.example.mrc.attendencesystem.provider.CodeUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText mUsernameEdit;
    EditText mPasswordEdit;
    EditText mIcEdit;
    AppCompatButton mLoginBtn;
    TextView mStartRegister;
    TextView mForgetPassword;
    ImageView mIcImage;
    private String mIcNumber;                           //自动生成的验证码
    private String username, password, icNumber;        //用户输入的信息
    private String mAppVersion;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        initValues();
        setListener();
    }

    void findView (){
        toolbar =(Toolbar) findViewById(R.id.login_toolbar);
        mUsernameEdit =(EditText)findViewById(R.id.username_editText);
        mPasswordEdit =(EditText)findViewById(R.id.password_editText);
        mIcEdit =(EditText) findViewById(R.id.ic_editText);
        mLoginBtn =(AppCompatButton)findViewById(R.id.login_btn);
        mStartRegister =(TextView)findViewById(R.id.start_register_str);
        mForgetPassword =(TextView)findViewById(R.id.forget_password_str);
        mIcImage =(ImageView)findViewById(R.id.ic_Image);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(0).setTitle(mAppVersion);
        return true;
    }

    private void initValues() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        String appVersion = "";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            appVersion = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String version = String.format(getResources().getString(R.string.app_version), appVersion);
        mAppVersion = version;
        mIcImage.setImageBitmap(CodeUtils.getInstance().createBitmap());
        mIcNumber = CodeUtils.getInstance().getCode();
    }

    /**
     * 添加控件的监听事件，并在点击登录按钮后访问服务器，
     * 判断用户名密码是否正确，若正确，则得到sessionId和其过期时间
     */
    private void setListener() {
        mStartRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mIcImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIcImage.setImageBitmap(CodeUtils.getInstance().createBitmap());
                mIcNumber = CodeUtils.getInstance().getCode();
            }
        });
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mUsernameEdit.getText().toString();
                password = mPasswordEdit.getText().toString();
                icNumber = mIcEdit.getText().toString();
                boolean noFault = validateInput(username, password, icNumber);
                if (noFault) {
                    handler.post(new Runnable(){
                        public void run() {
                            boolean b=login(username, password);
                            if(b){
                                Message m=new Message();
                                m.what=1;
                                handler.sendMessage(m);
                                //转到主界面
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }else {
                                Toast.makeText(LoginActivity.this, "账号和密码不匹配，请重新登录！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 判断输入用户名和密码是否为空
     *
     * @param userName
     * @param passWord
     * @return
     */
    private boolean validateInput(String userName, String passWord, String icNumber) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(LoginActivity.this,getString(R.string.username_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(LoginActivity.this,getString(R.string.password_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(icNumber)) {
            Toast.makeText(LoginActivity.this,getString(R.string.email_ic_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mIcNumber.equals(icNumber)) {
            Toast.makeText(LoginActivity.this,getString(R.string.email_ic_is_not_correct),Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 登录信息发送到服务端验证
     * @author  cqx
     * create at 2018/5/7 10:47
     */
    boolean login(String a, String p){
        UserInfo userInfo=new UserInfo();
        userInfo.setAccount(a);
        userInfo.setPassword(p);
        userInfo.setOperation("login");
        boolean b=new AttendenceSystemClient(this).sendLoginInfo(userInfo);
        //登陆成功
        if(b){
            try {
                //发送一个要求返回在线好友的请求的Message
                ObjectOutputStream oos = new ObjectOutputStream	(
                        ManageClientConServer.getClientConServerThread(userInfo.getAccount()).getS().getOutputStream());
                MessageInfo m=new MessageInfo();
                m.setType(MessageType.GET_ONLINE_FRIENDS);
                m.setSender(userInfo.getAccount());
                oos.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.finish();
            return true;
        }else {
            return false;
        }
    }


    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    dialog.dismiss();
                    break;
            }
        }
    };
}
