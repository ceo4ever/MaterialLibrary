package com.micnubinub.materiallibrary;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by root on 20/10/14.
 */
public class MaterialTwoLineText extends ViewGroup {
    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final AccelerateInterpolator interpolator = new AccelerateInterpolator();
    private static int duration = 600;
    private final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    private TextView primaryTextView, secondaryTextView;
    private String primaryText, secondaryText;
    private int width;
    private int height;
    private float animated_value = 0;
    private float scaleTo = 1.065f;
    private int clickedX, clickedY;
    private boolean scaleOnTouch = true;
    private boolean touchDown = false, animateRipple;
    private float ripple_animated_value = 0;
    private final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            animated_value = (Float) (animation.getAnimatedValue());
            ripple_animated_value = animated_value;
            Log.e("ripple val", String.format("%f", ripple_animated_value));
            invalidatePoster();
        }
    };
    private final ValueAnimator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            if (!touchDown)
                ripple_animated_value = 0;

            animateRipple = false;
            invalidatePoster();
        }

        @Override
        public void onAnimationCancel(Animator animator) {


        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };
    private int rippleR;
    private int rippleColor = 0x25000000;

    public MaterialTwoLineText(Context context) {
        super(context);
        init();
    }

    public MaterialTwoLineText(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialTwoLineText, 0, 0);
            setPrimaryTextColor(a.getInteger(R.attr.primaryTextColor, getResources().getColor(R.color.dark_dark_grey)));
            setSecondaryTextColor(a.getInteger(R.attr.secondaryTextColor, getResources().getColor(R.color.dark_grey)));
            setPrimaryTextSize(a.getInteger(R.attr.primaryTextSize, 18));
            setSecondaryTextSize(a.getInteger(R.attr.primaryTextSize, 16));
            setSecondaryTextMaxLines(a.getInteger(R.attr.secondaryTextMaxLines, 1));
            secondaryText = a.getString(R.attr.secondaryText);
            primaryText = a.getString(R.attr.primaryText);
            a.recycle();
        } catch (Exception ignored) {
        }

        if (secondaryText == null)
            secondaryText = "";

        if (primaryText == null)
            primaryText = "";

        init();

        setPrimaryText(primaryText);
        setSecondaryText(secondaryText);
    }


    public MaterialTwoLineText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialTwoLineText, 0, 0);
            setPrimaryTextColor(a.getInteger(R.attr.primaryTextColor, getResources().getColor(R.color.dark_dark_grey)));
            setSecondaryTextColor(a.getInteger(R.attr.secondaryTextColor, getResources().getColor(R.color.dark_grey)));
            setPrimaryTextSize(a.getInteger(R.attr.primaryTextSize, 18));
            setSecondaryTextSize(a.getInteger(R.attr.primaryTextSize, 16));
            setSecondaryTextMaxLines(a.getInteger(R.attr.secondaryTextMaxLines, 1));

            a.recycle();
        } catch (Exception ignored) {
        }

        if (secondaryText == null)
            secondaryText = "";

        if (primaryText == null)
            primaryText = "";

        init();

        setPrimaryText(primaryText);
        setSecondaryText(secondaryText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clickedX = (int) event.getX();
                clickedY = (int) event.getY();
                rippleR = (int) (Math.sqrt(Math.pow(Math.max(width - clickedX, clickedX), 2) + Math.pow(Math.max(height - clickedY, clickedY), 2)) * 1.15);

                animator.start();

                touchDown = true;
                animateRipple = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touchDown = false;

                if (!animator.isRunning()) {
                    ripple_animated_value = 0;
                    invalidatePoster();
                }
                break;
        }
        return true;
    }

    public void setPrimaryText(String text) {
        primaryText = text;
        primaryTextView.setText(text);
        invalidatePoster();
    }

    public void setSecondaryText(String text) {
        secondaryText = text;
        secondaryTextView.setText(text);
        invalidatePoster();
    }

    public int dpToPixels(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void init() {
        //Todo consider 16 and 14 (in the guidelines)
        final int padding = dpToPixels(16);
        final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        primaryTextView = new TextView(getContext());
        primaryTextView.setTextColor(getResources().getColor(R.color.dark_dark_grey));
        primaryTextView.setTypeface(null, Typeface.BOLD);
        primaryTextView.setTextSize(18);
        primaryTextView.setMaxLines(1);
        primaryTextView.setLayoutParams(params);
        primaryTextView.setEllipsize(TextUtils.TruncateAt.END);
        primaryTextView.setPadding(padding, padding, padding, padding / 2);

        secondaryTextView = new TextView(getContext());
        secondaryTextView.setTextColor(getResources().getColor(R.color.dark_grey));
        secondaryTextView.setTextSize(16);
        secondaryTextView.setMaxLines(1);
        secondaryTextView.setLayoutParams(params);
        secondaryTextView.setEllipsize(TextUtils.TruncateAt.END);
        secondaryTextView.setPadding(padding, padding / 2, padding, padding);

        primaryTextView.setText("Primary");
        secondaryTextView.setText("Secondary text dkjsgjkab sjkdg bka sjb gas sdg sj dgjsgjs gj sgdjj jsdgj sdg");

        setWillNotDraw(false);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(animatorUpdateListener);
        animator.addListener(animatorListener);
        animator.setDuration(duration);
        paint.setColor(0x25000000);


        addView(primaryTextView);
        addView(secondaryTextView);
    }

    public void setSecondaryTextColor(int color) {
        secondaryTextView.setTextColor(color);
    }

    public void setSecondaryTextMaxLines(int maxLines) {
        secondaryTextView.setMaxLines(maxLines);
    }

    public void setSecondaryTextSize(int sp) {
        secondaryTextView.setTextSize(sp);
    }

    public void setPrimaryTextSize(int sp) {
        primaryTextView.setTextSize(sp);
    }

    public void setPrimaryTextColor(int color) {
        primaryTextView.setTextColor(color);
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {
        final int textViewPadding = ((getMeasuredHeight() - primaryTextView.getMeasuredHeight() - secondaryTextView.getMeasuredHeight()) / 2);
        primaryTextView.layout(getPaddingLeft(), textViewPadding,
                getMeasuredWidth() - getPaddingRight(),
                primaryTextView.getMeasuredHeight() + textViewPadding);

        checkViewParams(primaryTextView);

        secondaryTextView.layout(getPaddingLeft(),
                getMeasuredHeight() - textViewPadding - secondaryTextView.getMeasuredHeight(),
                getMeasuredWidth() - getPaddingRight(),
                getMeasuredHeight() - textViewPadding
        );

        checkViewParams(secondaryTextView);
    }

    private void checkViewParams(final View view, final int layoutWidth, final int layoutHeight) {
        final int width = view.getMeasuredWidth();
        final int height = view.getMeasuredHeight();
        if ((width > layoutWidth) || (height > layoutHeight)) {
            view.setLayoutParams(new LayoutParams(layoutWidth, layoutHeight));
            measureChild(view, MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            view.requestLayout();
            view.invalidate();
            requestLayout();

        }
    }

    private void checkViewParams(final View view) {
        final int layoutWidth = view.getRight() - view.getLeft();
        final int layoutHeight = view.getBottom() - view.getTop();

        checkViewParams(view, layoutWidth, layoutHeight);

    }

    private void scale(final float scale) {
        post(new Runnable() {
            @Override
            public void run() {
                MaterialTwoLineText.this.setScaleX(scale);
                MaterialTwoLineText.this.setScaleY(scale);
                invalidatePoster();
            }
        });

    }

    private void scaleLater() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (touchDown)
                    scale(scaleTo);
                else
                    scale(1);
            }
        }, 175);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        return false;

    }

    public void setDuration(int duration) {
        MaterialTwoLineText.duration = duration;
        animator.setDuration(duration);
    }

    public void setScaleTo(float scaleTo) {
        this.scaleTo = scaleTo;
    }

    public void setScaleOnTouch(boolean scaleOnTouch) {
        this.scaleOnTouch = scaleOnTouch;
    }

    private void invalidatePoster() {
        this.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    public void setRippleColor(int color) {
        rippleColor = color;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (animateRipple) {
            paint.setColor(rippleColor);
            canvas.drawCircle(clickedX, clickedY, rippleR * ripple_animated_value, paint);
        }
    }


    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (getChildCount() >= 2)
            return;
        super.addView(child, index, params);
    }


    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = 0;
        int measuredWidth = 0;

        for (int i = 0; i < getChildCount(); i++) {

            final View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            measuredHeight += child.getMeasuredHeight();
            measuredWidth = Math.max(child.getMeasuredWidth(), measuredWidth);
        }

        setMeasuredDimension(resolveSizeAndState(measuredWidth, widthMeasureSpec, 0),
                resolveSizeAndState(measuredHeight, heightMeasureSpec, 0));


    }
}
