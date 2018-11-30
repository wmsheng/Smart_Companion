package hk.hku.cs.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import hk.hku.cs.myapplication.MainActivity;
import hk.hku.cs.myapplication.R;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.ui
 *  文件名：    GuideActivity
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 13:29
 *  描述：      引导页
 */public class GuideActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mVirePager;

    //设置容器
    private List<View> mList = new ArrayList<>();
    private View view1,view2,view3;
    //设置小圆点
    private ImageView point1,point2,point3;
    //引导页跳转主页
    private Button button_start;
    private ImageView iv_skip;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    //初始化
    public void initView(){
        mVirePager = findViewById(R.id.mViewPager);

        point1 = findViewById(R.id.pointer1);
        point2 = findViewById(R.id.pointer2);
        point3 = findViewById(R.id.pointer3);
        //设置默认图片
        setPointImg(true,false,false);

        view1 = View.inflate(this,R.layout.pager_item_one,null);
        view2 = View.inflate(this,R.layout.pager_item_two,null);
        view3 = View.inflate(this,R.layout.pager_item_three,null);

        //直接调用的写法
        view3.findViewById(R.id.button_start).setOnClickListener(this);
        //传统先获取id再设置点击事件，但是因为这里需要设置第三页不可见，所以必须再全局获取到ID，必须这样写
        iv_skip = findViewById(R.id.iv_skip);
        iv_skip.setOnClickListener(this);
        //这样写不行：
        //view3.findViewById(R.id.iv_skip).setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //滑动监听
        mVirePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            //监听page切换
            @Override
            public void onPageSelected(int i) {
                //L.i("position:" + i);
                switch (i){
                    case 0:
                        setPointImg(true,false,false);
                        iv_skip.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false,true,false);
                        iv_skip.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false,false,true);
                        iv_skip.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //设置适配器
        mVirePager.setAdapter(new GuideAdapter());
    }


    //跳转主页监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start:
            case R.id.iv_skip:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }

    }

    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager)container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }

    public void setPointImg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
        if(isCheck1){
            point1.setBackgroundResource(R.drawable.point_on);
        }else{
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if(isCheck2){
            point2.setBackgroundResource(R.drawable.point_on);
        }else{
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if(isCheck3){
            point3.setBackgroundResource(R.drawable.point_on);
        }else{
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
