package com.example.mrc.attendencesystem.baseinterface;

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
