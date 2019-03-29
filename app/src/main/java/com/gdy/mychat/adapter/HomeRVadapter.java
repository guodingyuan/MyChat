package com.gdy.mychat.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gdy.mychat.R;
import com.gdy.mychat.utils.Global;
import com.noober.background.drawable.DrawableCreator;

import java.util.List;

public class HomeRVadapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private int[] colorArrays={R.color.color_green1,R.color.text_color_red,R.color.possible_result_points,R.color.grey,
            R.color.color_head_blue,R.color.color_orange,R.color.color_red,R.color.colorAccent};

    public HomeRVadapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.nameTV,item);
        View photoView = helper.getView(R.id.photo);
        if(photoView!=null){
            int i= (int) (Math.random()*colorArrays.length);
            Drawable drawable = new DrawableCreator.Builder()
                    .setSolidColor(Color.parseColor(Global.getResources().getString(colorArrays[i])))
                    .setCornersRadius(Global.dip2px(8))
                    .build();
            photoView.setBackground(drawable);
        }
    }
}
