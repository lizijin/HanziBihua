package com.peter.hanzibihua.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiangbin on 16/7/6.
 */
public class HanzipinyinAnimateView extends View {

//    private String data = "240,24-240,24-250,41-240,41-241,58-259,57-268,73-243,74-246,90-272,89-272,105-248,107-248,123-272,122-272,138-248,140-248,156-272,155-272,171-248,173-248,189-272,188-272,204-248,206-248,222-272,221-272,237-248,239-248,255-272,254-272,270-247,272-242,289-272,287-272,303-247,305-260,321-272,320-272,336-272,336#272,96-272,80-287,79-289,95-305,93-304,77-320,76-322,92-338,90-337,74-353,72-355,88-371,87-370,71-386,69-388,85-404,83-403,67-419,66-421,82-437,80-436,64-452,63-454,78-470,77-469,61-485,59-487,75-503,73-502,58-518,51-520,72-537,71-533,36-550,43-553,71-554,70-568,56#512,68-556,68-542,83-512,78-511,94-535,98-534,114-509,110-508,126-532,131-531,147-506,143-505,159-529,163-528,179-503,175-502,191-526,195-525,212-501,208-499,224-523,228-522,244-498,240-496,256-520,256#280,200-264,184-281,183-283,200-300,199-298,182-314,181-316,197-333,196-331,179-347,178-349,194-366,193-364,177-380,174-382,190-399,187-397,171-413,168-415,185-432,182-429,164-446,161-448,179-464,176-448,160#280,304-264,288-281,287-283,304-300,302-298,285-315,283-317,300-333,298-332,281-348,280-350,296-367,294-365,278-382,276-384,292-401,290-399,274-416,273-417,288-434,287-432,271-449,269-451,286-468,285-466,267-483,265-485,284-502,283-499,262-516,258-519,282-536,281-534,267-552,280-552,280#72,400-72,400-89,399-90,419-107,423-105,398-121,397-123,422-139,420-137,396-153,394-156,418-172,416-169,393-186,392-188,415-204,413-202,391-218,390-220,411-236,409-234,388-250,387-252,408-268,406-266,386-283,385-285,404-301,402-299,384-315,382-317,401-333,399-331,381-347,380-349,397-365,395-364,379-380,377-381,394-397,392-396,376-412,375-414,391-430,389-428,373-444,372-446,388-462,387-461,371-477,369-478,386-494,384-493,368-509,366-511,383-527,382-525,365-541,364-543,380-559,379-557,362-574,361-575,378-591,376-590,357-605,353-608,375-624,374-621,350-637,347-640,372-656,371-654,348-671,358-672,370-688,368-688,368#368,392-392,392-392,409-368,409-368,425-392,425-392,441-368,441-368,457-392,457-392,473-368,473-368,489-392,489-392,505-368,505-368,521-392,521-392,537-368,537-368,553-392,553-392,569-368,569-368,585-392,585-392,601-368,601-368,617-392,617-392,632-368,632#392,520-376,504-394,503-396,520-413,519-411,502-428,501-431,517-448,516-445,500-463,498-465,514-482,513-480,497-497,492-499,511-516,508-513,486-530,480-533,505-550,502-546,475-565,482-567,499-584,496-584,496#240,456-272,488-269,500-236,475-232,493-257,512-248,526-228,511-224,529-238,539-229,553-215,542-206,556-220,567-211,581-197,570-187,584-201,595-192,609-178,598-167,611-180,621-168,633-156,623-145,636-156,645-144,656-134,649-123,661-133,668-121,680-112,673-100,685-109,692-96,703-88,697-76,709-83,715-70,726-64,721-52,733-58,737-48,744-40,744#224,576-224,568-239,575-237,583-252,591-254,581-269,588-266,598-281,606-284,594-299,601-295,614-310,621-314,607-329,614-325,629-339,637-344,620-359,627-354,644-369,652-374,633-389,640-383,660-398,667-404,646-419,653-412,675-427,683-434,659-449,665-442,690-456,698-464,672-478,678-471,706-485,713-493,685-508,691-500,721-515,729-523,697-539,700-529,736-544,744-555,703-571,707-558,751-576,748-587,710-603,713-594,743-612,738-620,713-637,713-631,733-649,728-653,713-670,713-667,724-685,720-687,713-704,713-703,716-720,712-720,712";
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ArrayList<ArrayList<PointF>> mPointFArray = new ArrayList<>();

    private Handler handler = new Handler();

    AtomicInteger currentStep = new AtomicInteger(0);

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int count = currentStep.incrementAndGet();
            if (count >= mPointFArray.size()) {
                handler.removeCallbacks(this);
                return;
            } else {
                invalidate();

                handler.postDelayed(this, 2);
            }
        }
    };
    private float mDensity;

    public HanzipinyinAnimateView(Context context) {
        super(context);
        init(context);
    }

    public HanzipinyinAnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HanzipinyinAnimateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HanzipinyinAnimateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(6);
        mDensity = context.getResources().getDisplayMetrics().density;
//        PointFs = showData(data);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)(100*mDensity), (int) (100*mDensity));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            canvas.save();
            int current = currentStep.get();
            for (int i = 0; i <= current; i++) {
                ArrayList<PointF> arrayList2 = mPointFArray.get(i);
                for (PointF pointF : arrayList2) {
                    canvas.drawPoint(pointF.x, pointF.y, paint);
                }
            }
        } catch (Exception e) {

        } finally {
            canvas.restore();
        }


    }

    public void setData(String data) {
//        ArrayList<ArrayList<PointF>> pointFArrayList = new ArrayList<>();
        mPointFArray.clear();
        String[] dataArr = data.split("#");

        for (String dataItem : dataArr) {//每一笔画的所有坐标
            String[] c = dataItem.split("-");//每一笔画的所有点

            for (int f = 0; f < c.length - 1; f++) {
                String[] b = c[f].split(","),
                        a = c[f + 1].split(",");
                ArrayList<PointF> arrayList = drawLine(Float.parseFloat(b[0]), Float.parseFloat(b[1]), Float.parseFloat(a[0]), Float.parseFloat(a[1]));
                mPointFArray.add(arrayList);
            }
        }
        handler.removeCallbacks(runnable);
        currentStep.set(0);
        handler.post(runnable);
    }


    public ArrayList<PointF> drawLine(float c, float g, float b, float f) {
        float e, a = (float) Math.floor(Math.sqrt((b - c) * (b - c) + (f - g) * (f - g)));
        double d = Math.atan((b - c) / (f - g));
        if (((f - g) < 0 && (b - c) > 0) || ((f - g) < 0 && (b - c) < 0)) {
            d = Math.PI + d;
        }
        double j = Math.sin(d),
                h = Math.cos(d);
        ArrayList<PointF> pointFs = new ArrayList<>();
        for (e = 0; e < a; e = e + 11) {
            pointFs.add(drawDot(c + e * j, g + e * h));
        }
        pointFs.add(drawDot(c, g));
        return pointFs;
    }

    public PointF drawDot(double a, double b) {
        double x = a / 4 + 5,
                y = b / 4 + 5;
        return new PointF((float) x, (float) y);
    }
}
