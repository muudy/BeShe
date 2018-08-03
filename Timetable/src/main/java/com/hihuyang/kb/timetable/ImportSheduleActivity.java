package com.hihuyang.kb.timetable;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.xinyu.utils.mylog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.hihuyang.kb.timetable.MainActivity.fTimeTable;

@Route(path = "/comhihuyangkbtimetable/ImportSheduleActivity")
public class ImportSheduleActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private TextView hinttext;
    private String lastJSESSIONID;
    private ProgressDialog waitingDialog;
    private String onDate;
    private String offDate;
    final Context ctx = this;
    CourseManager cm;

    private String oneDate;
    private String twoDate;
    private String threeDate;
    private String fourDate;
    private String fiveDate;
    private String sixDate;
    private String sevenDate;
    private String eightDate;

    private FragmentTransaction transaction;


    public  Map<String, String> coursemap=new HashMap<>();

    public static ImportSheduleActivity instance;
    public static String spacetext;
    private  String myDate1;
    private  String myDate2;
    private  String myDate3;
    private  String myDate4;
    private  String myDate5;
    private  String myDate6;
    private  String myDate7;

    public MaterialSpinner spinner;

    public String courseWithData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_shedule);

        //instance
        instance = this;

        getSupportActionBar().setTitle(getResources().getString(R.string.import_shedule));
        int color = Color.parseColor("#2196F3");
        ColorDrawable colorDrawable=new ColorDrawable(color);
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        waitingDialog = new ProgressDialog(this);
        hinttext = findViewById(R.id.textView);
        final ImageView captcha = findViewById(R.id.captchaImage);
        cm = new CourseManager(ctx);
        captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hinttext.getText() != getResources().getString(R.string.loading_captcha)){
                    loadCaptcha();
                    captcha.setImageDrawable(getResources().getDrawable(R.drawable.ic_sync));
                    hinttext.setText(getResources().getString(R.string.loading_captcha));
                }
            }
        });

        Calendar nowCal = Calendar.getInstance();
        ToggleButton tgBtn = findViewById(R.id.toggleButton);
        int month = nowCal.get(Calendar.MONTH);
        int year = nowCal.get(Calendar.YEAR);
        if(month>=8){
            tgBtn.setTextOn(getResources().getString(R.string.first_sem) + " (" + String.valueOf(year)+"-"+String.valueOf(year+1)+"-1)");
            onDate = String.valueOf(year)+"-"+String.valueOf(year+1)+"-1";
            tgBtn.setTextOff(getResources().getString(R.string.second_sem) + " (" + String.valueOf(year)+"-"+String.valueOf(year+1)+"-2)");
            offDate = String.valueOf(year)+"-"+String.valueOf(year+1)+"-2";
        }else if(month<=2){
            tgBtn.setTextOn(getResources().getString(R.string.first_sem) + " (" + String.valueOf(year-1)+"-"+String.valueOf(year)+"-1)");
            onDate = String.valueOf(year-1)+"-"+String.valueOf(year)+"-1";
            tgBtn.setTextOff(getResources().getString(R.string.second_sem) + " (" + String.valueOf(year-1)+"-"+String.valueOf(year)+"-2)");
            offDate = String.valueOf(year-1)+"-"+String.valueOf(year)+"-2";
        }else{
            tgBtn.setTextOn(getResources().getString(R.string.second_sem) + " (" + String.valueOf(year-1)+"-"+String.valueOf(year)+"-2)");
            onDate = String.valueOf(year-1)+"-"+String.valueOf(year)+"-2";
            tgBtn.setTextOff(getResources().getString(R.string.first_sem) + " (" + String.valueOf(year)+"-"+String.valueOf(year+1)+"-1)");
            offDate = String.valueOf(year)+"-"+String.valueOf(year+1)+"-1";
        }

        if(month>=3&&month<=7) {
             myDate1 = String.valueOf(year) + "-" + String.valueOf(year + 1) + "-1";
             myDate2 = String.valueOf(year-1) + "-" + String.valueOf(year) + "-2";
             myDate3 = String.valueOf(year-1) + "-" + String.valueOf(year) + "-1";
             myDate4 = String.valueOf(year-2) + "-" + String.valueOf(year - 1) + "-2";
             myDate5 = String.valueOf(year-2) + "-" + String.valueOf(year - 1) + "-1";
             myDate6 = String.valueOf(year-3) + "-" + String.valueOf(year -2) + "-2";
             myDate7 = String.valueOf(year-3) + "-" + String.valueOf(year -2) + "-1";
        }

        else{


        }
        String aaaa="aaaaa";

        String[] ANDROID_VERSIONS = {
                myDate1,
                myDate2,
                myDate3,
                myDate4,
                myDate5,
                myDate6,
                myDate7,
                "Jelly Bean",
                "KitKat",
                "Lollipop",
                "Oreo"
        };


        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems(ANDROID_VERSIONS);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                courseWithData=item;
                mylog.I("courseWithData: "+courseWithData);
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


        tgBtn.setText(onDate);
        SharedPreferences sharedPref = getSharedPreferences("USER_STORE",Context.MODE_PRIVATE);
        EditText usernameText = findViewById(R.id.username_text);
        EditText passwordText = findViewById(R.id.password_text);
        usernameText.setText(sharedPref.getString("account_username", ""));
        passwordText.setText(sharedPref.getString("account_password", ""));
        loadCaptcha();
    }


    public void import_Clicked(View view) {

        findCourseandScore();

    }



    public  String findCourseandScore() {
        if(lastJSESSIONID == null){
            return "1";
        }
        Button import_now = findViewById(R.id.start_import);
        waitingDialog.setTitle(getResources().getString(R.string.importing));
        waitingDialog.setMessage(getResources().getString(R.string.logging_to_jwc));
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
        EditText usernameText = findViewById(R.id.username_text);
        EditText passwordText = findViewById(R.id.password_text);
        EditText captchaText = findViewById(R.id.captcha_text);
        String inputUsername = usernameText.getText().toString();
        String inputPassword = passwordText.getText().toString();
        String inputCaptcha = captchaText.getText().toString();

        FormBody formBody = new FormBody.Builder()
                .add("PASSWORD", inputPassword)
                .add("RANDOMCODE", inputCaptcha)
                .add("useDogCode", "")
                .add("USERNAME", inputUsername)
                .add("x", "55")
                .add("y", "17")
                .build();

        //登录界面
        Request request = new Request.Builder()
                .url("http://jwxt.wust.edu.cn/whkjdx/Logon.do?method=logon")
                .addHeader("Cookie", lastJSESSIONID)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        waitingDialog.dismiss();
                        Snackbar.make(hinttext,getResources().getString(R.string.network_connection_failed),Snackbar.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                //Log.d("RES11登录界面",res);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(res.length()>200){
                            waitingDialog.dismiss();
                            if(res.contains("验证码错误")){
                                Snackbar.make(hinttext,getResources().getString(R.string.invalid_captcha),Snackbar.LENGTH_SHORT).show();
                            }else if(res.contains("该帐号不存在或密码错误")){
                                Snackbar.make(hinttext,getResources().getString(R.string.invalid_account),Snackbar.LENGTH_SHORT).show();
                            }
                            loadCaptcha();
                        }else{
                            waitingDialog.setMessage(getResources().getString(R.string.downloading_shedule));
                            //Success logon
                            waitingDialog.setMessage(getResources().getString(R.string.client_start_progress));

                            //主界面
                            Request request = new Request.Builder()
                                    .url("http://jwxt.wust.edu.cn/whkjdx/framework/main.jsp")
                                    .addHeader("Cookie", lastJSESSIONID)
                                    .build();
                            Call call = client.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            waitingDialog.dismiss();
                                            Snackbar.make(hinttext,getResources().getString(R.string.network_connection_failed),Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, final Response responseFinal) throws IOException {

                                    final String res1122 = responseFinal.body().string();
                                    //Log.d("RES11主界面",res1122);

                                    //登录界面
                                    Request request = new Request.Builder()
                                            .url("http://jwxt.wust.edu.cn/whkjdx/Logon.do?method=logonBySSO")
                                            .addHeader("Cookie", lastJSESSIONID)
                                            .build();
                                    Call call2 = client.newCall(request);
                                    call2.enqueue(new Callback() {//call2
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    waitingDialog.dismiss();
                                                    Snackbar.make(hinttext, getResources().getString(R.string.network_connection_failed), Snackbar.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        @Override
                                        public void onResponse(Call call, final Response responseFinal) throws IOException {
                                            final String r2 = responseFinal.body().string();
                                            //Log.d("RES11logonBySSO",r2);

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    EditText usernameText = findViewById(R.id.username_text);
                                                    String inputUsername = usernameText.getText().toString();
                                                    ToggleButton tgBtn = findViewById(R.id.toggleButton);
                                                    String date;

                                                    if(tgBtn.isChecked()){
                                                        //date = onDate;
                                                        //Log.d("RES",onDate);
                                                    }else{
                                                        //date = offDate;  2017-2018-2
                                                        //Log.d("RES",offDate);
                                                    }


                                                    int year1=2016;
                                                    String myDate = String.valueOf(year1)+"-"+String.valueOf(year1+1)+"-1";

                                                    date = myDate;

                                                    //课程表表格选择学期？
                                                    //课程表界面
                                                    Request request = new Request.Builder()
                                                            .url("http://jwxt.wust.edu.cn/whkjdx/tkglAction.do?method=printExcelByFz&sql=&type=xsdy&xnxqh="+courseWithData+"&xsid="+inputUsername)
                                                            .addHeader("Cookie", lastJSESSIONID)
                                                            .build();
                                                    Call call3 = client.newCall(request);
                                                    call3.enqueue(new Callback() {
                                                        @Override
                                                        public void onFailure(Call call, IOException e) {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    waitingDialog.dismiss();
                                                                    Snackbar.make(hinttext, getResources().getString(R.string.network_connection_failed), Snackbar.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                        @Override
                                                        public void onResponse(Call call, final Response responseFinal) throws IOException {
                                                            final String r3 = responseFinal.body().string();
                                                            //LogUtil.e("mylog,r3: ",r3);

                                                            //Log.d("RES11printExcelr3",r3);
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    String[] lines = r3.split("\n");
                                                                    //LogUtil.e("mylog,lines: ",lines);

                                                                    int items = 0;
                                                                    for (String item : lines) {
                                                                        if(item.startsWith("objdocSheets.Sheets(\"Sheet1\").Cells") && item.length()>=60 && !item.startsWith("objdocSheets.Sheets(\"Sheet1\").Cells(2,2).Value")){
                                                                            items++;
                                                                            String[] temp = item.split("  ");
                                                                            items = items + temp.length - 1;
                                                                        }
                                                                    }
                                                                    String[] results = new String[items];
                                                                    items = 0;

                                                                    for (String item : lines) {
                                                                        if(item.startsWith("objdocSheets.Sheets(\"Sheet1\").Cells") && item.length()>=60 && !item.startsWith("objdocSheets.Sheets(\"Sheet1\").Cells(2,2).Value")){
                                                                            String substr = item.substring(item.indexOf("Cells(")+6);
                                                                            String[] temp = substr.split("  ");
                                                                            String[] titleTemps = substr.split("\"");
                                                                            String title = titleTemps[0] + "\"";
                                                                            String content = titleTemps[1];
                                                                            String[] ntemp = content.split("n");
                                                                            for(int t=3;t<=temp.length;t++){
                                                                                String newString = ntemp[(t-3)*2] + "n" + ntemp[1+(t-3)*2];
                                                                                newString = title + newString.substring(0, newString.length() - 1) + "\";";
                                                                                results[items] = newString;
                                                                                //Log.d("EX",newString);
                                                                                items++;
                                                                            }
                                                                            String lastString = title + ntemp[ntemp.length-2] + "n" + ntemp[ntemp.length-1] + "\";";
                                                                            results[items] = lastString;
                                                                            //Log.d("EX",lastString);
                                                                            items++;
                                                                        }
                                                                    }
                                                                    CourseClass[] resultCourse = new CourseClass[items];
                                                                    for(int i=0;i<items;i++){
                                                                        //Get Clock
                                                                        resultCourse[i] = new CourseClass();
                                                                        String[] temp = results[i].split(",");
                                                                        resultCourse[i].clock = ((Integer.valueOf(temp[0])-14)*2+1)*10+2;
                                                                        String temps = "";
                                                                        if(temp.length>2){
                                                                            for(int m=1;m<temp.length;m++){
                                                                                temps = temps + temp[m];
                                                                                if(m!=temp.length-1){
                                                                                    temps = temps + ",";
                                                                                }
                                                                            }
                                                                        }else{
                                                                            temps = temp[1];
                                                                        }
                                                                        //Get Weekdays
                                                                        temp = temps.split("\\)");
                                                                        String temps1 = temp[0];
                                                                        resultCourse[i].weekday = Integer.valueOf(temps1)-1;
                                                                        //Get ClassName
                                                                        temp = temps.split("\"");
                                                                        temps = temp[1];
                                                                        temp = temps.split("n");
                                                                        temps = temp[0];
                                                                        temps = temps.substring(0, temps.length() - 1);
                                                                        resultCourse[i].name = temps;
                                                                        temps = temp[1];
                                                                        //Get teacherName
                                                                        temp = temps.split("  ");
                                                                        resultCourse[i].teacher = temp[0];
                                                                        temps = temp[1];
                                                                        //Get week

                                                                        //Get Week New Version
                                                                        String[] weektemp = temps.split("周");
                                                                        String allweeks = weektemp[0];
                                                                        String[] eachweek = allweeks.split(",");
                                                                        int allweekid = 0;
                                                                        for(String thisweek:eachweek){
                                                                            String[] splitline = thisweek.split("-");
                                                                            if(splitline.length==1){
                                                                                allweekid += Math.pow(2,25-Integer.valueOf(splitline[0]));
                                                                            }else{
                                                                                int startWeek = Integer.valueOf(splitline[0]);
                                                                                int endWeek = Integer.valueOf(splitline[1]);
                                                                                int weekid = 0;
                                                                                if(startWeek<=endWeek){
                                                                                    for(int j=startWeek;j<=25;j++){
                                                                                        weekid *= 2;
                                                                                        if(j<=endWeek){
                                                                                            weekid += 1;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                allweekid += weekid;
                                                                            }
                                                                        }
                                                                        resultCourse[i].week = allweekid;
                                                                        temps = weektemp[1];
                                                                        //Get Place
                                                                        String[] placetemp = temps.split("节]");
                                                                        if(placetemp.length>1){
                                                                            resultCourse[i].place = placetemp[1];
                                                                        }else{
                                                                            resultCourse[i].place = temps;
                                                                        }
                                                                        //Finally
                                                                        resultCourse[i].intype = 1;

                                                                        cm.addCourse(resultCourse[i]);
                                                                    }
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                                                                    int classNum = items;

                                                                    if(classNum==0){
                                                                        builder.setMessage(getResources().getString(R.string.no_class_imported))
                                                                                .setCancelable(false)
                                                                                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                                        waitingDialog.dismiss();
                                                                                    }
                                                                                });
                                                                    }else{
                                                                        builder.setMessage(String.format(getResources().getString(R.string.class_imported),classNum))
                                                                                .setCancelable(false)
                                                                                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                                        waitingDialog.dismiss();

                                                                                        mylog.I("ARouter1: ");

                                                                                        ARouter.getInstance().build("/comxinyuactivity/MainActivity").withString("data","3").navigation();
                                                                                        mylog.I("ARouter2: ");

                                                                                        /*Intent intent =new Intent(ImportSheduleActivity.this,MainActivity.class);
                                                                                        //用Bundle携带数据
                                                                                        Bundle bundle=new Bundle();
                                                                                        //传递name参数为tinyphpW
                                                                                        bundle.putString("postionAtTimeTable", "2");
                                                                                        intent.putExtras(bundle);
                                                                                        startActivity(intent);*/


                                                                                        //MainActivity mainActivity = new MainActivity();
                                                                                        //mainActivity.setFragment(2);

                                                                                        //MainActivity .instance.setFragment(2);
                                                                                        //transaction.replace(R.id.fragment_content, fTimeTable);
                                                                                        //transaction.commit();


                                                                                        finish();



                                                                                    }
                                                                                });
                                                                    }
                                                                    AlertDialog alert = builder.create();
                                                                    alert.show();


                                                                }
                                                            });






                                                        }
                                                    });////课程表界面结束
                                                }
                                            });







                                            //String XSCJ_PATH = "http://jwxt.wust.edu.cn/whkjdx/xszqcjglAction.do?method=queryxscj";

                                            //成绩表界面，http://jwxt.wust.edu.cn/whkjdx/xszqcjglAction.do?method=queryxscj
                                            String path="http://jwxt.wust.edu.cn/whkjdx/xszqcjglAction.do?method=queryxscj";
                                            //String path="http://jwxt.wust.edu.cn/whkjdx/xszqcjglAction.do?method=queryxscj&kksj=&kcxz=&kcmc=&xsfs=qbcj&ok=";


                                            FormBody formBody5 = new FormBody.Builder()
                                                    .add("kksj", "")
                                                    .add("kcxz", "")
                                                    .add("kcmc", "")
                                                    .add("xsfs", "qbcj")
                                                    .add("ok", "")
                                                    .build();



                                            Request request5 = new Request.Builder()
                                                    .url(path)
                                                    .addHeader("Cookie", lastJSESSIONID)
                                                    .post(formBody5)
                                                    .build();

                                            Call call5 = client.newCall(request5);
                                            call5.enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            waitingDialog.dismiss();
                                                            Snackbar.make(hinttext, getResources().getString(R.string.network_connection_failed), Snackbar.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                                @Override
                                                public void onResponse(Call call, final Response responseFinal1) throws IOException {
                                                    final String r5 = responseFinal1.body().string();
                                                    //String r55 = responseFinal1.toString();
                                                    //LogUtil.e("RES11queryxscjr5",r5);



                                                    Document doc = Jsoup.parse(r5);


                                                    //Log.d("mylog","mylog");

                                                    //Log.d("mylogtd",doc.getElementsByTag("td").toString());

                                                    //LogUtil.e("mylogtd",doc.select("td").toString());


                                                    //Log.d("mylogoption",doc.select("option").toString());
                                                    //只需要这一个就够了
                                                    //Log.d("mylogtext",doc.text());

                                                    String text=doc.text();
                                                    //Log.d("mylogStringtext",text);
                                                    //mylog.I("text: "+text);

                                                    //mylog.I("spacetext: "+spacetext);
                                                    spacetext=text.replace(" ", "");

                                                    //Log.d("mylogspacetext",spacetext);
                                                    matcherBysign(spacetext);


                                                    //LogUtil.e("mylog带有height属性的td元素",doc.select("td[height]").toString());
                                                    //LogUtil.e("mylog带有height=\"23\"属性的td元素",doc.select("td[height=\"23\"]").toString());
                                                    //LogUtil.e("mylog带有width=\"2000\" 属性的td元素",doc.select("td[width=\"2000\" ]").toString());
                                                    //LogUtil.e("mylog带有title 属性的td元素",doc.select("td[title]").toString());

                                                    //LogUtil.e("mylog带有height width title属性的td元素",doc.select("td[height][width][title]").toString());

                                                    //LogUtil.e("mylog带有height width title属性的td元素",doc.select("td[height][width][title]").toString());
                                                    //Elements elements = doc.select("td[height][width][title];

                                                    //Iterator it = Jsoup.connect(path).cookie(cookies[1], cookies[0]).userAgent("Mozilla").
                                                    //data((Map) map).timeout(5000).post().select("table#mxh").first().select("tr").iterator();

                                                    //Document doc11=doc.select("td[height][width][title]");

                                                    //Iterator it = doc.select("td[height][width][title]").iterator();
                                                    /*List<Map<String, String>> list = new ArrayList();

                                                        Map<String, String> map2;
                                                        //Iterator it = Jsoup.connect(path).cookie(cookies[1], cookies[0]).userAgent("Mozilla").data((Map) map).timeout(5000).post().select("table#mxh").first().select("tr").iterator();
                                                        //Iterator it = doc.select("td[height][width][title]").iterator();
                                                    //Iterator it = doc.select("td").iterator();
                                                    Iterator it = doc.select("td").iterator();
                                                    Log.d("mylogdoc",doc.select("td").toString());

                                                    Log.e("mylogIterator",it.toString());

                                                    Map<String, String> map22 = null;
                                                        while (it.hasNext()) {
                                                            //Elements elements = ((Element) it.next()).select("td");

                                                            Elements elements = ((Element) it.next()).select("td");
                                                            Log.e("mylogelements",elements.toString());
                                                            map2 = new HashMap();
                                                                map2.put(COURSETIME, elements.get(3).text());
                                                            Log.e("mylogCOURSETIME",elements.get(3).text());


                                                            map2.put(COURSENAME, elements.get(4).text());
                                                                map2.put(SCORE, elements.get(5).text());
                                                                map2.put(NATURE_OF_TEST, elements.get(11).text());

                                                                list.add(map2);
                                                                map22 = map2;
                                                        }
                                                        map2 = map22;



                                                    for (Map<String, String> m : list){
                                                        for (String k : m.keySet()){
                                                            System.out.println(k + " : " + m.get(k));
                                                            Log.e("mylog",k +m.get(k));

                                                        }

                                                    }*/



                                                    //map2.put(COURSETIME, elements.get(3).text());
                                                    //map2.put(COURSENAME, elements.get(4).text());
                                                    //map2.put(SCORE, elements.get(5).text());
                                                    //map2.put(NATURE_OF_TEST, elements.get(11).text());




                                            /*try {
                                                Document document = Jsoup.connect("http://jwxt.wust.edu.cn/whkjdx/xszqcjglAction.do?method=queryxscj")
                                                        .timeout(10000)
                                                        .get();
                                                Log.i("mylogdocument",document.toString());

                                                //Document.toString()

                                                Elements noteList = document.select("ul.note-list");
                                                Log.i("mylognoteList",noteList.toString());


                                                Elements li = noteList.select("li");
                                                Log.i("mylogli",li.toString());


                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }*/



                                                }
                                            });//成绩表界面结束








                                        }
                                    });//登录界面结束










                                }//主界面结束



                            });
                        }
                    }
                });
            }
        });

        return spacetext;

    }//import_Clicked,end


    private void getScheduleNow(){
        ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setTitle(getResources().getString(R.string.importing));
        waitingDialog.setMessage(getResources().getString(R.string.logging_to_jwc));
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    public void loadCaptcha(){
        Request request = new Request.Builder()
                .get()
                .url("http://jwxt.wust.edu.cn/whkjdx/verifycode.servlet")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hinttext.setText(getResources().getString(R.string.load_captcha_failed_click_to_reload));
                        ImageView captcha = findViewById(R.id.captchaImage);
                        captcha.setImageDrawable(getResources().getDrawable(R.drawable.ic_sync));
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.code()!=200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                                                                           //string.school_system_not_operational_with_code
                            hinttext.setText(String.format(getResources().getString(R.string.school_system_not_operational_with_code),response.code()));
                            ImageView captcha = findViewById(R.id.captchaImage);
                            captcha.setImageDrawable(getResources().getDrawable(R.drawable.ic_sync));
                        }
                    });
                    return;
                }
                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView captcha = findViewById(R.id.captchaImage);
                        captcha.setImageBitmap(bitmap);
                        hinttext.setText(getResources().getString(R.string.click_image_to_reload_captcha));
                        String[] separated = response.header("Set-Cookie").split(";");
                        lastJSESSIONID = separated[0];
                    }
                });
            }
        });
    }



    public  void matcherBysign(String str) {

        Matcher m=Pattern.compile("缪德浩.+?正常考试").matcher(str);
        while(m.find()) {
            //缪德浩2017-2018-1形势与政策94 通识教育平台课程必修322正常考试
            String courseA=m.group();

            //形势与政策94 通识教育平台课
            String courseAsubstring=courseA.substring(14,courseA.length()-10);

            //成绩
            String regex = "\\d+\\d";//匹配数字
            Matcher m3 = Pattern.compile(regex).matcher(courseAsubstring);

            while(m3.find()){
                //找到成绩,正则式匹配，94
                String courseScore=m3.group();
                //System.out.println("成绩: "+courseScore);

                //根据成绩找到课名，形势与政策
                String coursename=courseAsubstring.substring(0,m3.start());
                //System.out.println("课程名字：" + coursename);

                coursemap.put(coursename, courseScore);
                //coursemap.put(courseScore, coursename);
                //coursemap.put(SCORE, elements.get(5).text());
                //coursemap.put(NATURE_OF_TEST, elements.get(11).text());
            }
        }//匹配结束

        for (String key : coursemap.keySet()) {
            Log.i("key="+ key,"and value="+coursemap.get(key));
            //System.out.println("key= "+ key + " and value= " + coursemap.get(key));
        }

    }//matcherBysign,end

}
