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
public class HanzibihuaView extends View {


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private BihuaParser.Bihua mBihua;

    private ArrayList<Path> mPaths = new ArrayList<>();

    private float mDensity;

    public HanzibihuaView(Context context) {
        super(context);
        init(context);
    }

    public HanzibihuaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HanzibihuaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HanzibihuaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setPathEffect(new CornerPathEffect(3));
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
            for (Path path : mPaths) {
                canvas.drawPath(path, paint);
            }
        } catch (Exception e) {

        }
        canvas.restore();
    }


    public void setBihua(BihuaParser.Bihua bihua) {
        this.mBihua = bihua;
        mPaths.clear();

        for (ArrayList<PointF> pointFs : mBihua.points) {
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
