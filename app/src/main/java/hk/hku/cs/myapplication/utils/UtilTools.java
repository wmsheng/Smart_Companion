package hk.hku.cs.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.utils
 *  文件名：    UtilTools
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:35
 *  描述：      TODO
 */public class UtilTools {
    //设置字体
    public static void setFont(Context mContext, TextView mTextView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        mTextView.setTypeface(fontType);
    }

    //保存图片到ShareUtils
    public static void putImageToShare(Context mContext, ImageView imageView){
        //拿到图片
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //第二步：利用Base64将字节数组输出流转换成Stream
        byte [] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //第三步：将String 保存在shareUtils中
        SharUtils.putString(mContext,"image_title",imgString);
    }

    //获取图片
    public static void getImageToShare(Context mContext, ImageView imageView){
        //1.拿到String
        String imgString = SharUtils.getString(mContext,"image_title","");
        if (!imgString.equals("")){
            //2.利用Base64将String转换
            byte [] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);

        }
    }
}
