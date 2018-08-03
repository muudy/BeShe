package com.xinyu.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity{

    private static Toast toast;//toast

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayoutViewLayoutId();//初始化布局
        ButterKnife.bind(this);//注解
        initView();
    }

    protected abstract void setContentLayoutViewLayoutId();//初始化布局
    protected abstract void initView();//初始化控件


    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this,clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }





}