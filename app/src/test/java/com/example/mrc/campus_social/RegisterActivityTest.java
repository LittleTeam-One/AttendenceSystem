package com.example.mrc.campus_social;

import com.example.mrc.campus_social.activity.RegisterActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/4/3.
 */
public class RegisterActivityTest {
    private RegisterActivity mRegisterActivity;

    @Before
    public void setUp() throws Exception {
        mRegisterActivity = new RegisterActivity();
    }

    @Test
    public void isPhone() throws Exception {
        assertEquals("18738997273 is ",true,mRegisterActivity.isPhone("18738383838"));
    }

}