package com.cml.second.app.common.widget.menu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * dp
     */
    private int menuRightPadding = 100;
    /**
     * 菜单的宽度
     */
    private int menuWidth;
    private int mHalfMenuWidth;

    private ViewGroup menu;
    private ViewGroup content;

    private boolean once;

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 显示的设置一个宽度 
         */
        if (!once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            menu = (ViewGroup) wrapper.getChildAt(0);
            content = (ViewGroup) wrapper.getChildAt(1);

            menuWidth = mScreenWidth - 200;
            mHalfMenuWidth = menuWidth / 2;
            menu.getLayoutParams().width = menuWidth;
            content.getLayoutParams().width = mScreenWidth;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 将菜单隐藏  
            this.scrollTo(menuWidth, 0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth)
                    this.smoothScrollTo(menuWidth, 0);
                else
                    this.smoothScrollTo(0, 0);
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / menuWidth;
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;


        ViewCompat.setScaleX(menu, leftScale);
        ViewCompat.setScaleY(menu, leftScale);
        ViewCompat.setAlpha(menu, 0.6f + 0.4f * (1 - scale));
        ViewCompat.setTranslationX(menu, menuWidth * scale * 0.6f);

        ViewCompat.setPivotX(content, 0);
        ViewCompat.setPivotY(content, content.getHeight() / 2);
        ViewCompat.setScaleX(content, rightScale);
        ViewCompat.setScaleY(content, rightScale);

    }
}  