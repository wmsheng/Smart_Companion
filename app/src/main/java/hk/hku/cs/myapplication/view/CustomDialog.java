package hk.hku.cs.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import hk.hku.cs.myapplication.R;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.view
 *  文件名：    CustomDialog
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:36
 *  描述：      TODO
 */public class CustomDialog extends Dialog{
    //定义模板
    public CustomDialog(Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }

    //定义属性
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim){
        super(context, style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams= window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setWindowAnimations(anim);
        window.setAttributes(layoutParams);
    }

    //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context,width,height,layout,style,gravity, R.style.pop_anim_style);
    }
}
