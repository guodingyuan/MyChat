package com.gdy.mychat.utils;

import android.content.Context;
import android.content.res.Resources;

public class Global {

    private static Context mContext;//全局的上下文

    public static void init(Context context) {
        mContext = context;
    }

    /** 提供全局的上下文变量*/
    public static Context getContext(){
        return mContext;
    }

    /** 获取Resources*/
    public static Resources getResources(){
        return mContext.getResources();
    }

    public static int getColor(int colId){
        return getResources().getColor(colId);
    }

    /**
     * dp转px
     */
    public static int dip2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
