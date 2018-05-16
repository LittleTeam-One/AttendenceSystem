package com.example.mrc.attendencesystem.control;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mrc.attendencesystem.R;

/**
 * Created by Mr.C on 2018/5/10.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CustomToolbar extends Toolbar {
    /**
     * 左侧头像
     */
    private ImageView mImgLeftIcon;
    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 右侧图标
     */
    private ImageView mImgRightAdd;

    public CustomToolbar(Context context) {
        this(context,null);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImgLeftIcon = (ImageView) findViewById(R.id.img_left_icon);
        mTxtMiddleTitle = (TextView) findViewById(R.id.txt_main_title);
        mImgRightAdd = (ImageView) findViewById(R.id.img_right_add);
    }

    //设置中间title的内容
    public void setMainTitle(String text) {
        this.setTitle(" ");
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
    }

    //设置中间title的内容文字的颜色
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    //设置title左边文字
    public void setLeftTitleText(Drawable text) {
        mImgLeftIcon.setVisibility(View.VISIBLE);
        mImgLeftIcon.setBackground(text);
    }

    /*//设置title左边文字颜色
    public void setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
    }*/

    //设置title左边图标
    /*public void setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mImgLeftIcon.setCompoundDrawables(dwLeft, null, null, null);
    }*/
    //设置title左边点击事件
    public void setLeftTitleClickListener(OnClickListener onClickListener){
        mImgLeftIcon.setOnClickListener(onClickListener);
    }

    //设置title右边文字
    public void setRightTitleText(Drawable text) {
        mImgRightAdd.setVisibility(View.VISIBLE);
        mImgRightAdd.setBackground(text);
    }

    /*//设置title右边文字颜色
    public void setRightTitleColor(int color) {
        mImgRightAdd.setTextColor(color);
    }

    //设置title右边图标
    public void setRightTitleDrawable(int res) {
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mImgRightAdd.setCompoundDrawables(null, null, dwRight, null);
    }*/

    //设置title右边点击事件
    public void setRightTitleClickListener(OnClickListener onClickListener){
        mImgRightAdd.setOnClickListener(onClickListener);
    }
}
