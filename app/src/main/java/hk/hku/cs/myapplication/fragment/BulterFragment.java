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
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.adapter.ChatListAdapter;
import hk.hku.cs.myapplication.entity.ChatListData;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.fragment
 *  文件名：    BulterFragment
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 14:06
 *  描述：      聊天机器人
 */public class BulterFragment extends Fragment implements View.OnClickListener {
    private ListView mChatListView;

    private List<ChatListData> mChatList = new ArrayList<>();

    private ChatListAdapter adapter;

    private EditText ed_send;

    private Button btn_send;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler,null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
        mChatListView = view.findViewById(R.id.mChatListView);
        ed_send = view.findViewById(R.id.ed_send);
        btn_send = view.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(this);
        //初始化适配器
        adapter = new ChatListAdapter(getActivity(),mChatList);
        mChatListView.setAdapter(adapter);

        addLeftItem("亲你好，最近怎么样？");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                String send_message = ed_send.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(send_message)){
                    //清除上一次发送后的对话框
                    ed_send.setText("");
                    addRightItem(send_message);
                    //定义URL
                    //根据图灵机器人接口进行
                    /*
                        String API_KEY = "2626b80c8de14eb191e7301ecda560c8";
                        String MI_YAO = "661d6226648a4d73";
                        String INTERFACE_ADDR = "http://openapi.tuling123.com/openapi/api/v2";
                     */
                    String url = "http://openapi.tuling123.com/openapi/api/v2";
                    String apikey = "2626b80c8de14eb191e7301ecda560c8";
                    String userID = "123456";

                    HttpParams params = new HttpParams();
                    params.putJsonParams("{\n" +
                            "\t\"reqType\":0,\n" +
                            "    \"perception\": {\n" +
                            "        \"inputText\": {\n" +
                            "            \"text\": \""+ send_message +"\"\n" +
                            "        },\n" +
                            "    },\n" +
                            "    \"userInfo\": {\n" +
                            "        \"apiKey\": \""+ apikey +"\",\n" +
                            "        \"userId\": \""+ userID + "\"\n" +
                            "    }\n" +
                            "}");

                    RxVolley.jsonPost(url, params, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //Toast.makeText(getActivity(),t,Toast.LENGTH_LONG).show();
                            //L.i(t);
                            paramsJSON(t);
                        }
                    });

                }else {
                    Toast.makeText(getActivity(),"亲，有啥想和我说的，所出来呀！",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void paramsJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0 ; i < jsonArray.length() ; i ++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                JSONObject jsonObject2 = jsonObject1.getJSONObject("values");
                String answer = jsonObject2.getString("text");
                addLeftItem(answer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //添加左边文本
    private void addLeftItem(String text){
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mChatList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }
    //添加右边文本
    private void addRightItem(String text){
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mChatList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
