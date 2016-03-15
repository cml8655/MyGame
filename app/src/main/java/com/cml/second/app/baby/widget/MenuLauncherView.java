package com.cml.second.app.baby.widget;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cmlBeliever on 2016/2/22.
 */
public class MenuLauncherView extends View {

    private static final String TAG = MenuLauncherView.class.getSimpleName();

    private float launcherPadGap = 100;
    private float launcherRadius = 30;
    private boolean isLaunching;

    /**
     * 发射平台颜色
     */
    private int launcherPadColor;
    private Paint launcherPadPaint;
    /**
     * 发射物体颜色
     */
    private int launcherViewColor;
    private Paint launcherViewPaint;
    private PointF touchPoint;

    /**
     * 发射动画坐标
     */
    private PointF launchingPoint = new PointF();
    private ValueAnimator animator;

    public MenuLauncherView(Context context) {
        super(context);
        init();
    }

    public MenuLauncherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuLauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MenuLauncherView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //发射台颜色设置
        launcherPadColor = Color.GREEN;
        launcherPadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        launcherPadPaint.setColor(launcherPadColor);
        launcherPadPaint.setStrokeWidth(10);
        launcherPadPaint.setStyle(Paint.Style.STROKE);

        //发射物体颜色设置
        launcherViewColor = Color.RED;
        launcherViewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        launcherViewPaint.setColor(launcherViewColor);

        launcherPadGap = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, launcherPadGap, getContext().getResources().getDisplayMetrics());
        launcherRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, launcherRadius, getContext().getResources().getDisplayMetrics());
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (null != animator && animator.isRunning()) {
            animator.removeAllUpdateListeners();
            animator.end();
        }
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isLaunching || event.getY() < getHeight() / 2) {
            return false;
        }
        touchPoint = new PointF(event.getX(), event.getY());
        boundCheck();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                launchingPoint.x = touchPoint.x;
                startLaunchAnim();
                touchPoint = null;
                return true;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 移动范围检查
     *
     * @return
     */
    private void boundCheck() {
        if (touchPoint.x < launcherRadius) {
            touchPoint.x = launcherRadius;
        } else if (touchPoint.x > getWidth() - launcherRadius) {
            touchPoint.x = getWidth() - launcherRadius;
        }

        if (touchPoint.y + launcherRadius > getHeight()) {
            touchPoint.y = getHeight() - launcherRadius;
        } else if (touchPoint.y < getHeight() - launcherPadGap) {
            touchPoint.y = getHeight() - launcherPadGap + launcherRadius;
        }

//        return ((touchPoint.x >= launcherRidus && touchPoint.x + launcherRidus <= getWidth()) && (touchPoint.y + launcherRidus <= getHeight() && touchPoint.y >= getHeight() - launcherPadGap));
    }

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
//            float value = (float) animation.getAnimatedValue();
//            launchingPoint.y = value;
            PointF value = (PointF) animation.getAnimatedValue();
            launchingPoint = value;
            postInvalidate();
        }
    };


    private void startLaunchAnim() {
        animator = new ValueAnimator();
        animator.addUpdateListener(updateListener);
        animator.setDuration(250);
        animator.setObjectValues(touchPoint, new PointF(touchPoint.x, 0));
        animator.setEvaluator(new BezierEvaluator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isLaunching = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isLaunching = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isLaunching = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();


        if (!isLaunching) {
            if (touchPoint == null) {
                //初始化图片
                canvas.drawLine(0, height - launcherPadGap, width, height - launcherPadGap, launcherPadPaint);
            } else {
                //画跳绳
                canvas.drawLine(0, height - launcherPadGap, touchPoint.x, touchPoint.y, launcherPadPaint);
                canvas.drawLine(touchPoint.x, touchPoint.y, width, height - launcherPadGap, launcherPadPaint);
                //画发射器
                canvas.drawCircle(touchPoint.x, touchPoint.y, launcherRadius, launcherViewPaint);
            }

        } else {  //正在执行动画
            //画发射器
            canvas.drawCircle(launchingPoint.x, launchingPoint.y, launcherRadius, launcherViewPaint);
            if (launchingPoint.y <= height - launcherPadGap) {
                canvas.drawLine(0, height - launcherPadGap, width, height - launcherPadGap, launcherPadPaint);
            } else {
                //画跳绳
                canvas.drawLine(0, height - launcherPadGap, launchingPoint.x, launchingPoint.y, launcherPadPaint);
                canvas.drawLine(launchingPoint.x, launchingPoint.y, width, height - launcherPadGap, launcherPadPaint);
            }
        }
    }


    class BezierEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue,
                               PointF endValue) {
            final float t = fraction;
            float oneMinusT = 1.0f - t;
            PointF point = new PointF();

            PointF point1 = new PointF();
            point1.set(getWidth() * oneMinusT, 0);

            PointF point2 = new PointF();
            point2.set(0, getHeight() * oneMinusT);

            point.x = oneMinusT * oneMinusT * oneMinusT * (startValue.x)
                    + 3 * oneMinusT * oneMinusT * t * (point1.x)
                    + 3 * oneMinusT * t * t * (point2.x)
                    + t * t * t * (endValue.x);

            point.y = oneMinusT * oneMinusT * oneMinusT * (startValue.y)
                    + 3 * oneMinusT * oneMinusT * t * (point1.y)
                    + 3 * oneMinusT * t * t * (point2.y)
                    + t * t * t * (endValue.y);
            return point;
        }
    }
}
