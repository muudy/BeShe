package com.hihuyang.kb.timetable.myscore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hihuyang.kb.timetable.R;
import com.xinyu.utils.mylog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
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

@Route(path = "/comhihuyangkbtimetablemyscore/ScoreLoginActivity")
public class ScoreLoginActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();
    private TextView hinttext;
    private String lastJSESSIONID;
    private ProgressDialog waitingDialog;
    private String onDate;
    private String offDate;
    Context ctx = this;
    public  Map<String, String> coursemap=new HashMap<>();
    public static String spacetextAtScoreLoginActivity;
    public static String beforespacetext;
    public static String inputUsername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_score);

        getSupportActionBar().setTitle("查询成绩");

        int color = Color.parseColor("#2196F3");
        ColorDrawable colorDrawable=new ColorDrawable(color);
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        waitingDialog = new ProgressDialog(this);
        hinttext = findViewById(R.id.textView_score);
        final ImageView captcha = findViewById(R.id.captchaImage_score);

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


        SharedPreferences sharedPref = getSharedPreferences("USER_STORE",Context.MODE_PRIVATE);
        EditText usernameText = findViewById(R.id.username_text_score);
        EditText passwordText = findViewById(R.id.password_text_score);
        Button start_import_score=findViewById(R.id.start_import_score);
        usernameText.setText(sharedPref.getString("account_username", ""));
        passwordText.setText(sharedPref.getString("account_password", ""));
        loadCaptcha();

        start_import_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylog.I("start_import_score.setOnClickListener: ");
                findCourseandScore();

            }
        });


    }//onCreate


    public  String findCourseandScore() {

        if(lastJSESSIONID == null){
            return "1";
        }

        waitingDialog.setTitle(getResources().getString(R.string.importing));
        waitingDialog.setMessage(getResources().getString(R.string.logging_to_jwc));
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();

        EditText usernameText = findViewById(R.id.username_text_score);
        EditText passwordText = findViewById(R.id.password_text_score);
        EditText captchaText = findViewById(R.id.captcha_text_score);
        inputUsername = usernameText.getText().toString();
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
                                                    EditText usernameText = findViewById(R.id.username_text_score);
                                                    String inputUsername = usernameText.getText().toString();
                                                    //ToggleButton tgBtn = findViewById(R.id.toggleButton);

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

                                                    LogUtil.e("r5: ",r5);

                                                    Document doc = Jsoup.parse(r5);
                                                    beforespacetext=doc.text();

                                                    LogUtil.e("beforespacetext: ",beforespacetext);

                                                    //Log.d("mylogStringtext",text);


                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            //mylog.I("beforespacetext: "+beforespacetext);

                                                            spacetextAtScoreLoginActivity=beforespacetext.replace(" ", "");
                                                            LogUtil.e("spacetextAtScoreLoginActivity: ",spacetextAtScoreLoginActivity);
                                                            mylog.I("spacetext: "+spacetextAtScoreLoginActivity);

                                                            //Log.d("mylogspacetext",spacetext);
                                                            //matcherBysign(spacetext);

                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                                                            if(spacetextAtScoreLoginActivity==null&&beforespacetext==null){
                                                                builder.setMessage("请检查教务处状态，填写的学号与网络，并稍后再试")
                                                                        .setCancelable(false)
                                                                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int id) {
                                                                                waitingDialog.dismiss();
                                                                            }
                                                                        });
                                                            }

                                                            else{

                                                                builder.setMessage("已查询成绩")
                                                                        .setCancelable(false)
                                                                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int id) {
                                                                                waitingDialog.dismiss();

                                                                                //跳转操作
                                                                                if(spacetextAtScoreLoginActivity!=null){
                                                                                    ARouter.getInstance().build("/comhihuyangkbtimetablemyscore/ScoreActivityAttimetable").navigation();
                                                                                }

                                                                                finish();
                                                                            }
                                                                        });

                                                            }
                                                            AlertDialog alert = builder.create();
                                                            alert.show();





                                                        }
                                                    });


















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


        return spacetextAtScoreLoginActivity;

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
                        ImageView captcha = findViewById(R.id.captchaImage_score);
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
                            ImageView captcha = findViewById(R.id.captchaImage_score);
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
                        ImageView captcha = findViewById(R.id.captchaImage_score);
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
