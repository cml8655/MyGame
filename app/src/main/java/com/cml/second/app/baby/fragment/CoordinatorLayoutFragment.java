package com.cml.second.app.baby.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.View;

import com.cml.second.app.baby.R;

/**
 * Created by cmlBeliever on 2016/3/9.
 * 学习layoutbehavior使用
 */
public class CoordinatorLayoutFragment extends BaseFragment {

//    @Bind(R.id.depentent)
//    TextView dependentView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        final View targetView = view.findViewById(R.id.target);
        final int width = 300;
        Log.e("ScrollBehavior", "width================>:" + width);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("ScrollBehavior", "onOffsetChanged================>:" + verticalOffset + ":" + (1 - Math.abs(verticalOffset) / 450));
//                ViewGroup.LayoutParams params = targetView.getLayoutParams();
//
//                ViewCompat.setScaleX(targetView, 1 - Math.abs(verticalOffset) / 450f);
//                ViewCompat.setScaleY(targetView, 1 - Math.abs(verticalOffset) / 450f);
//                ViewCompat.setTranslationX(targetView, (1 - Math.abs(verticalOffset) / 450f) * width / 2);
//                ViewCompat.setTranslationY(targetView, -(1 - Math.abs(verticalOffset) / 450f) * width / 2);

//                params.width = (int) (width * (1-Math.abs(verticalOffset) / 450f));
//                params.height = (int) (width * (1-Math.abs(verticalOffset) / 450f));
            }
        });
//        AppBarLayout.ScrollingViewBehavior
//        dependentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViewCompat.offsetTopAndBottom(v, 100);
//            }
//        });
    }

    @Override
    protected int getContainerRes() {
        return R.layout.fragment_coordinator;
    }
}
