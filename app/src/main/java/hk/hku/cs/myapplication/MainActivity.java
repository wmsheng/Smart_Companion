package hk.hku.cs.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hk.hku.cs.myapplication.fragment.BulterFragment;
import hk.hku.cs.myapplication.fragment.CourierFragment;
import hk.hku.cs.myapplication.fragment.PhoneFragment;
import hk.hku.cs.myapplication.fragment.UserFragment;
import hk.hku.cs.myapplication.fragment.WechatFragment;
import hk.hku.cs.myapplication.ui.SettingActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;
    //悬浮窗
    private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影

        initData();
        initView();
    }

    //初始化数据
    private void initData() {
        mTitle = new ArrayList<String>();
        mTitle.add("情感伴侣");
        mTitle.add("物流查询");
        mTitle.add("电话查询");
        mTitle.add("个人中心");

        mFragment = new ArrayList<>();
        mFragment.add(new BulterFragment());
        mFragment.add(new CourierFragment());
        mFragment.add(new PhoneFragment());
        mFragment.add(new UserFragment());
    }

    //初始化View
    @SuppressLint("RestrictedApi")
    private void initView() {
        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.mViewPager);
        fab_setting = findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);

        //fab_setting对应的floatActionButton默认隐藏
        fab_setting.setVisibility(View.GONE);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int i) {
                if (i == 0 | i == 2){
                    fab_setting.setVisibility(View.GONE);
                }
                else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        //配置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int i) {
                return mFragment.get(i);
            }
            //返回item个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
            //设置标题
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定TabLayout和ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
