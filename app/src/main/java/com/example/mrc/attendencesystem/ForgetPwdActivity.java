package com.example.mrc.attendencesystem;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPwdActivity extends AppCompatActivity {
    EditText mEmailEdit;
    EditText mEmailIcEdit;
    AppCompatButton mSendIcBtn;
    AppCompatButton mNextBtn;
    private TimeCount time;
    private String mMessage;
    private boolean mIsNotExist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        findView();
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        setListener();
    }

    void findView(){
        mEmailEdit =(EditText)findViewById(R.id.register_email_editText);
        mEmailIcEdit =(EditText)findViewById(R.id.register_email_ic_editText);
        mSendIcBtn =(AppCompatButton)findViewById(R.id.forget_send_ic_btn);
        mNextBtn =(AppCompatButton)findViewById(R.id.next);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setListener() {
        time = new TimeCount(60000, 1000);
        mSendIcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEdit.getText().toString();
                if (validateEmail(email)) {

                }
            }
        });
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEdit.getText().toString();
                String emailIc = mEmailIcEdit.getText().toString();
                if (mIsNotExist) {
                    Toast.makeText(ForgetPwdActivity.this,
                            mMessage, Toast.LENGTH_LONG).show();
                } else {
                    if (validateInput(email, emailIc)) {
                        Intent intent = new Intent(ForgetPwdActivity.this, ResetPwdActivity.class);
                        Bundle bundle = new Bundle();
                        //bundle.putString(App.EMAIL_CODE, emailIc);
                       // bundle.putString(App.EMAIL, email);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

            }
        });

    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgetPwdActivity.this,getString(R.string.email_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isEmail(email)) {
            Toast.makeText(ForgetPwdActivity.this,getString(R.string.please_input_the_correct_email),Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateInput(String email, String emailIc) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgetPwdActivity.this,getString(R.string.email_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(emailIc)) {
            Toast.makeText(ForgetPwdActivity.this,getString(R.string.email_ic_can_not_be_null),Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onTick(long millisUntilFinished) {
            mSendIcBtn.setBackground(getResources().getDrawable(R.drawable.rectangle_button_clicked_style));
            mSendIcBtn.setClickable(false);
            mSendIcBtn.setText(millisUntilFinished / 1000 + getString(R.string.second_can_send_again));
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onFinish() {
            mSendIcBtn.setText(getString(R.string.get_id_code_again));
            mSendIcBtn.setClickable(true);
            mSendIcBtn.setBackground(getResources().getDrawable(R.drawable.rectangle_button_style));

        }
    }
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
