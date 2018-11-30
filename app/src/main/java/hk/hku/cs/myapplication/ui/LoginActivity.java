package hk.hku.cs.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import hk.hku.cs.myapplication.MainActivity;
import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.entity.MyUser;
import hk.hku.cs.myapplication.utils.L;
import hk.hku.cs.myapplication.utils.SharUtils;
import hk.hku.cs.myapplication.view.CustomDialog;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.ui
 *  文件名：    LoginActivity
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:31
 *  描述：      用户登陆
 */public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //注册按钮
    private Button btn_register;
    //登陆按钮
    private Button btn_login;
    //用户名
    private EditText user_name;
    //密码
    private EditText user_password;
    //用户
    private MyUser user = new MyUser();
    //记住密码
    private CheckBox keep_password;
    //忘记密码
    private TextView tv_forget;
    //登陆时弹出来的Dialog
    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        user_name = findViewById(R.id.et_Name);
        user_password = findViewById(R.id.et_Password);
        tv_forget = findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        dialog = new CustomDialog(this,100,100,R.layout.dialog_loding,R.style.Theme_dialog,
                Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        keep_password = findViewById(R.id.keep_password);
        //设置选中的状态
        boolean ischeck = SharUtils.getBoolean(this,"keeppassword",false);
        keep_password.setChecked(ischeck);
        if(ischeck){
            user_name.setText(SharUtils.getString(this,"name",""));
            user_password.setText(SharUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                //获取输入框的值
                String name = user_name.getText().toString().trim();
                String password = user_password.getText().toString();
                //判断是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)){
                    dialog.show();
                    //登陆
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if(e == null){
                                L.i("eee");
                                //判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    //已经验证，跳转至主页面
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this,"登陆失败" + e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    //存在一种可能就是用户输入了账号密码但是没有登陆就退出了，这时候不能保存
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        SharUtils.putBoolean(this,"keeppassword",keep_password.isChecked());
        //是否记住密码
        if(keep_password.isChecked()){
            //记住账号和密码
            SharUtils.putString(this,"name",user_name.getText().toString().trim());
            SharUtils.putString(this,"password",user_password.getText().toString().trim());
        }else{
            SharUtils.deleShare(this,"name");
            SharUtils.deleShare(this,"password");
        }
    }
}

