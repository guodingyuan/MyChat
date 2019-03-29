package com.gdy.mychat.wedgit.pullextend;


import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gdy.mychat.R;
import com.gdy.mychat.adapter.HomeRVadapter;
import com.gdy.mychat.utils.Global;

import java.util.Arrays;
import java.util.List;


/**
 * 这个类封装了下拉刷新的布局
 */
public class ExtendListHeader extends ExtendLayout {

    private Context context;
    float containerHeight = Global.dip2px(60);
    float listHeight/* = Global.dip2px(460)*/;
    boolean arrivedListHeight = false;
    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private LinearLayout canSeeLL;

    /**
     * 原点
     */

    private ExpendPoint mExpendPoint;
    private ImageView upIV;
    private NestedScrollView mNestedScrollView;

    /**
     * 构造方法
     *
     * @param context context
     */
    public ExtendListHeader(Context context) {
        super(context);
        init(context);
    }


    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public ExtendListHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context){
        this.context=context;
        //获取屏幕的高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        listHeight = dm.heightPixels;//必须先这样暂定为屏幕高度
    }

    @Override
    public void restListSize(int h) {
        super.restListSize(h);
        listHeight=h;
        //Log.e("gdy","当前高度哈哈哈哈哈："+listHeight);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams1 = getLayoutParams();
                //Log.e("gdy","改变前1："+layoutParams1.height);
                layoutParams1.height= (int) listHeight;
                setLayoutParams(layoutParams1);
                ViewGroup.LayoutParams layoutParams = canSeeLL.getLayoutParams();
                //Log.e("gdy","改变前2："+layoutParams.height);
                layoutParams.height= (int) listHeight;
                canSeeLL.setLayoutParams(layoutParams);
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    @Override
    protected void bindView(View container) {
        mRecyclerView1 = findViewById(R.id.list1);
        mRecyclerView2 = findViewById(R.id.list2);
        mExpendPoint = findViewById(R.id.expend_point);
        upIV = findViewById(R.id.upIV);
        upIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resetLayout!=null){
                    resetLayout.onRest();
                }
            }
        });
        mNestedScrollView = findViewById(R.id.mNestedScrollView);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        String[] name={"大程序1","大程序2","大程序3","大程序4","大程序5","大程序6"};
        List<String> list=Arrays.asList(name);
        HomeRVadapter homeRVadapter=new HomeRVadapter(R.layout.home_program_item,list);
        mRecyclerView1.setAdapter(homeRVadapter);
        mRecyclerView2.setAdapter(homeRVadapter);
        /*  mRecyclerView1.setNestedScrollingEnabled(false);
        mRecyclerView2.setNestedScrollingEnabled(false);*/
        childScrollView=mNestedScrollView;
        canSeeLL=findViewById(R.id.canSeeLL);
    }


    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        return LayoutInflater.from(context).inflate(R.layout.extend_header, null);
    }


    @Override
    public int getContentSize() {
        return (int) (containerHeight);
    }

    @Override
    public int getListSize() {
        return (int) (listHeight);
    }


    @Override
    protected void onReset() {
        mExpendPoint.setVisibility(VISIBLE);
        mExpendPoint.setAlpha(1);
        mExpendPoint.setTranslationY(0);
        childScrollView.setTranslationY(0);
        arrivedListHeight = false;
        upIV.setVisibility(GONE);
        if(stateLayout!=null){
            stateLayout.onStateChange(State.RESET);
        }
    }

    @Override
    protected void onReleaseToRefresh() {

    }

    @Override
    protected void onPullToRefresh() {

    }
    private int nestedScrollViewTop;
    @Override
    protected void onArrivedListHeight() {
        arrivedListHeight = true;
        upIV.setVisibility(View.VISIBLE);

        if(nestedScrollViewTop==0){
            int[] intArray=new int[2];
            mNestedScrollView.getLocationOnScreen(intArray);
            nestedScrollViewTop=intArray[1];
        }
        //滑动到canSeeLL的位置
        int[] intArray=new int[2];
        canSeeLL.getLocationOnScreen(intArray);//测量某View相对于屏幕的距离
        int distance=intArray[1]-nestedScrollViewTop;
        Log.e("gdy","当前位置："+distance);
        mNestedScrollView.fling(distance);//添加上这句滑动才有效
        mNestedScrollView.scrollBy(0,distance);

        if(stateLayout!=null){
            stateLayout.onStateChange(State.arrivedListHeight);
        }
    }

    @Override
    protected void onRefreshing() {
    }

    @Override
    public void onPull(int offset) {
        if (!arrivedListHeight) {
            mExpendPoint.setVisibility(VISIBLE);
            float percent = Math.abs(offset) / containerHeight;
            int moreOffset = Math.abs(offset) - (int) containerHeight;
            if (percent <= 1.0f) {
                mExpendPoint.setPercent(percent);
                mExpendPoint.setTranslationY(-Math.abs(offset) / 2 + mExpendPoint.getHeight() / 2);
                childScrollView.setTranslationY(-containerHeight);
            } else {
                float subPercent = (moreOffset) / (listHeight - containerHeight);
                subPercent = Math.min(1.0f, subPercent);
                mExpendPoint.setTranslationY(-(int) containerHeight / 2 + mExpendPoint.getHeight() / 2 + (int) containerHeight * subPercent / 2);
                mExpendPoint.setPercent(1.0f);
                float alpha = (1 - subPercent * 2);
                mExpendPoint.setAlpha(Math.max(alpha, 0));
                childScrollView.setTranslationY(-(1 - subPercent) * containerHeight);
            }
        }
        if (Math.abs(offset) >= listHeight) {
            mExpendPoint.setVisibility(GONE);
            childScrollView.setTranslationY(-(Math.abs(offset) - listHeight) / 2);
        }
    }


}
