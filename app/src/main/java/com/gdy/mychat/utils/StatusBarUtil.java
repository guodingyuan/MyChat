package com.gdy.mychat.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by wyk on 2016/9/2.
 * 描述:沉浸式(不需要改动)
 * 作用:
 */
public class StatusBarUtil {

    public static void translucentBar(int color, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = Global.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = Global.getResources().getDimensionPixelSize(resourceId);

            View rectView = new View(activity);
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            rectView.setBackgroundColor(Global.getColor(color));
            //rectView.setAlpha(0.92f);

            // 添加矩形View到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(rectView, -1);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

            //设置根布局延伸到状态栏,如不,布局被titleBar覆盖
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);

        }
    }

    private static int calculateStatusColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    public static void setStatusBarAlpha(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //   activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id
                    .content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

 /*   activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    if (contentView.getChildCount() > 1)
    {
        contentView.getChildAt(1).setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
    }*/


    public static View translucentBarAndViewColor(int color, FragmentActivity activity) {
        View rectView = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = Global.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = Global.getResources().getDimensionPixelSize(resourceId);

            rectView = new View(activity);
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            rectView.setBackgroundColor(Global.getColor(color));
            rectView.setAlpha(0.92f);

            // 添加矩形View到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(rectView, -1);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

            //设置根布局延伸到状态栏,如不,布局被titleBar覆盖
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);

        }
        return rectView;
    }


    /**
     * StatusBar Utils handle with special FlymeOS4.x/Android4.4.4
     * (状态栏工具,处理魅族FlymeOS4.x/Android4.4.4)
     */
    public static int getHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        if (isFlymeOs4x()) {
            return 2 * statusBarHeight;
        }

        return statusBarHeight;
    }

    public static boolean isFlymeOs4x() {
        String sysVersion = Build.VERSION.RELEASE;
        if ("4.4.4".equals(sysVersion)) {
            String sysIncrement = Build.VERSION.INCREMENTAL;
            String displayId = Build.DISPLAY;
            if (!TextUtils.isEmpty(sysIncrement)) {
                return sysIncrement.contains("Flyme_OS_4");
            } else {
                return displayId.contains("Flyme OS 4");
            }
        }
        return false;
    }

    /**
     * 修改状态栏为全透明，沉浸式，需在setContentView之前调用
     * @param activity
     */
    public static void transparencyBar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
