package com.xinyu.util;

import android.util.Log;

public class mylog {
    private static final String TAG = "mylog";
    public static boolean Verbose = true;
    public static boolean Debug = true;
    public static boolean Info = true;//false
    public static boolean Warn = true;
    public static boolean Error = true;
    /*
    mylog.I("log:");

    Log.i("mylog", "mylog:");
    */

    public static void V(String info) {
        if (Verbose) {
            String[] infos = getAutoJumpLogInfos();
            Log.v(TAG, info + " where-" + infos[2]);
        }
    }

    public static void D(String info) {
        if (Debug) {
            String[] infos = getAutoJumpLogInfos();
            Log.d(TAG, info + " where-" + infos[2]);
        }
    }

    public static void I(String info) {
        if (Info) {
            String[] infos = getAutoJumpLogInfos();
            Log.i(TAG, info + " where-" + infos[2]);
        }
    }

    public static void W(String info) {
        if (Warn) {
            String[] infos = getAutoJumpLogInfos();
            Log.w(TAG, info + " where-" + infos[2]);
        }
    }

    public static void E(String info) {
        if (Error) {
            String[] infos = getAutoJumpLogInfos();
            Log.e(TAG, info + " where-" + infos[2]);
        }
    }

    /**
     * 显示Log信息（带行号）
     *
     * @param logLevel 1 v ; 2 d ; 3 i ; 4 w ; 5 e .
     * @param info     显示的log信息
     */
    public static void showLogWithLineNum(int logLevel, String info) {
        String[] infos = getAutoJumpLogInfos();
        switch (logLevel) {
            case 1:
                if (Verbose)
                    Log.v(infos[0], info + " : " + infos[1] + infos[2]);
                break;
            case 2:
                if (Debug)
                    Log.d(infos[0], info + " : " + infos[1] + infos[2]);
                break;
            case 3:
                if (Info)
                    Log.i(infos[0], info + " : " + infos[1] + infos[2]);
                break;
            case 4:
                if (Warn)
                    Log.w(infos[0], info + " : " + infos[1] + infos[2]);
                break;
            case 5:
                if (Error)
                    Log.e(infos[0], info + " : " + infos[1] + infos[2]);
                break;
        }
    }

    /**
     * 获取打印信息所在方法名，行号等信息
     *2018.01.08写
     * @return
     */
    private static String[] getAutoJumpLogInfos() {
        String[] infos = new String[]{"", "", ""};
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        //Log.e("标志+elements", ""+elements[4]);
        //Log.e("标志+elements", ""+elements[4].getClassName());
        //elements[4].getClassName()

        if (elements.length < 5) {
            Log.e("mylog", "elements.length < 5,Stack is too shallow!!!");
            return infos;
        } else {
            infos[0] = elements[4].getClassName().substring(
                    elements[4].getClassName().lastIndexOf(".") + 1);
            infos[1] = elements[4].getMethodName() + "()";
            infos[2] = "at(" + elements[4] + ".java:"
                    + elements[4].getLineNumber() + ")";
            return infos;
        }
    }

}
