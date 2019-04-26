package com.example.mrc.campus_social.activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrc.campus_social.CampusSocialSystemApplication;
import com.example.mrc.campus_social.R;
import com.example.mrc.campus_social.clientandserver.ClientUtil;
import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.entity.User;
import com.example.mrc.campus_social.provider.CodeUtils;

public class LoginActivity extends BaseActivity {
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
    CampusSocialSystemApplication mApplication;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApplication = (CampusSocialSystemApplication) this.getApplicationContext();
        sp = getSharedPreferences(CampusSocialSystemApplication.SHARED_PREF,0);
        editor = sp.edit();
        username = sp.getString(CampusSocialSystemApplication.USER_PHONE, "");
        password = sp.getString(CampusSocialSystemApplication.USER_PASSWORD, "");
        startVerify();
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
        /*toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);*/
        String appVersion = "";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            appVersion = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String version = getResources().getString(R.string.app_version);
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
                    startVerify();
                }
            }
        });
    }

    //提交用户名和密码验证登录
    private void  startVerify() {
        //在子线程里运行网络请求
        Thread thread =new Thread(new Runnable(){
            public void run() {
                User user = new User(username,password);
                ClientUtil.checkLogin(user,mApplication);
                /*boolean b=login(username, password);*/
                /*if(true){
                    //转到主界面
                    Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("phoneNumber" ,username);
                    startActivity(intent);
                }else {
                    //子线程不能运行Toast，需要推到子线程里
                    Handler handlerThree=new Handler(Looper.getMainLooper());
                    handlerThree.post(new Runnable(){
                        public void run(){
                            Toast.makeText(LoginActivity.this,
                                    "账号和密码不匹配，请重新登录！", Toast.LENGTH_SHORT).show();
                        }
                    });

                }*/
            }
        });
        thread.start();
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

    @Override
    public void getMessage(TranObject msg) {
        if (msg != null) {

            Log.d("Client", "getMessage: "+ msg);
            switch (msg.getType()) {
                case LOGIN:// LoginActivity只处理登录的消息
                    Boolean isOk = msg.isSuccess();
                    Log.d("Client", "getMessage: "+ isOk);
                    if(msg.isSuccess())//账号密码匹配，允许登录
                    {
                        editor.putString(CampusSocialSystemApplication.USER_PHONE,mUsernameEdit.getText().toString());
                        editor.putString(CampusSocialSystemApplication.USER_PASSWORD,mPasswordEdit.getText().toString());
                        editor.apply();
                        Log.d("Client", "login_success");

                        /*String sqlquery = "select * from user where phone=?";
                        Cursor cursor = dbManager.querySql(sqlquery,new String[]{sp.getString(MyApplication.USER_PHONE,"")});
                        if(cursor.getCount() == 0){
                            String sql1 = "insert into user values(?,?,?,?,?,?,?,?,?,?,?,?)";
                            User user = msg.getUser();
                            if(dbManager.executeSql(sql1,new String[]{null,user.getPassword(),user.getNickname(),user.getRealname(),
                                    user.getGender(),user.getId(),user.getSchool(),user.getEmail(),user.getIntroduce(),user.getPhone(),
                                    user.getImgPath(),String.valueOf(user.getLoginStatus())})){
                                Log.d("sql1","true");
                            }else {
                                Log.d("sql1","false");
                            }
                        }
                        while (cursor.moveToNext()){
                            Log.d("usercontent","密码"+cursor.getString(1)+"\n"
                                    +"手机号"+cursor.getString(9)+"\n"
                                    +"昵称"+cursor.getString(2)+"\n");
                        }*/
                        Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("phoneNumber" ,username);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.d("Client", "login_fail");
                        Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 登录信息发送到服务端验证
     * @author  cqx
     * create at 2018/5/7 10:47
     *//*
    boolean login(String a, String p){
        User user =new User();
        user.setPhoneNumber(a);
        user.setPassword(p);
        user.setOperation("login");
        boolean b=new AttendenceSystemClient(this).sendLoginInfo(user);
        //登陆成功
        if(b){
            try {
                //发送一个要求返回在线好友的请求的Message
                ObjectOutputStream oos = new ObjectOutputStream	(
                        ManageClientConServer.getClientConServerThread(user.getPhoneNumber()).getS().getOutputStream());
                Message m=new Message();
                m.setType(MessageType.GET_ONLINE_FRIENDS);
                m.setSender(user.getPhoneNumber());
                oos.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.finish();
            return true;
        }else {
            return false;
        }*/
    /*}*/
}
