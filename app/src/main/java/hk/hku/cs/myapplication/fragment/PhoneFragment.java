package hk.hku.cs.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.utils.StaticClass;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.fragment
 *  文件名：    PhoneFragment
 *  创建者：    BENNETT
 *  创建时间：  2018/11/27 23:17
 *  描述：      TODO
 */public class PhoneFragment extends Fragment implements View.OnClickListener {
    //输入手机号
    private EditText et_phone_number;
    //对应公司LOGO
    private ImageView iv_phone_result;
    //查询结果
    private TextView tv_phone_result;
    //按钮
    private Button btn_phone_1, btn_phone_2, btn_phone_3, btn_phone_4, btn_phone_5, btn_phone_6, btn_phone_7, btn_phone_8,
            btn_phone_9, btn_phone_0, btn_phone_del, btn_phone_query;

    //标记位,查找成功后清空内容
    private boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phone,null);
        findview(view);
        return view;
    }

    private void findview(View v) {
        et_phone_number = v.findViewById(R.id.et_phone_number);
        iv_phone_result = v.findViewById(R.id.iv_phone_result);
        tv_phone_result = v.findViewById(R.id.tv_phone_result);

        btn_phone_1 = v.findViewById(R.id.btn_phone_1);
        btn_phone_1.setOnClickListener(this);
        btn_phone_2 = v.findViewById(R.id.btn_phone_2);
        btn_phone_2.setOnClickListener(this);
        btn_phone_3 = v.findViewById(R.id.btn_phone_3);
        btn_phone_3.setOnClickListener(this);
        btn_phone_4 = v.findViewById(R.id.btn_phone_4);
        btn_phone_4.setOnClickListener(this);
        btn_phone_5 = v.findViewById(R.id.btn_phone_5);
        btn_phone_5.setOnClickListener(this);
        btn_phone_6 = v.findViewById(R.id.btn_phone_6);
        btn_phone_6.setOnClickListener(this);
        btn_phone_7 = v.findViewById(R.id.btn_phone_7);
        btn_phone_7.setOnClickListener(this);
        btn_phone_8 = v.findViewById(R.id.btn_phone_8);
        btn_phone_8.setOnClickListener(this);
        btn_phone_9 = v.findViewById(R.id.btn_phone_9);
        btn_phone_9.setOnClickListener(this);
        btn_phone_0 = v.findViewById(R.id.btn_phone_0);
        btn_phone_0.setOnClickListener(this);
        btn_phone_del = v.findViewById(R.id.btn_phone_del);
        btn_phone_del.setOnClickListener(this);
        btn_phone_query = v.findViewById(R.id.btn_phone_query);
        btn_phone_query.setOnClickListener(this);

        //设置长按del键清空
        btn_phone_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_phone_number.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        //1.获取输入内容
        String phone_number = et_phone_number.getText().toString().trim();

        switch (v.getId()) {
            case R.id.btn_phone_0:
            case R.id.btn_phone_1:
            case R.id.btn_phone_2:
            case R.id.btn_phone_3:
            case R.id.btn_phone_4:
            case R.id.btn_phone_5:
            case R.id.btn_phone_6:
            case R.id.btn_phone_7:
            case R.id.btn_phone_8:
            case R.id.btn_phone_9:
                if (flag) {
                    flag = false;
                    phone_number = "";
                    et_phone_number.setText("");
                }
                //每次输入号码结尾添加1
                et_phone_number.setText(phone_number + ((Button) v).getText());
                //移动光标
                et_phone_number.setSelection(phone_number.length() + 1);
                break;
            case R.id.btn_phone_del:
                //判断是否为空
                if (!TextUtils.isEmpty(phone_number) && phone_number.length() > 0) {
                    //每次结尾减去1
                    et_phone_number.setText(phone_number.substring(0, phone_number.length() - 1));
                    //移动光标
                    et_phone_number.setSelection(phone_number.length() - 1);
                }
                break;
            case R.id.btn_phone_query:
                if (!TextUtils.isEmpty(phone_number)) {
                    getinfo(phone_number);
                }
                break;
        }
    }
    //获取号码相关的归属地信息
    private void getinfo(String phone_number) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + phone_number + "&key=" + StaticClass.PHONE_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(PhoneActivity.this,"结果"+t , Toast.LENGTH_SHORT).show();
                parseJson(t);
            }
        });
    }
    //解析JSON文件
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");

            tv_phone_result.setText("归属地：" + province + city + "\n"
                    +"区号：" + areacode + "\n"
                    + "邮编：" + zip + "\n"
                    + "运营商：" + company + "\n");
            //运营商显示
            switch (company) {
                case "移动":
                    iv_phone_result.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    iv_phone_result.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    iv_phone_result.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
