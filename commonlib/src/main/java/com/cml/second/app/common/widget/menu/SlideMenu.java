package com.cml.second.app.common.widget.menu;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

/**
 * Created by cmlBeliever on 2016/3/15.
 * <p>左右滑动的组件容器,</p>
 * <p>第一个view为container</p>
 * <p>第二个view为leftmenu</p>
 * <p>第三个view为rightmenu</p>
 */
public class SlideMenu extends FrameLayout {

    private static final String TAG = SlideMenu.class.getSimpleName();

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;


    private View containerView;
    private View leftMenuView;
    private View rightMenuView;


    public SlideMenu(Context context) {
        super(context);
        this.init();
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private ViewDragHelper dragHelper;
    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        private int mLeft = 0;

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == leftMenuView;
        }


        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            dragHelper.captureChildView(leftMenuView, pointerId);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int newLeft = Math.max(-child.getWidth(), Math.min(left, 0));
            return newLeft;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            if (child == SlideMenu.this) {
                return 0;
            }
            return child.getMeasuredWidth();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mLeft += dx;
            leftMenuView.layout(mLeft, 0, leftMenuView.getMeasuredWidth(), leftMenuView.getMeasuredHeight());
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }
    };

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
        //设置edge_left track
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置显示内容
        containerView = getChildAt(0);
        if (null != leftMenuView) {
            int widthSpec = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * 0.8), MeasureSpec.EXACTLY);
            leftMenuView.measure(widthSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        containerView.layout(left, top, right, bottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//
//
//        Log.d(TAG, "dispatchTouchEvent===>action==>" + event.getAction());
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                initialPoint.x = event.getX();
//                initialPoint.y = event.getY();
//                Log.d(TAG, "onTouchEvent===>actiondown::" + event.getAction());
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "onTouchEvent===>actionmove::" + (event.getX() - initialPoint.x));
//                if (event.getX() - initialPoint.x > 20 && Math.abs(event.getY() - initialPoint.y) < 20) {
//                    //从左到右滑动
//                    state = STATE_LEFT_TO_RIGHT;
//                    dx = event.getX() - initialPoint.x;
//                    requestLayout();
//                }
//                return true;
//
//            case MotionEvent.ACTION_UP:
//                dx = event.getX() - initialPoint.x;
//
//                if (dx >= leftMenuView.getMeasuredWidth() / 2) {
//                    dx = leftMenuView.getMeasuredWidth();
//                    requestLayout();
//                }
//
//        }
//
//
//        return super.dispatchTouchEvent(event);
//    }


    /**
     * 设置左边菜单
     *
     * @param v
     */
    public void setLeftMenuView(View v) {
        this.leftMenuView = v;
        addView(leftMenuView);
    }

    /**
     * 设置右边菜单
     *
     * @param v
     */
    public void setRightMenuView(View v) {
        this.rightMenuView = v;
        addView(rightMenuView);
    }

    public void showMenu(int direction) {
        View menu = null;
        if (direction == DIRECTION_RIGHT) {
            menu = rightMenuView;
        } else {
            menu = leftMenuView;
        }
        removeView(menu);
        addView(menu);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.1f, 1f, 1f, 1f);
        scaleAnimation.setDuration(500);
        menu.startAnimation(scaleAnimation);

//        TranslateAnimation animation = new TranslateAnimation(0.2f, 1.0f, 0.1f, 1f);
//        animation.setDuration(5000);
//        menu.startAnimation(animation);
    }

}
