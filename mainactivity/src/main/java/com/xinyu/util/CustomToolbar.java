package com.xinyu.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xinyu.activity.R;


/**
 * Created by Administrator on 2016/3/18.
 */
public class CustomToolbar extends RelativeLayout implements View.OnClickListener {

    TextView tv_left;
    TextView tv_center;
    TextView tv_right;
    int left_img_resid=0;
    String left_text=null;
    int right_img_resid=0;
    String right_text= null;
    int mDrawablePadding=5;//默认
    TitleBarClickListener titleBarClickListener;
    Context mContext;
    RelativeLayout rl_titlebar;
    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        View view = LayoutInflater.from(context).inflate(R.layout.toolbar1, this);

        tv_left = (TextView) view.findViewById(R.id.id_tv_left);
        tv_left.setOnClickListener(this);
        tv_center = (TextView) view.findViewById(R.id.id_tv_center);
        tv_center.setOnClickListener(this);
        tv_right = (TextView) view.findViewById(R.id.id_tv_right);
        tv_right.setOnClickListener(this);
        rl_titlebar= (RelativeLayout) findViewById(R.id.id_rl_titlebar);

    }

    public void setTitleBarClickListener(TitleBarClickListener titleBarClickListener) {
        this.titleBarClickListener = titleBarClickListener;
    }

    public interface TitleBarClickListener {
        void leftClick();

        void centerClick();

        void rightClick();
    }

    protected void setTitleBarBackgroundColor(Object colorOrDrawable){
        if(colorOrDrawable instanceof Integer){
            rl_titlebar.setBackgroundColor((Integer) colorOrDrawable);
        }else if(colorOrDrawable instanceof Drawable){
            this.setDrawableByVersion(rl_titlebar,(Drawable) colorOrDrawable);
        }
    }
    protected void setCenterText(String text){
        tv_center.setText(text);
    }
    protected void setCenterTextSize(float size){
        tv_center.setTextSize(size);
    }
    protected void setCenterTextColor(Object colorOrColorStateList){
        if(colorOrColorStateList instanceof Integer) {
            tv_center.setTextColor((Integer) colorOrColorStateList);
        }else if(colorOrColorStateList instanceof ColorStateList) {
            tv_center.setTextColor((ColorStateList) colorOrColorStateList);
        }
    }
    protected void setLeftAndRightTextSize(float size){
        tv_left.setTextSize(size);
        tv_right.setTextSize(size);
    }
    protected void setLeftAndRightTextColor(Object colorOrColorStateList){
        if(colorOrColorStateList instanceof Integer) {
            tv_left.setTextColor((Integer) colorOrColorStateList);
            tv_right.setTextColor((Integer) colorOrColorStateList);
        }else if(colorOrColorStateList instanceof ColorStateList) {
            tv_left.setTextColor((ColorStateList) colorOrColorStateList);
            tv_right.setTextColor((ColorStateList) colorOrColorStateList);
        }
    }
    protected void setCenterTextBackground(Object colorOrDrawable){
        if(colorOrDrawable instanceof Integer){
            tv_center.setBackgroundColor((Integer) colorOrDrawable);
        }else if(colorOrDrawable instanceof Drawable){
            this.setDrawableByVersion(tv_center,(Drawable) colorOrDrawable);
        }
    }
    protected void setBarLeft(int imgResID,String tv_text){
        left_img_resid=imgResID;
        left_text=tv_text;
        setBarLeftConfig();
    }
    protected void setBarLeft(int imgResID){
        left_img_resid=imgResID;
        left_text="";
        setBarLeftConfig();
    }
    protected void setBarLeft(String tv_text){
        left_text=tv_text;
        left_img_resid=0;
        setBarLeftConfig();
    }

    private void setBarLeftConfig(){
        if (left_text!=null){
            tv_left.setText(left_text);
        }
        if (left_img_resid!=0){
            Drawable drawable=getDrawableByVersion(left_img_resid,mContext);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_left.setCompoundDrawables(drawable, null, null, null);//设置TextView的drawableleft
            tv_left.setCompoundDrawablePadding(mDrawablePadding);//设置图片和text之间的间距
        }else
        {
            tv_left.setCompoundDrawables(null, null, null, null);//设置TextView的drawableleft
            tv_left.setCompoundDrawablePadding(0);
        }
    }
    protected void setBarRight(int imgResID,String tv_text){
        right_img_resid=imgResID;
        right_text=tv_text;
        setBarRightConfig();
    }
    protected void setBarRight(int imgResID){
        right_img_resid=imgResID;
        right_text="";
        setBarRightConfig();
    }
    protected void setBarRight(String tv_text){
        right_img_resid=0;
        right_text=tv_text;
        setBarRightConfig();
    }

    private void setBarRightConfig(){
        if (right_text!=null){
            tv_right.setText(right_text);
        }
        if (right_img_resid!=0){
            Drawable drawable=getDrawableByVersion(right_img_resid,mContext);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_right.setCompoundDrawables(null, null, drawable, null);//设置TextView的drawableleft
            tv_right.setCompoundDrawablePadding(mDrawablePadding);//设置图片和text之间的间距
        }else
        {
            tv_right.setCompoundDrawables(null, null, null, null);//设置TextView的drawableleft
            tv_right.setCompoundDrawablePadding(0);
        }
    }

    private Drawable getDrawableByVersion(int resid, Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resid, context.getTheme());
        } else {
            return context.getResources().getDrawable(resid);
        }
    }
    private void setDrawableByVersion(View view,Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        }else{
            view. setBackgroundDrawable(drawable);
        }
    }
    @Override
    public void onClick(View v) {

            int id = v.getId();

            if (id == R.id.id_tv_left) {
                titleBarClickListener.leftClick();

            } else if (id == R.id.id_tv_center) {
                titleBarClickListener.centerClick();
            } else if (id == R.id.id_tv_right) {
                titleBarClickListener.rightClick();
            } else  ;
    }

    public  void init(String titleText,int left_img_resid,String left_text,int right_img_resid,String right_text,int drawablePadding){
        tv_center.setText(titleText);
        mDrawablePadding=drawablePadding;
        if (left_img_resid!=0) {
            this.setBarLeft(left_img_resid, left_text);
        }else {
            this.setBarLeft(left_text);
        }
        if (right_img_resid!=0) {
            this.setBarRight(right_img_resid, right_text);
        }else {
            this.setBarRight(right_text);
        }
    }
}