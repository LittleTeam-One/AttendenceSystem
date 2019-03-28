package com.example.mrc.attendencesystem.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.mrc.attendencesystem.AttendenceSystem;
import com.example.mrc.attendencesystem.entity.TranObject;

public abstract class BaseFragment extends Fragment {
    private ReceiveBroadCast mReceiveBroadCast;
    class ReceiveBroadCast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            TranObject msg = (TranObject) intent
                    .getSerializableExtra(AttendenceSystem.MSGKEY);
            Log.d("client", "FragmentReceiver:"+msg.toString());
            if (msg != null) {//如果不是空，说明是消息广播
                // System.out.println("MsgActivity:" + msg);
                getMessage(msg);// 把收到的消息传递给子类
            } else {//如果是空消息，说明是关闭应用的广播
                close();
            }
        }
    }
    @Override
    public void onAttach(Activity activity) {

       // Log.d("BaseFragment:","registered:onAttach");
        /*receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION);    //只有持有相同的action的接受者才能接收此广播
        activity.registerReceiver(receiveBroadCast, filter);*/
        super.onAttach(activity);
    }

    @Override
    public void onStart(){
        /** 注册广播 */
        Log.d("BaseFragment:","registered");
        mReceiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AttendenceSystem.ACTION);    //只有持有相同的action的接受者才能接收此广播
        getActivity().registerReceiver(mReceiveBroadCast, filter);
        super.onStart();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    /**
     *注销广播
     * */
    @Override
    public void onDestroyView() {
        Log.d("BaseFragment:","unregistered");
        getActivity().unregisterReceiver(mReceiveBroadCast);
        super.onDestroyView();
    }


    public abstract void getMessage(TranObject msg);
    /**
     * 子类直接调用这个方法关闭应用
     */
    public void close() {
        Intent i = new Intent();
        i.setAction(AttendenceSystem.ACTION);
        getActivity().sendBroadcast(i);
        getActivity().finish();
    }

}
