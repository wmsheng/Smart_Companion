package hk.hku.cs.myapplication.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import hk.hku.cs.myapplication.utils.StaticClass;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.application
 *  文件名：    BaseActivity
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:33
 *  描述：      TODO
 */public class BaseActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
