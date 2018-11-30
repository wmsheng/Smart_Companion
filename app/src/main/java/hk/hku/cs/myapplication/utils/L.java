package hk.hku.cs.myapplication.utils;

import android.util.Log;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.utils
 *  文件名：    L
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:34
 *  描述：      TODO
 */public class L {
    //开关
    private static final boolean DEBUG = true;
    //TAG
    private static final String TAG = "SmartButler";

    //四个等级  DIWE
    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }
}
