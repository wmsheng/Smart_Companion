package hk.hku.cs.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.entity.MyUser;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.ui
 *  文件名：    ForgetPasswordActivity
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 12:45
 *  描述：      TODO
 */public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_email;
    private Button btn_forget_password;
    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    //初始化View
    private void initView() {
        et_email = findViewById(R.id.et_email);
        btn_forget_password = findViewById(R.id.btn_forget_password);
//        et_now = findViewById(R.id.et_now);
//        et_new = findViewById(R.id.et_new);
//        et_new_password = findViewById(R.id.et_new_password);
//        btn_update_password = findViewById(R.id.btn_update_password);

//        btn_update_password.setOnClickListener(this);
        btn_forget_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn_update_password:
//                //1.获取输入的旧密码和新输入的密码
//                String nowpassword = et_now.getText().toString().trim();
//                String newpass = et_new.getText().toString().trim();
//                String newpassword = et_new_password.getText().toString().trim();
//                //2.判断是否为空
//                if(!TextUtils.isEmpty(nowpassword) & !TextUtils.isEmpty(newpass) & !TextUtils.isEmpty(newpassword)){
//                    //3.判断两次输入密码是否一致
//                    if(newpass.equals(newpassword)){
//                        //4.重置密码
//                        MyUser.updateCurrentUserPassword(nowpassword, newpassword, new UpdateListener() {
//                            @Override
//                            public void done(BmobException e) {
//                                if(e == null){
//                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功！可以用新密码登陆啦",
//                                            Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }else {
//                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败" + e, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }else {
//                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
//                }
//                break;

            case R.id.btn_forget_password:
                //1.获取输入的邮箱内容，写在了上面
                final String email = et_email.getText().toString().trim();
                //2.判断输入是否为空
                if(!TextUtils.isEmpty(email)){
                    //3.发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ForgetPasswordActivity.this, "邮件已经发送，请到"
                                        + email+"进行密码重置", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(ForgetPasswordActivity.this, "请求失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
