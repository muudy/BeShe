package com.xinyu.activity;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Created by ME on 2018/5/15.
 */

@Route(path = "/comxinyuactivity/SoftwareIntroduction")
public class SoftwareIntroduction extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.softwareintroduction);

    }

}
