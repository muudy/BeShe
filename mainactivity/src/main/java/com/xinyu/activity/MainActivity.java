package com.xinyu.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xinyu.fragment.FourFragmentMain;
import com.xinyu.fragment.OneFragment;
import com.xinyu.fragment.TwoFragmentMain;
import com.xinyu.util.BottomNavigationViewEx;
import com.xinyu.util.ToastUtils;
import com.xinyu.utils.mylog;

//201413138072
//20451X
//201413138071
//243735

@Route(path = "/comxinyuactivity/MainActivity")
public class MainActivity extends Activity {
    private FragmentManager fragmentManager;
    private NewsFragment newsFragment;
    private OneFragment oneFragment;
    private TwoFragmentMain maintwoFragment;
    private Fragment timetableFragment;
    private FourFragmentMain mainfourFragment;

    private FragmentTransaction transaction;
    private Toolbar toolbar;

    public static String static_nikenameAtMainActivity;
    public static String static_phoneNumberAtMainActivity;

    int postionAtMainActivity = 1;//默认是另  显示第一个页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNaviation();

         String stringWithRoute=getIntent().getStringExtra("data");
         static_nikenameAtMainActivity=getIntent().getStringExtra("static_nikename");
         static_phoneNumberAtMainActivity=getIntent().getStringExtra("static_phoneNumber");

        mylog.I("static_nikenameAtMainActivity: "+static_nikenameAtMainActivity);
        mylog.I("setDefaultFragment: "+stringWithRoute);

        if(stringWithRoute!=null){
            postionAtMainActivity=Integer.parseInt(stringWithRoute);
        }

        setDefaultFragment(postionAtMainActivity);
        toolbar();
    }

    private void initBottomNaviation() {
        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getFragmentManager();
            transaction = fragmentManager.beginTransaction();

            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                setTitle("毕设");
                transaction.replace(R.id.fragment_content, newsFragment);
                transaction.commit();
                return true;

            } else if (id == R.id.navigation_dashboard) {
                setTitle("查找");
                transaction.replace(R.id.fragment_content, maintwoFragment);
                transaction.commit();
                return true;
            } else if (id == R.id.navigation_notifications) {
                setTitle("课程表");

                //Log.i("mylog","navigation_notifications");
                //ARouter.getInstance().build("/comhihuyangkbtimetable/TimeTableFragment").navigation();

                transaction.replace(R.id.fragment_content, timetableFragment);
                transaction.commit();
                ToastUtils.showToast(MainActivity.this,"如果没有数据，请先查询课程表");
                return true;

            } else if (id == R.id.menu_friends) {//4
                setTitle("我的");
                transaction.replace(R.id.fragment_content, mainfourFragment);
                transaction.commit();

                return true;
            } else
                return false;

        }
    };

    private void setDefaultFragment(int i) {
        mylog.I("setDefaultFragment: "+i);

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        newsFragment = new NewsFragment();

        oneFragment = new OneFragment();//1
        maintwoFragment = new TwoFragmentMain();//2

        //3
        timetableFragment = (Fragment)ARouter.getInstance().build("/comhihuyangkbtimetable/TimeTableFragment").navigation();

        mainfourFragment = new FourFragmentMain();//4

        if(i==1){
            //默认显示界面
            //默认fragment
            setTitle("毕设");
            transaction.replace(R.id.fragment_content, newsFragment);
            transaction.commit();
        }

        else if(i==2){
            //2
            setTitle("查找");

            transaction.replace(R.id.fragment_content, maintwoFragment);
            transaction.commit();
        }

        else if(i==3){
            setTitle("课程表");

            transaction.replace(R.id.fragment_content, timetableFragment);
            transaction.commit();
        }

        else if(i==4){
            setTitle("我的");
            transaction.replace(R.id.fragment_content, mainfourFragment);
            transaction.commit();
        }
        else Log.i("else","else");

    }

    private void toolbar() {
        setTitle("毕设");
        toolbar = (Toolbar) findViewById(R.id.view_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setActionBar(toolbar);
    }
}
