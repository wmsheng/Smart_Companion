package hk.hku.cs.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.utils.SharUtils;
import hk.hku.cs.myapplication.utils.StaticClass;
import hk.hku.cs.myapplication.utils.UtilTools;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.ui
 *  文件名：    SplashActivity
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:52
 *  描述：      TODO
 */public class SplashActivity extends AppCompatActivity {
    /*
       1.延时2000ms
       2.判断程序是否第一次运行
       3.自定义字体
       4.Activity全屏主题
    */
    private TextView tv_splash;
    //用Handler延时
    private MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv_splash = (TextView) findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this,tv_splash);
    }
    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst =  SharUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            //是第一次运行
            SharUtils.putBoolean(SplashActivity.this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断是否第一次运行
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
