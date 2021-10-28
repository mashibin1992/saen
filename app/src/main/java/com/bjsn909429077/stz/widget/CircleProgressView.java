package com.bjsn909429077.stz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/5 10:50
 **/
public class CircleProgressView extends View {
    private Paint mCirPaint;
    private Paint mArcPaint;
    private Paint mTextPaint;
    private float radius = 200;
    private int textsize = 60;
    private int progress = 68;
    private int stokeWidth = 10;
    private int circleColor = Color.GRAY;
    private int arcColor = Color.GREEN;
    private int textColor = Color.BLACK;
    private int speed = 0;
    private String content = "";

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public void setTextSize(int textsize) {
        this.textsize = textsize;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setText(String content) {
        this.content = content;
    }

    public void setStokewidth(int stokeWidth) {
        this.stokeWidth = stokeWidth;
        invalidate();
    }

    public void setColor(int circleColor, int arcColor, int textColor) {
        this.circleColor = circleColor;
        this.arcColor = arcColor;
        this.textColor = textColor;
        invalidate();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private void init() {
        mCirPaint = new Paint();
        mCirPaint.setColor(circleColor);
        mCirPaint.setAntiAlias(true);
        mCirPaint.setStyle(Paint.Style.STROKE);
        mCirPaint.setStrokeWidth(stokeWidth);
        mArcPaint = new Paint();
        mArcPaint.setColor(arcColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(stokeWidth);
        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textsize);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        canvas.drawCircle(centerX, centerY, radius, mCirPaint);
        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, -90, progress * 360 / 100, false, mArcPaint);
        if (TextUtil.isEmpty(content))
            canvas.drawText(progress + "%", centerX - (mTextPaint.measureText(progress + "%")) / 2, centerY + textsize / 2, mTextPaint);
        else
            canvas.drawText(content, centerX - (mTextPaint.measureText(content)) / 2, centerY + textsize / 2, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (speed != 0) {
            startProgress();
        }
    }

    public void startProgress() {
        final int preProgress = progress;
        new CountDownTimer(preProgress * speed, speed) {
            @Override
            public void onTick(long l) {
                setProgress(preProgress - (int) (l / speed));
                invalidate();
            }

            @Override
            public void onFinish() {
                setProgress(preProgress);
                invalidate();
                this.cancel();
            }
        }.start();
    }
}
