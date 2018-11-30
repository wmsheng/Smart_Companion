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
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.adapter.CourierAdapter;
import hk.hku.cs.myapplication.entity.CourierData;
import hk.hku.cs.myapplication.utils.StaticClass;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.fragment
 *  文件名：    CourierFragment
 *  创建者：    BENNETT
 *  创建时间：  2018/11/27 23:16
 *  描述：      快递查询
 */public class CourierFragment extends Fragment implements View.OnClickListener {

    private Button btn_get_id;
    private EditText et_number;
    private EditText et_name;
    private ListView mListView;
    private List<CourierData> mList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_courier,null);
        findview(view);
        return view;
    }

    private void findview(View v) {
        et_number = v.findViewById(R.id.et_number);
        et_name = v.findViewById(R.id.et_name);
        btn_get_id = v.findViewById(R.id.btn_get_id);
        mListView = v.findViewById(R.id.mListView);
        btn_get_id.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_id:
                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接url
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY + "&com=" + name +
                        "&no=" + number;
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //3.拿到输入的数据去请求数据（Json）
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
                            //4.解析Json
                            parseJson(t);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonOject1 = jsonObject.getJSONObject("result");
            JSONArray jsonArray1 = jsonOject1.getJSONArray("list");
            for (int i = 0; i< jsonArray1.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);

                CourierData data = new CourierData();
                data.setDatatime(jsonObject1.getString("datetime"));
                data.setRemark(jsonObject1.getString("remark"));
                data.setZone(jsonObject1.getString("zone"));
                mList.add(data);
            }
            CourierAdapter adapter = new CourierAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
