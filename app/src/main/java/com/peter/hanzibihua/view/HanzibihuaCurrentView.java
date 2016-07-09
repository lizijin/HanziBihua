package com.peter.hanzibihua.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.peter.hanzibihua.utils.BihuaParser;

import java.util.ArrayList;

/**
 * Created by jiangbin on 16/7/6.
 */
public class HanzibihuaCurrentView extends View {


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mCurrentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private BihuaParser.Bihua mBihua;

    private ArrayList<Path> mPaths = new ArrayList<>();
    private int currentStep =0;

    private float mDensity;

    public HanzibihuaCurrentView(Context context) {
        super(context);
        init(context);
    }

    public HanzibihuaCurrentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HanzibihuaCurrentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HanzibihuaCurrentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setPathEffect(new CornerPathEffect(3));


        mCurrentPaint.setColor(Color.RED);
        mCurrentPaint.setStyle(Paint.Style.STROKE);
        mCurrentPaint.setStrokeWidth(20);
        mCurrentPaint.setStrokeCap(Paint.Cap.ROUND);
        mCurrentPaint.setPathEffect(new CornerPathEffect(3));

        mDensity = context.getResources().getDisplayMetrics().density;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)(100*mDensity), (int)(100*mDensity));
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
//        canvas.translate(0, 200);
        canvas.scale(0.25f, 0.25f);
        try {
            for (int i=0;i<mPaths.size()-1;i++) {
                Path path = mPaths.get(i);
                canvas.drawPath(path, paint);
            }
            canvas.drawPath(mPaths.get(mPaths.size()-1), mCurrentPaint);

        } catch (Exception e) {

        }
        canvas.restore();
    }


    public void setBihua(BihuaParser.Bihua bihua) {
        this.mBihua = bihua;

    }

    public void setCurrentStep(int step){
        this.currentStep = step;

        mPaths.clear();

        for(int j=0;j<=this.currentStep;j++){
//        for (ArrayList<PointF> pointFs : mBihua.points) {
            ArrayList<PointF> pointFs = mBihua.points.get(j);
            Path path = new Path();
            paint.setPathEffect(new CornerPathEffect(3));

            for (int i = 0; i < pointFs.size(); i++) {
                if (i == 0) {
                    path.moveTo(pointFs.get(i).x, pointFs.get(i).y);
                } else {
                    path.lineTo(pointFs.get(i).x, pointFs.get(i).y);
                }

            }
            mPaths.add(path);
        }
        invalidate();
    }

}
