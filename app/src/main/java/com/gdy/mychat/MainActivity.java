package com.gdy.mychat;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.gdy.mychat.adapter.MainHomePagerAdapter;
import com.gdy.mychat.base.BaseFragment;
import com.gdy.mychat.fragment.MyChatFragment;
import com.gdy.mychat.utils.BottomNavigationViewHelper;
import com.gdy.mychat.wedgit.NoScrollViewPager;
import com.noober.background.BackgroundLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int i = item.getItemId();
            mPager.setCurrentItem(idList.indexOf(i),false);
            return true;
        }

    };
    private NoScrollViewPager mPager;
    private BottomNavigationView navigation;
    private List<BaseFragment> mFragments;
    private Integer[] idArray={R.id.navigation_chat, R.id.navigation_address_list,R.id.navigation_find,R.id.navigation_me};
    private List<Integer> idList=Arrays.asList(idArray);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        mPager = findViewById(R.id.container_pager);
        mFragments = new ArrayList<>();
        mFragments.add(new MyChatFragment());
        mFragments.add(new MyChatFragment());
        mFragments.add(new MyChatFragment());
        mFragments.add(new MyChatFragment());
        MainHomePagerAdapter mAdapter = new MainHomePagerAdapter(getSupportFragmentManager(), mFragments);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(idList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void setNavigationVisibility(boolean isVISIBLE) {
        if(isVISIBLE){
            navigation.setVisibility(View.VISIBLE);
            mPager.setPagerEnabled(true);//允许滑动
        }else {
            navigation.setVisibility(View.GONE);
            mPager.setPagerEnabled(false);//禁止滑动
        }
    }
}
