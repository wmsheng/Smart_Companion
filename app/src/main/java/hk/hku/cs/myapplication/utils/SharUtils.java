package hk.hku.cs.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.utils
 *  文件名：    SharUtils
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:43
 *  描述：      TODO
 */public class SharUtils {


    public static final String NAME = "config";
    //键值
    public static void putString(Context mContext, String key, String value){
        SharedPreferences sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    //默认值
    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    //键值
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences  sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    //默认值
    public static int getInt(Context mContext, String key, int defValue){
        SharedPreferences  sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    //键值
    public static void putBoolean(Context mContext,String key,boolean value){
        SharedPreferences  sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    //默认值
    public static boolean getBoolean(Context mContext, String key, boolean defValue){
        SharedPreferences  sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    //删除单个
    public static void deleShare(Context mContext,String key){
        SharedPreferences  sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //删除全部
    public static void deleAll(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences("NAME",Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
