package com.hihuyang.kb.timetable;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xinyu.utils.mylog;

//主页面
@Route(path = "/comhihuyangkbtimetable/MainActivity")
public class MainActivity extends AppCompatActivity {

    public static TimeTableFragment fTimeTable;
    private ToolsFragment fTools;

    //private SettingsFragment fSettings;

    public static FragmentTransaction transactionAtTableMainActivity;
    int postionAtTimeTable = 1;//默认是另  显示第一个页面

    public static MainActivity instance;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getFragmentManager();
            transactionAtTableMainActivity = fm.beginTransaction();
            transactionAtTableMainActivity.setCustomAnimations(R.animator.fade_in,R.animator.fade_out);

            int id=item.getItemId();
            if(id==R.id.navigation_tools){

                transactionAtTableMainActivity.replace(R.id.FragmentLayout, fTools);
                transactionAtTableMainActivity.commit();
                DatabaseUtil.copyDatabaseToExtStg(MainActivity.this);
                return true;

            }

            else if(id==R.id.navigation_timetable){

                transactionAtTableMainActivity.replace(R.id.FragmentLayout, fTimeTable);
                transactionAtTableMainActivity.commit();
                return true;

            }
            else if(id==R.id.navigation_settings){

                //transactionAtTableMainActivity.replace(R.id.FragmentLayout, fSettings);
                transactionAtTableMainActivity.commit();
                return true;

            }else             return false;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_timetable);
        getSupportActionBar().hide();
        instance = this;
        //toolbar();

        //资源重名,布局文件名字
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_main_timetable);

        //第一次进去选中的按钮
        navigation.setSelectedItemId(R.id.navigation_timetable);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fm = getFragmentManager();
        transactionAtTableMainActivity = fm.beginTransaction();

        fTools = ToolsFragment.newInstance();
        fTimeTable = TimeTableFragment.newInstance();

        //fSettings = new SettingsFragment();

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        String intentname = bundle.getString("postionAtTimeTable");
        mylog.I("获取到的name值为："+intentname);

        if(intentname!=null){
            postionAtTimeTable=Integer.parseInt(intentname);
        }

        //默认显示界面
        setFragment(postionAtTimeTable);
    }


    /*private void toolbar() {
        setTitle("课程表");
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setActionBar(toolbar);
    }*/

    public void setFragment(int position){

        if(position==1){
            //默认显示界面
            transactionAtTableMainActivity.replace(R.id.FragmentLayout, fTools);
            transactionAtTableMainActivity.commit();
        }

        if(position==2){
            //默认显示界面
            transactionAtTableMainActivity.replace(R.id.FragmentLayout, fTimeTable);
            transactionAtTableMainActivity.commit();
        }

        if(position==3){
            //默认显示界面
            //transactionAtTableMainActivity.replace(R.id.FragmentLayout, fSettings);
            transactionAtTableMainActivity.commit();
        }


    }


}
