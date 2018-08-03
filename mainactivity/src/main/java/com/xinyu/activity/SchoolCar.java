package com.xinyu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Created by ME on 2018/5/17.
 */

@Route(path = "/comxinyuactivity/SchoolCar")
public class SchoolCar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schoolcar);
        Button schoolcarreturn=(Button)findViewById(R.id.schoolcarreturn);
        schoolcarreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
