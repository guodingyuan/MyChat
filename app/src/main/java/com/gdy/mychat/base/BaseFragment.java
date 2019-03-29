package com.gdy.mychat.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdy.mychat.R;
import com.gdy.mychat.utils.StatusBarUtil;


/**
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment{

    protected View rootView;					//Fragment显示的视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            initParams(getArguments());
        }
        setStatuBar();
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            bindView(rootView, savedInstanceState);
        }
        return rootView;
    }

    public void bindView(View rootView, Bundle savedInstanceState) {

    }
    protected void initParams(Bundle bundle) {

    }

    protected abstract int getLayoutId();

    public void setStatuBar() {
        StatusBarUtil.translucentBar(R.color.colorPrimaryDark, getActivity());
    }

}


















