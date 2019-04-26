package com.example.mrc.campus_social.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.mrc.campus_social.entity.TranObject;
import com.example.mrc.campus_social.provider.CampusSocialSystem;
import com.example.mrc.campus_social.tool.FragmentControl;


/**
 * Created by hppc on 2017/10/31.
 */

public abstract class BaseActivity extends FragmentActivity {

    /**
     * 广播接收者，接收GetMsgService发送过来的消息
     */
    private BroadcastReceiver MsgReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            TranObject msg = (TranObject) intent
                    .getSerializableExtra(CampusSocialSystem.MSGKEY);
            Log.d("client", "MsgReceiver:"+msg.toString());
            if (msg != null) {//如果不是空，说明是消息广播
                // System.out.println("MsgActivity:" + msg);
                getMessage(msg);// 把收到的消息传递给子类
            } else {//如果是空消息，说明是关闭应用的广播
                close();
            }
        }
    };

    /**
     * 抽象方法，用于子类处理消息，
     *
     * @param msg
     *            传递给子类的消息对象
     */
    public abstract void getMessage(TranObject msg);

    /**
     * 子类直接调用这个方法关闭应用
     */
    public void close() {
        Intent i = new Intent();
        i.setAction(CampusSocialSystem.ACTION);
        sendBroadcast(i);
        finish();
    }

    @Override
    public void onStart() { // 在start方法中注册广播接收者
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CampusSocialSystem.ACTION);
        registerReceiver(MsgReceiver, intentFilter);// 注册接受消息广播

    }

    @Override
    protected void onStop() {// 在stop方法中注销广播接收者
        super.onStop();
        unregisterReceiver(MsgReceiver);// 注销接受消息广播
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        FragmentControl.getInstance().getmFragmentManager().popBackStack();
    }
}
