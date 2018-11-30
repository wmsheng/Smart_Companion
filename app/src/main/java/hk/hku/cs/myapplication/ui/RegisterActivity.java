package hk.hku.cs.myapplication.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.entity.MyUser;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.ui
 *  文件名：    RegisterActivity
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:47
 *  描述：      注册界面
 */public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_email;
    private Button btnRegistered;
    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_user = findViewById(R.id.et_user);
        et_age = findViewById(R.id.et_age);
        et_desc = findViewById(R.id.et_desc);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        et_pass = findViewById(R.id.et_pass);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        btnRegistered = findViewById(R.id.btnRegistered);

        btnRegistered.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistered:
                //获取输入框的值
                String name = et_user.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(email) &
                        !TextUtils.isEmpty(pass) & !TextUtils.isEmpty(password)) {
                    //判断两次输入的密码是否一样
                    if(pass.equals(password)){
                        //先判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.rb_boy) {
                                    isGender = true;
                                } else {
                                    isGender = false;
                                }
                            }
                        });
                        //判断简介是否为空
                        if(TextUtils.isEmpty(desc)){
                            desc = "这个人很懒，什么都没有写";
                        }
                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);
                        user.setEmail(email);
                        user.setDes(desc);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null){
                                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this,"注册失败！" + e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(this, "两次输入密码不相同，请重新输入", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
