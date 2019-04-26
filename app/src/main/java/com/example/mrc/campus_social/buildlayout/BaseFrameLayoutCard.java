package com.example.mrc.campus_social.buildlayout;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mrc.campus_social.provider.CampusSocialSystem;
import com.example.mrc.campus_social.baseinterface.ILayout;
import com.example.mrc.campus_social.entity.TranObject;

import butterknife.ButterKnife;

public abstract class BaseFrameLayoutCard extends FrameLayout implements ILayout {
    View mView = null;
    private ReceiveBroadCast mReceiveBroadCast;
    class ReceiveBroadCast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            TranObject msg = (TranObject) intent
                    .getSerializableExtra(CampusSocialSystem.MSGKEY);
            Log.d("client", "FragmentReceiver:"+msg.toString());
            if (msg != null) {//如果不是空，说明是消息广播
                // System.out.println("MsgActivity:" + msg);
                getMessage(msg);// 把收到的消息传递给子类
            } else {//如果是空消息，说明是关闭应用的广播
                close();
            }
        }
    }

    public abstract void getMessage(TranObject msg);
    /**
     * 子类直接调用这个方法关闭应用
     */
    public void close() {
        Intent i = new Intent();
        i.setAction(CampusSocialSystem.ACTION);
        getContext().sendBroadcast(i);
    }

    public BaseFrameLayoutCard(@NonNull Context context) {
        this(context, null);
    }

    public BaseFrameLayoutCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFrameLayoutCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,  0);
    }

    @SuppressLint("NewApi")
    public BaseFrameLayoutCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d("BaseFrameLayoutCard:","registered");
        mReceiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CampusSocialSystem.ACTION);    //只有持有相同的action的接受者才能接收此广播
        getContext().registerReceiver(mReceiveBroadCast, filter);
        mView = this;
    }

    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     *
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
        initView();
        resume();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mReceiveBroadCast);

    }

    //传参，可以用于修改View的内容
    @Override
    public void changeView(Object object) {

    }

    //获取当前View的对象
    @Override
    public View getView() {
        return mView;
    }

    //初始化当前View
    @Override
    public void initView() {

    }

    //当前View可见
    @Override
    public void resume() {

    }

    //当前View即将进入不可见状态
    @Override
    public void pause() {

    }

    //当前View不可见
    @Override
    public void stop() {

    }

    //销毁当前View
    @Override
    public void destroy() {
        this.onDetachedFromWindow();
    }
}