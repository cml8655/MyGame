package com.cml.second.app.baby.fragment.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by cmlBeliever on 2016/3/9.
 */
public class ScrollBehavior extends CoordinatorLayout.Behavior<View> {

    private int centerX;
    private int centerY;
    private float minRatio = 0.2f;

    public ScrollBehavior() {
        Log.e("ScrollBehavior", "ScrollBehavior");
    }

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("ScrollBehavior", "ScrollBehavior");
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        Log.e("ScrollBehavior", "onLayoutChild================>:" + child.getClass());

        return super.onLayoutChild(parent, child, layoutDirection);
    }


    int childWidth;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //初始化必要的参数
        initVars(parent, child);

        CoordinatorLayout.LayoutParams childParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();

        float scrollRatio = 1 + dependency.getY() / centerY;

        //
        if (scrollRatio < minRatio) {
            return true;
        }

        child.setX((parent.getWidth() - childWidth) * scrollRatio);
        child.setY((centerY - childWidth / 2) * scrollRatio);

        childParams.width = (int) (childWidth * scrollRatio);
        childParams.height = (int) (childWidth * scrollRatio);

        child.setLayoutParams(childParams);

        ViewCompat.setAlpha(child,scrollRatio);

//        FrameLayout
//        ViewGroup.LayoutParams params = parent.getLayoutParams();
//        Log.e("ScrollBehavior", "onDependentViewChanged================>dependency:" + dependency.getClass() + ",child:" + child.getClass());
//        Log.e("ScrollBehavior", "onDependentViewChanged================>:" + parent.getWidth() + "," + dependency.getY() + "," + child.getY() + "," + parent.getY());
        return true;
    }

    private void initVars(CoordinatorLayout parent, View child) {
        //确认CoordinatorLayout中心点
        if (centerX == 0) {
            centerX = parent.getWidth() / 2;
        }

        //确认图片Y中间位置
        if (centerY == 0) {
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                if (parent.getChildAt(i) instanceof AppBarLayout) {
                    AppBarLayout appBarLayout = (AppBarLayout) parent.getChildAt(i);
                    centerY = appBarLayout.getHeight();
                }
            }
        }

        if (childWidth == 0) {
            childWidth = child.getWidth();
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        Log.e("ScrollBehavior", "layoutDependsOn================>:" + dependency.getClass());
//        return dependency instanceof ImageView;
        return dependency instanceof AppBarLayout;
    }


}
