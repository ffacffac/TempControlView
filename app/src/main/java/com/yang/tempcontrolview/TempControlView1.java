package com.yang.tempcontrolview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by huangqj on 2017/1/3.
 */
public class TempControlView1 extends View {

    private int width;//控件宽
    private int height;//控件高
    private int dialRadius;//刻度盘的半径
    private int scaleHeight = dp2px(10);//刻度的高度
    private Paint dialPaint;//刻度盘画笔
    // 温度
    private int temperature = 15;
    // 最低温度
    private int minTemp = 15;
    // 最高温度
    private int maxTemp = 30;
    // 四格（每格4.5度，共18度）代表温度1度
    private int angleRate = 4;

    private int arcRadius;//圆弧半径
    private Paint arcPaint;//圆弧画笔


    public TempControlView1(Context context) {
//        super(context);
        this(context, null);
    }


    public TempControlView1(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, null, 0);
    }


    public TempControlView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //刻度的画笔
        dialPaint = new Paint();
        dialPaint.setAntiAlias(true);
        dialPaint.setColor(Color.parseColor("#3CB7EA"));
        dialPaint.setStrokeWidth(dp2px(2));
        dialPaint.setStyle(Paint.Style.STROKE);
        //圆弧的画笔
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(Color.parseColor("#3CB7EA"));
        arcPaint.setStrokeWidth(dp2px(3));
        arcPaint.setStyle(Paint.Style.STROKE);//设置空心的，不设置的话，画出来的弧形是实心的
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 控件宽、高
        width = height = Math.min(h, w);
        // 刻度盘半径
        dialRadius = width / 2 - dp2px(20);
        // 圆弧半径，圆弧的半径要比刻度的半径小一点
        arcRadius = dialRadius - dp2px(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);//绘制刻度
        drawArc(canvas);//绘制圆弧

    }

    /**
     * 绘制刻度盘
     *
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);//把当前画布的原点移到(getWidth() / 2, getHeight() / 2),后面的操作都以这个坐标作为参照点
        canvas.rotate(-133);//逆时针旋转133度
        //绘制60个刻度
        for (int i = 0; i < 60; i++) {
            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
            canvas.rotate(4.5f);
        }

//        canvas.rotate(90);
//        dialPaint.setColor(Color.parseColor("#E37364"));
//        for (int i = 0; i < (temperature - minTemp) * angleRate; i++) {
//            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
//            canvas.rotate(4.5f);
//        }
        canvas.restore();
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);//
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        canvas.drawArc(rectF, 0, 265, false, arcPaint);//画弧形线条
        canvas.restore();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
