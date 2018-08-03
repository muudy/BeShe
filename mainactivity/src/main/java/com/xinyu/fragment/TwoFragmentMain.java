package com.xinyu.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinyu.activity.R;
import com.xinyu.util.ToastUtils;
import com.xinyu.utils.mylog;

//在library中尽量不要用butterknife,也不要swicth
public class TwoFragmentMain extends Fragment implements View.OnClickListener {

    private LinearLayout courseLinearLayout;
    private LinearLayout scoreLinearLayout;
    private LinearLayout teaching_assessmentLinearLayout;
    private LinearLayout more_thingLinearLayout;
    private LinearLayout EnglishScoreLinearLayout;
    private LinearLayout schoolcarLinearLayout;






    public Button turntotable;
    public Button returnlogin;
    public Button clear;
    private Fragment ScoreFragmentAtTwoFragmentMain;

    public Activity ActivityAtTwoFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_fragment_main, container, false);

        courseLinearLayout = (LinearLayout)view.findViewById(R.id.courseLinearLayout);
        scoreLinearLayout = (LinearLayout)view.findViewById(R.id.scoreLinearLayout);
        teaching_assessmentLinearLayout = (LinearLayout)view.findViewById(R.id.teaching_assessmentLinearLayout);
        more_thingLinearLayout = (LinearLayout)view.findViewById(R.id.more_thingLinearLayout);
        EnglishScoreLinearLayout = view.findViewById(R.id.EnglishScoreLinearLayout);
        schoolcarLinearLayout = view.findViewById(R.id.schoolcarLinearLayout);

        ActivityAtTwoFragment=getActivity();

        /*turntotable = (Button) view.findViewById(R.id.turntotable);
        returnlogin = (Button) view.findViewById(R.id.returnlogin);
        clear = (Button) view.findViewById(R.id.clear);*/

        //设置监听事件
        /*turntotable.setOnClickListener(this);
        returnlogin.setOnClickListener(this);
        clear.setOnClickListener(this);*/
        courseLinearLayout.setOnClickListener(this);
        scoreLinearLayout.setOnClickListener(this);
        teaching_assessmentLinearLayout.setOnClickListener(this);
        more_thingLinearLayout.setOnClickListener(this);
        EnglishScoreLinearLayout.setOnClickListener(this);

        schoolcarLinearLayout.setOnClickListener(this);


        return view;
    }


    public void onClick(View v) {

        int id = v.getId();

        //课程
        if (id == R.id.courseLinearLayout) {

            mylog.I("courseLinearLayout:");

            ARouter.getInstance().build("/comhihuyangkbtimetable/ImportSheduleActivity").navigation();
            //ActivityAtTwoFragment.finish();
        }

        //成绩
        else if (id == R.id.scoreLinearLayout) {

            mylog.I("scoreLinearLayout:");

            //ScoreFragmentAtTwoFragmentMain = (Fragment)ARouter.getInstance().build("/comhihuyangkbtimetablemyscore/ScoreFragment").navigation();

            ARouter.getInstance().build("/comhihuyangkbtimetablemyscore/ScoreLoginActivity").navigation();

            //FragmentManager fmAtTwoFragmentMain = getActivity().getFragmentManager();
            //fmAtTwoFragmentMain.beginTransaction().replace(R.id.fragment_content, ScoreFragmentAtTwoFragmentMain).commit();
            //transaction.replace(R.id.fragment_content, ScoreFragmentAtTwoFragmentMain);
            //transaction.commit();
        }

        //？？？
        else if (id == R.id.teaching_assessmentLinearLayout) {

            ToastUtils.showToast(ActivityAtTwoFragment,"待添加功能");
            //Toast.makeText(getActivity(), "待添加功能", Toast.LENGTH_SHORT);

        }

        //？？？
        else if (id == R.id.more_thingLinearLayout) {

            ToastUtils.showToast(ActivityAtTwoFragment,"待添加功能");

        }

        //四六级
        else if (id == R.id.EnglishScoreLinearLayout) {

            ToastUtils.showToast(ActivityAtTwoFragment,"待添加功能");

        }

        //校车
        else if (id == R.id.schoolcarLinearLayout) {

            ARouter.getInstance().build("/comxinyuactivity/SchoolCar").navigation();

        }



        //turntotable
        /*else if (id == R.id.turntotable) {

            mylog.I("turntotable:");

            ARouter.getInstance().build("/comhihuyangkbtimetable/MainActivity").navigation();

        }

        //returnlogin
        else if (id == R.id.returnlogin) {

            mylog.I("returnlogin:");

            ARouter.getInstance().build("/comxinyubeshe/LoginActivity").navigation();

        }

        //清除数据退出
        else if (id == R.id.clear) {

            SharedPreferences sp = this.getActivity().getSharedPreferences("loginToken", 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();

            ARouter.getInstance().build("/comxinyubeshe/LoginActivity").navigation();

        }*/

    }//onClick,end


}