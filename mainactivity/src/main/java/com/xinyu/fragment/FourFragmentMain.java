package com.xinyu.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leon.lib.settingview.LSettingItem;
import com.xinyu.activity.R;

import static com.xinyu.activity.MainActivity.static_nikenameAtMainActivity;
import static com.xinyu.activity.MainActivity.static_phoneNumberAtMainActivity;


/**
 * @作者 miaoxinyu on 2018/1/11.
 * @描述 应用版本信息
 */
//implements OnClickListener,需要实现他的必须重写的方法
public class FourFragmentMain extends Fragment{

    //static_register_input_nickname;
    //static_register_input_phone;
    //static_register_input_password;

    private ImageView mIvHead;
    //private TextView one_textviewAtFragmentFour;
    private LSettingItem mSettingItemOne;
    private LSettingItem mSettingItemTwo;
    private LSettingItem mSettingItemThree;
    private LSettingItem mSettingItemFour;
    private LSettingItem mSettingItemFive;
    private LSettingItem mSettingItemSix;
    private Button cleardataandexit;
    private Activity myActivityAtFourFragmentMain;

    private TextView text1;
    private TextView text2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_main, container, false);
        initViews(view);
        initEvents();
        myActivityAtFourFragmentMain=getActivity();

        //Picasso.with(this).load(R.drawable.ic_launcher_background).transform(new CircleTransform()).into(mIvHead);
        return view;
    }


    public void initEvents() {


        mSettingItemOne.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                ARouter.getInstance().build("/comhihuyangkbtimetable/NewDeleteAllClass").navigation();

            }
        });


        mSettingItemTwo.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                ARouter.getInstance().build("/comhihuyangkbtimetable/AddCustomClassActivity").navigation();

            }
        });


        mSettingItemThree.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                ARouter.getInstance().build("/comhihuyangkbtimetable/SetAccountActivity").navigation();

            }
        });

        mSettingItemFour.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                ARouter.getInstance().build("/comxinyuactivity/SoftwareIntroduction").navigation();

            }
        });

        mSettingItemFive.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                ARouter.getInstance().build("/comxinyuactivity/OpenSource").navigation();

            }
        });

        mSettingItemSix.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                ARouter.getInstance().build("/comxinyuactivity/AboutThisAboutMe").navigation();

            }
        });


        cleardataandexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = myActivityAtFourFragmentMain.getSharedPreferences("loginToken", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                ARouter.getInstance().build("/comxinyubeshe/LoginActivity").navigation();
                myActivityAtFourFragmentMain.finish();

            }
        });


    }


    public void initViews(View view) {

        mIvHead = (ImageView) view.findViewById(R.id.headimage);
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);

        if(static_nikenameAtMainActivity!=null&&static_phoneNumberAtMainActivity!=null){

            text1.setText("昵称："+static_nikenameAtMainActivity);
            text2.setText("电话号码："+static_phoneNumberAtMainActivity);
        }


        mSettingItemOne = (LSettingItem) view.findViewById(R.id.item_one);
        mSettingItemTwo = (LSettingItem) view.findViewById(R.id.item_two);
        mSettingItemThree = (LSettingItem) view.findViewById(R.id.item_three);

        mSettingItemFour = (LSettingItem) view.findViewById(R.id.item_four);
        mSettingItemFive = (LSettingItem) view.findViewById(R.id.item_five);
        mSettingItemSix = (LSettingItem) view.findViewById(R.id.item_six);
        cleardataandexit = (Button) view.findViewById(R.id.cleardataandexit);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}