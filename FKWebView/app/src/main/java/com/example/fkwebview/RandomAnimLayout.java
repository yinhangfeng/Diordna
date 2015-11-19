package com.example.fkwebview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * Created by yinhf on 2015/11/6.
 */
public class RandomAnimLayout extends FrameLayout {
    private static final String TAG = "RandomAnimLayout";

    private boolean isStart;

    public RandomAnimLayout(Context context) {
        super(context);
    }

    public RandomAnimLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RandomAnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void start() {
        if(isStart) {
            return;
        }
        isStart = true;
        for(int i = 0; i < getChildCount(); ++i) {
            View view = getChildAt(i);
            startAnimation(view);
        }
    }

    private void startAnimation(final View view) {

        int direction = (int) (Math.random() * 2);
        int start;
        int distance;
        float cur;
        if(direction == 0) {
            start = view.getLeft();
            distance = getWidth();
        } else {
            start = view.getTop();
            distance = getHeight();
        }
        cur = start / (float) distance;
        float dt = (float) (Math.random() * 1.6 - 1);
        float target = cur + dt;
        if(target > 1.01 || target < -1.01) {
            target = cur - dt;
        }
        int end = (int) (target * distance);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.direction = direction;
        layoutParams.start = start;
        layoutParams.end = end;
        Log.i(TAG, "startAnimation direction=" + direction + " start=" + start + " end=" + end);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                if(lp.direction == 0) {
                    int d = (int) ((lp.end - lp.start) * fraction + lp.start - view.getLeft());
                    //Log.d(TAG, "onAnimationUpdate d=" + d + " fraction=" + fraction);
                    view.offsetLeftAndRight(d);
                } else {
                    int d = (int) ((lp.end - lp.start) * fraction + lp.start - view.getTop());
                    //Log.d(TAG, "onAnimationUpdate d=" + d + " fraction=" + fraction);
                    view.offsetTopAndBottom(d);
                }
                invalidate();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(isStart) {
                    startAnimation(view);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        layoutParams.valueAnimator = valueAnimator;
        valueAnimator.start();
    }

    public void stop() {
        if(!isStart) {
            return;
        }
        isStart = false;
        for(int i = 0; i < getChildCount(); ++i) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if(lp.valueAnimator != null) {
                lp.valueAnimator.end();
                lp.valueAnimator = null;
            }
        }

        requestLayout();
    }

    @Override
    protected FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if(p instanceof WebView.LayoutParams) {
            return new LayoutParams(p);
        }
        return new LayoutParams(p);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        ValueAnimator valueAnimator;
        int start;
        int end;
        int direction;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
