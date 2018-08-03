package com.xinyu.beshe;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

import static com.xinyu.beshe.Utils.isCorrectPassword;
import static com.xinyu.beshe.Utils.isTelPhoneNumber;


@Route(path = "/comxinyubeshe/LoginActivity")
public class RegisterActivity extends Activity implements View.OnClickListener {

    /*
    15927557003
    123456
    * */

    private EditText register_input_nickname, register_input_phone, register_input_password, Confirmregister_input_password, register_edt_Securitycode;
    private Button btn_get_Securitycode, register_btn_register;
    private Button register_return;
    private MyDatabaseHelper dbhelper;

    private String nickname;
    private String phone;
    private String passWord;
    private String Confirmpassword;
    private String Securitycode;

    public  String static_register_input_nickname;
    public  String static_register_input_phone;
    public  String static_register_input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化bomb
        BmobSMS.initialize(RegisterActivity.this, "a142d348ebcc7bc6af971e561db32111");
        //初始化控件
        initView();
    }

    @Override
    public void onClick(View v) {
        //去除所有空格
        nickname = register_input_nickname.getText().toString().replaceAll(" +","");
        phone = register_input_phone.getText().toString().replaceAll(" +","");
        passWord = register_input_password.getText().toString().replaceAll(" +","");
        Confirmpassword = Confirmregister_input_password.getText().toString().replaceAll(" +","");
        Securitycode = register_edt_Securitycode.getText().toString().replaceAll(" +","");//验证码

        switch (v.getId()) {

            //获得验证码按钮
            case R.id.register_btn_get_Securitycode:
                event_btn_get_Securitycode();
                break;

            //注册按钮
            case R.id.register_register_btn_register:
                event_register_register_btn_register();
                break;

            //返回界面
            case R.id.register_register_return:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    //获得验证码按钮
    private void event_btn_get_Securitycode() {

        if (TextUtils.isEmpty(nickname)) {
            register_input_nickname.setError("昵称不能为空");

        } else if (!isTelPhoneNumber(phone)) {

            register_input_phone.setError("电话号码输入不正确");

        } else if (!isCorrectPassword(passWord)) {

            register_input_password.setError("密码须要六到十六位数字或字母组成");

        } else if (!Confirmpassword.equals(passWord)) {

            Confirmregister_input_password.setError("两次密码输入不一致");

        }

        else {

            ToastUtils.showToast(RegisterActivity.this,"如果验证码发送失败，请在设置中授予该程序所有权限");

            //进行获取验证码操作和倒计时1分钟操作
            BmobSMS.requestSMSCode(this, phone, "短信模板", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        //发送成功时，让获取验证码按钮不可点击，且为灰色
                        btn_get_Securitycode.setClickable(false);
                        btn_get_Securitycode.setBackgroundColor(Color.GRAY);
                        Toast.makeText(RegisterActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();

                        new CountDownTimer(60000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                btn_get_Securitycode.setText(millisUntilFinished / 1000 + "秒");
                            }

                            @Override
                            public void onFinish() {
                                btn_get_Securitycode.setClickable(true);
                                btn_get_Securitycode.setBackgroundResource(R.drawable.button_shape);
                                btn_get_Securitycode.setText("重新发送");
                            }
                        }.start();
                    } else {
                        Toast.makeText(RegisterActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    //注册按钮
    private void event_register_register_btn_register() {

        if (TextUtils.isEmpty(nickname)) {
            register_input_nickname.setError("昵称不能为空");

        } else if (!isTelPhoneNumber(phone)) {

            register_input_phone.setError("电话号码输入不正确");

        } else if (!isCorrectPassword(passWord)) {

            register_input_password.setError("密码须要六到十六位数字或字母组成");

        } else if (!Confirmpassword.equals(passWord)) {

            Confirmregister_input_password.setError("两次密码输入不一致");

        } else if (TextUtils.isEmpty(Securitycode)) {

            register_edt_Securitycode.setError("验证码不能为空");

        } else {
            //验证 验证码
            BmobSMS.verifySmsCode(this, phone, Securitycode, new VerifySMSCodeListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            processingUserData();
            ToastUtils.showToast(RegisterActivity.this,"注册成功，请登录");
            ARouter.getInstance().build("/comxinyubeshe/LoginActivity").navigation();
            finish();

        }//else


    }


    private void processingUserData() {

        //初始化数据库
        dbhelper = new MyDatabaseHelper(this);

        static_register_input_nickname = register_input_nickname.getText().toString();
        static_register_input_phone = register_input_phone.getText().toString();
        static_register_input_password = register_input_password.getText().toString();
        User user = new User(static_register_input_nickname, static_register_input_phone, static_register_input_password);
        dbhelper.insert(user);

        Cursor cursor = dbhelper.queryAll();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //int cursor_id = cursor.getString(cursor.getColumnIndex("_id"));
                int cursor_id = cursor.getInt(cursor.getColumnIndex("_id"));
                String cursor_register_input_nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                String cursor_phone_number = cursor.getString(cursor.getColumnIndex("phone_number"));
                String cursor_passWord = cursor.getString(cursor.getColumnIndex("password"));
                List<User> mqItemList = new ArrayList<>();
                mqItemList.add(new User(cursor_id, cursor_register_input_nickname, cursor_phone_number, cursor_passWord));
                mqItemList.clear();
            }
            cursor.close();
        }

    }

    /**
     * 判断手机是否合法
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8}任意数字；
     * 第3—11位：0—9任意数字
     */

    //初始化控件
    private void initView() {
        register_input_nickname = (EditText) findViewById(R.id.register_input_nickname);//昵称
        register_input_phone = (EditText) findViewById(R.id.register_input_phone);//电话号码
        register_input_password = (EditText) findViewById(R.id.register_input_password);//密码
        Confirmregister_input_password = (EditText) findViewById(R.id.register_input_Confirmpassword);//确认密码
        register_edt_Securitycode = (EditText) findViewById(R.id.register_edt_Securitycode);//验证码框

        btn_get_Securitycode = (Button) findViewById(R.id.register_btn_get_Securitycode);//获得验证码按钮
        register_btn_register = (Button) findViewById(R.id.register_register_btn_register);//注册
        register_return = (Button) findViewById(R.id.register_register_return);//返回界面
        //监听初始化
        btn_get_Securitycode.setOnClickListener(this);
        register_btn_register.setOnClickListener(this);
        register_return.setOnClickListener(this);

        //后门
        Button  register_btnn = (Button) findViewById(R.id.register_btnn);
        register_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            //注册
            public void onClick(View view) {
                User user1 = new User("qwer", "123", "123");
                //初始化数据库
                dbhelper = new MyDatabaseHelper(getApplicationContext());
                dbhelper.insert(user1);
            }
        });

    }
}


/**
 * 目标要求：输入手机号，点击获取验证码，用户把验证码填写完毕，点击登录
 * 具体内容：
 * 1、输入手机号时，判断是不是11位手机号，不是11位，当点击获取
 * 验证码按钮时则提示-->请输入11位有效手机号码，是11位，则进行点击获取验
 * 证码操作，并提示验证码已发送，请尽快使用
 * 2、当进行获取验证码操作后，获取验证码按钮变成灰色，且不可点击，并进行
 * 倒计时操作，倒计时1分钟后可以重新点击，再次发送验证码
 * 3、点击登录按钮时，若手机号和验证码有一个为空，则提示手机号与验证码
 * 不能为空，若验证码已填写，则判断用户填写所验证码与系统发送验证码是否一致，
 * 一致则提示登录成功，错误则提示验证码输入错误
 */