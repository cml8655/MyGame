package com.cml.second.app.baby.fragment;

import android.os.Bundle;
import android.view.View;

import com.cml.second.app.baby.R;

/**
 * Created by cmlBeliever on 2016/3/15.
 */
public class GameFragment extends BaseFragment {

//    @Bind(R.id.slide_menu)
//    SlideMenu menu;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        TextView textView = new TextView(getContext());
//        textView.setText("leftMenu");
//        textView.setTextSize(22f);
//        textView.setBackgroundColor(Color.GREEN);
//        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        params.rightMargin = 200;
//        textView.setLayoutParams(params);
//        menu.setLeftMenuView(textView);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "click left", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        TextView rightMenu = new TextView(getContext());
//        rightMenu.setText("rightMenu");
//        rightMenu.setTextSize(22f);
//        rightMenu.setBackgroundColor(Color.BLUE);
//        rightMenu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        menu.setRightMenuView(rightMenu);
    }

    @Override
    protected int getContainerRes() {
        return R.layout.layout_game;
    }
}
