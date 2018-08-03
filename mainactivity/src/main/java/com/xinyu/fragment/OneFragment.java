package com.xinyu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xinyu.activity.R;
import com.xinyu.util.mylog;


//在library中尽量不要用butterknife,也不要swicth
public class OneFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);

        return view;
    }



    public void onClick(View v) {

        /*int id = v.getId();
        if (id == R.id.turntotable) {

            //@Route(path = "/comhihuyangkbtimetable/MainActivity")

            ARouter.getInstance().build("/comhihuyangkbtimetable/MainActivity").navigation();

        } else if (id == R.id.returnlogin) {

            ARouter.getInstance().build("/comxinyubeshe/LoginActivity").navigation();


        } else if (id == R.id.clear) {

            ARouter.getInstance().build("/comxinyubeshe/LoginActivity").navigation();

        }
*/
    }//onClick,end





}