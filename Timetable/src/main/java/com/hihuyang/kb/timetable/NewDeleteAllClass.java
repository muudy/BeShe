package com.hihuyang.kb.timetable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

//主页面
@Route(path = "/comhihuyangkbtimetable/NewDeleteAllClass")
public class NewDeleteAllClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdeleteallcourse);
        Button delete_allAtNewDeleteAllCourse = findViewById(R.id.delete_allAtNewDeleteAllCourse);


        delete_allAtNewDeleteAllCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(NewDeleteAllClass.this);
                normalDialog.setTitle(getResources().getString(R.string.delete_all_classes));
                normalDialog.setMessage(getResources().getString(R.string.confirm_delete_all_classes));
                normalDialog.setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CourseManager cm = new CourseManager(NewDeleteAllClass.this);
                                cm.truncateDatabase();
                                finish();
                                //ARouter.getInstance().build("/comxinyuactivity/MainActivity").navigation();


                            }
                        });

                normalDialog.setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Nothing to do
                            }
                        });
                normalDialog.show();


            }
        });


    }


}

