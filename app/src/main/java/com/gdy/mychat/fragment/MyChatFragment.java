package com.gdy.mychat.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gdy.mychat.MainActivity;
import com.gdy.mychat.R;
import com.gdy.mychat.adapter.HomeRVadapter;
import com.gdy.mychat.base.BaseFragment;
import com.gdy.mychat.wedgit.pullextend.ExtendLayout;
import com.gdy.mychat.wedgit.pullextend.ExtendListFooter;
import com.gdy.mychat.wedgit.pullextend.ExtendListHeader;
import com.gdy.mychat.wedgit.pullextend.IExtendLayout;
import com.gdy.mychat.wedgit.pullextend.PullExtendLayout;

import java.util.Arrays;
import java.util.List;


public class MyChatFragment extends BaseFragment {

    private MainActivity mainActivity;

    public MyChatFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_chat;
    }

    @Override
    public void bindView(View rootView, Bundle savedInstanceState) {
        super.bindView(rootView, savedInstanceState);
        mainActivity= (MainActivity) getActivity();

        String[] name={"二狗子","二牛","傻蛋","三多","二狗子","来福","旺财","大傻","刘二根","喜娃","小样","喜洋洋","比卡丘","钢铁侠","三毛","七龙珠"};
        List<String> list=Arrays.asList(name);
        HomeRVadapter homeRVadapter=new HomeRVadapter(R.layout.home_chat_item,list);
        RecyclerView myRV = rootView.findViewById(R.id.myRV);
        myRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRV.setAdapter(homeRVadapter);

        ExtendListFooter extendFooter = rootView.findViewById(R.id.extend_footer);
        RecyclerView extendFooterRV = extendFooter.getRecyclerView();
        extendFooterRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        String[] footName={"脚程序1","脚程序2","脚程序3","脚程序4","脚程序5","脚程序6"};
        List<String> footList=Arrays.asList(footName);
        HomeRVadapter footRVadapter=new HomeRVadapter(R.layout.home_program_item,footList);
        extendFooterRV.setAdapter(footRVadapter);


        final PullExtendLayout pullExtend = rootView.findViewById(R.id.pull_extend);
        final ExtendListHeader extendListHeader=rootView.findViewById(R.id.extend_header);
        extendListHeader.setStateLayout(new ExtendLayout.StateLayout() {
            @Override
            public void onStateChange(IExtendLayout.State state) {
                switch (state){
                    case arrivedListHeight:
                        mainActivity.setNavigationVisibility(false);
                        break;
                    case RESET:
                        mainActivity.setNavigationVisibility(true);
                        break;
                }
            }
        });
    }
}
