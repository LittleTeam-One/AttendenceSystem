package com.example.mrc.campus_social.baseinterface;

import android.view.View;

public interface ILayout {

    public View getView();

    public void changeView(Object object);

    public void initView();

    public void resume();

    public  void pause();

    public void stop();

    public void destroy();
}
