package com.hihuyang.kb.timetable.myscore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hihuyang.kb.timetable.R;
import com.xinyu.utils.mylog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hihuyang.kb.timetable.myscore.ScoreLoginActivity.inputUsername;
import static com.hihuyang.kb.timetable.myscore.ScoreLoginActivity.spacetextAtScoreLoginActivity;

@Route(path = "/comhihuyangkbtimetablemyscore/ScoreActivityAttimetable")
public class ScoreActivityAttimetable extends Activity {

    private ListView score_listview;
    public static List<Item1> mItemList1 = new ArrayList<>();
    public Map<String, String> coursemap = new HashMap<>();
    public static ListViewAdapter mListViewAdapter;

    private Toolbar toolbar;

    public String courseAsubstring;
    public String TheScoreAtLast;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorefragment);
        score_listview = (ListView) findViewById(R.id.score_listview);
        toolbar();

        LogUtil.e("spacetextAtScoreLoginActivity: ", spacetextAtScoreLoginActivity);

        matcherBysign(inputUsername, spacetextAtScoreLoginActivity);
        initItemList1();
        initListView(score_listview);

    }


    public void initItemList1() {
        mItemList1.clear();
        for (String key : coursemap.keySet()) {
            //Log.i("key="+ key,"and value="+coursemap.get(key));
            //mylog.I("value:"+coursemap.get(key));

            String coursescore = key;
            String coursename = coursemap.get(key);
            mItemList1.add(new Item1(coursename, coursescore));
            //System.out.println("key= "+ key + " and value= " + coursemap.get(key));
        }

    }

    public void initListView(ListView listView) {

        //适配器填数据
        mListViewAdapter = new ListViewAdapter(ScoreActivityAttimetable.this, mItemList1);

        mylog.I("mListViewAdapter: " + mListViewAdapter);

        //mListViewAdapter = new ListViewAdapter(mItemList1);
        listView.setAdapter(mListViewAdapter);
    }


    private void toolbar() {
        setTitle("成绩表");
        toolbar = (Toolbar) findViewById(R.id.view_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setActionBar(toolbar);

    }


    public void matcherBysign(String number, String str) {
        //String Matcherstr="缪德浩.+?正常考试";
        String Matcherstr = number + ".+?正常考试";
        Matcher m = Pattern.compile(Matcherstr).matcher(str);
        while (m.find()) {

            //缪德浩2017-2018-1形势与政策94 通识教育平台课程必修322正常考试
            String courseA = m.group();
            mylog.E("courseA: "+courseA);
            //courseA: 201413138103缪德浩2017-2018-1形势与政策94 通识教育平台课程必修322正常考试 where-at(com.hihuyang.kb.timetable.myscore.ScoreActivityAttimetable.matcherBysign(ScoreActivityAttimetable.java:93).java:93)

            String courseStudentname = courseA.substring(0, 16);
            //courseStudentname: 201413138103缪德浩2
            mylog.E("courseStudentname: "+courseStudentname);

            int namecount = matcherByHanzi(courseStudentname);

            //mylog.I("namecount: " + namecount);

            //形势与政策94 通识教育平台课
            courseAsubstring = courseA.substring(23 + namecount, courseA.length() - 10);

            mylog.E("courseAsubstring: "+courseAsubstring);


            TheScoreAtLast = courseA.substring(27 + namecount, courseA.length() - 10);

            mylog.I("TheScoreAtLast: "+TheScoreAtLast);

            //courseAsubstring = courseA.substring(26, courseA.length() - 10);
            //mylog.I("courseAsubstring: " + courseAsubstring);

            //数字成绩
            String regex = "\\d+\\d";//匹配数字
            Matcher m3 = Pattern.compile(regex).matcher(courseAsubstring);
            while (m3.find()) {

                //找到成绩,正则式匹配，94
                String courseScore = m3.group();
                mylog.E("courseScore: "+courseScore);

                //根据成绩找到课名，形势与政策
                String coursename = courseAsubstring.substring(0, m3.start());
                mylog.E("coursename: "+coursename);

                if (courseScore.length() > 0 && courseScore.length() < 3 && coursename.length() > 3 && coursename.length() < 21) {
                    coursemap.put(coursename, courseScore);
                }

            }

            //字母成绩    //形势与政策94 通识教育平台课

            //[0-9\.\-] //匹配所有的数字，句号和减号
            // ^[\u4e00-\u9fa5_a-zA-Z0-9]+$
            String regexzimu = "[A-D][+\\-\\ ]";//匹配字母
            Matcher m3zimu = Pattern.compile(regexzimu).matcher(courseAsubstring);
            while (m3zimu.find()) {

                String m3zimu1 = m3zimu.group();
                mylog.E("m3zimu1: "+m3zimu1);

                //字母成绩
                String zimuScore = courseAsubstring.substring(m3zimu.start(), m3zimu.start() + 2);//字母成绩
                mylog.E("zimuScore: "+zimuScore);

                //根据字母成绩找到课名，形势与政策
                String coursename = courseAsubstring.substring(0, m3zimu.start());
                mylog.I("coursename: "+coursename);

                if (zimuScore.length() > 0 && zimuScore.length() < 3 && coursename.length() > 3 && coursename.length() < 21) {
                    coursemap.put(coursename, zimuScore);
                }
            }////匹配字母


        }//匹配结束

        for (String key : coursemap.keySet()) {
            //Log.i("key=" + key, "and value=" + coursemap.get(key));
            mylog.E("coursenameandscore: "+key+coursemap.get(key));
            //mylog.E("coursename: "+key.length());
        }

    }//matcherBysign,end



    public int matcherByHanzi(String strname) {
        String Matcherstr = "[\\u4e00-\\u9fa5]";
        Matcher m = Pattern.compile(Matcherstr).matcher(strname);
        int namecount = 0;
        while (m.find()) {
            namecount++;
            String nameA = m.group();
            //mylog.E("nameA: "+nameA+nameA.length());
        }//匹配结束

        //System.out.println("nameA: "+namecount);
        return namecount;
    }//matcherByHanzi,end


}
