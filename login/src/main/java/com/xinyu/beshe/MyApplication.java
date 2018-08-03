package com.xinyu.beshe;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.alibaba.android.arouter.launcher.ARouter;

public class MyApplication extends Application {

    //public SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sp=  getSharedPreferences("loginToken",
                Activity.MODE_PRIVATE);

        ARouter.init(this); // 尽可能早，推荐在Application中初始化

    }

}
