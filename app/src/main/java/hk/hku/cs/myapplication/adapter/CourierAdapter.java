package hk.hku.cs.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hk.hku.cs.myapplication.R;
import hk.hku.cs.myapplication.entity.CourierData;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.adapter
 *  文件名：    CourierAdapter
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 12:47
 *  描述：      TODO
 */public class CourierAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourierData> mList;
    private CourierData data;

    //布局加载器
    private LayoutInflater inflater;


    //构造函数
    public CourierAdapter(Context mContext,List<CourierData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统微服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //判断是否是第一次调用
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_zone = convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_remark = convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_datetime = convertView.findViewById(R.id.tv_timeline);
            //设置缓存
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tv_datetime.setText(data.getDatatime());
        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());

        return convertView;
    }

    class ViewHolder{
        private TextView tv_zone;
        private TextView tv_remark;
        private TextView tv_datetime;
    }
}
