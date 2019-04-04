package com.gdy.mychat.utils;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.widget.FrameLayout;

import com.gdy.mychat.wedgit.MyBadge;

/**
 *   BottomNavigationView取消位移动画(菜单数大于3个)
 */
public class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        menuView.setLabelVisibilityMode(1);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            item.setShifting(false);

        }
    }

    /**
     * 设置第几个下巴是否显示角标
     */
    public static void setBadge(BottomNavigationView bottomNavigationView,int i,int num){
        //获取整个的NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
        for(int a=0;a<itemView.getChildCount();a++){
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) itemView.getChildAt(a).getLayoutParams();

            Log.e("gdy","左边距："+layoutParams.leftMargin+"，右边距："+layoutParams.rightMargin);
            Log.e("gdy",itemView.getChildAt(a).getClass().getName()+",距离："+layoutParams.width+"，边距："+itemView.getChildAt(a).getLeft());
        }
        //先判断是否存在角标
        if(!(itemView.getChildAt(itemView.getChildCount()-1) instanceof MyBadge)){
            MyBadge myBadge=new MyBadge(bottomNavigationView.getContext());
            itemView.addView(myBadge);//不存在就添加角标
        }
        MyBadge myBadge = (MyBadge) itemView.getChildAt(itemView.getChildCount()-1);
        myBadge.setNum(num);
    }

}
