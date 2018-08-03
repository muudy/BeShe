package com.xinyu.beshe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/comxinyubeshe/LoginActivity")
public class LoginActivity extends Activity {

    //登录
    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    public  String nikenameAtLoginActivity;
    public  String phoneNumberAtLoginActivity;
    public  String passwordAtLoginActivity;

    public  String static_nikename;
    public  String static_phoneNumber;

    private Button loginBtn;
    private TextView registerBtn;
    //数据库使用
    private MyDatabaseHelper dbhelper;
    private Button bun_cum;
    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbhelper = new MyDatabaseHelper(this);
        initView();
        loginGoToMain();

    }

    //存储密码直接登录功能
    private void loginGoToMain() {

        sp = getSharedPreferences("loginToken", Activity.MODE_PRIVATE);

        String phoneNumber1 = sp.getString("sp_phoneNumber", null);
        String password1 = sp.getString("sp_password", null);

        Log.i("标志", "phoneNumber1：" + phoneNumber1);
        Log.i("标志", "password1：" + password1);

        if (TextUtils.isEmpty(phoneNumber1) && TextUtils.isEmpty(password1)) {
            Log.i("标志", "TextUtils：");
            initEvent();
        } else {

            ARouter.getInstance().build("/comxinyuactivity/MainActivity").navigation();
            //ARouter.getInstance().build("/comxinyumainactivity/MainActivity").navigation();
            Log.i("标志", "ARouter：");
            finish();
        }


    }


    private void initEvent() {
        //name = sp.getString("userId", null);
        //userName = sp.getString("userName", null);
        //email = sp.getString("email", null);

        //登录
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进行数据库的查询
                phoneNumberAtLoginActivity = phoneNumberEditText.getText().toString();
                passwordAtLoginActivity = passwordEditText.getText().toString();
                Log.i("标志", "password：" + passwordAtLoginActivity);
                Cursor cursor = dbhelper.queryByphoneNumberANDpassword(phoneNumberAtLoginActivity, passwordAtLoginActivity);

                if (cursor.moveToNext()) {
                    String dataBasePassword = cursor.getString(cursor.getColumnIndex("password"));

                    String dataBasenikename = cursor.getString(cursor.getColumnIndex("nickname"));

                    if (dataBasePassword.equals(passwordAtLoginActivity)) {


                        static_nikename=dataBasenikename;
                        static_phoneNumber=phoneNumberAtLoginActivity;

                        //跳转主界面
                        ARouter.getInstance().build("/comxinyuactivity/MainActivity")
                                .withString("static_nikename",static_nikename)
                                .withString("static_phoneNumber",static_phoneNumber).navigation();


                        //ARouter.getInstance().build("/comxinyuactivity/MainActivity").navigation();
                        finish();
                        sp = getSharedPreferences("loginToken", 0);
                        SharedPreferences.Editor editor = sp.edit();

                        //editor.putString("userId",user.getmUserId());
                        editor.putString("sp_phoneNumber", phoneNumberAtLoginActivity);
                        editor.putString("sp_password", passwordAtLoginActivity);
                        //editor.putString("email",user.getmEmail());
                        //editor.putString("headImageUrl",user.getmHeadImageUrl());
                        editor.commit();

                    } else {
                        //密码输入失败
                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                        dialog.setTitle("提醒");
                        dialog.setMessage("输入密码不正确");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("确定", null);
                        dialog.show();
                    }
                } else {
                    //没有这个用户
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setTitle("提醒");
                    dialog.setMessage("该用户不存在");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", null);
                    dialog.show();
                }

            }
        });


        //注册
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //注册
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initView() {

        phoneNumberEditText = (EditText) findViewById(R.id.login_input_phone);
        passwordEditText = (EditText) findViewById(R.id.login_input_password);
        loginBtn = (Button) findViewById(R.id.login_btn_login);
        registerBtn = (TextView) findViewById(R.id.login_btn_register);

        bun_cum = (Button) findViewById(R.id.bun_cum);

        //登录
        bun_cum.setOnClickListener(new View.OnClickListener() {
            @Override
            //注册
            public void onClick(View view) {

                ARouter.getInstance().build("/comxinyuactivity/MainActivity").navigation();
            }
        });


    }


}
