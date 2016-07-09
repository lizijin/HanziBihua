package com.peter.hanzibihua.utils;

/**
 * Created by jiangbin on 16/7/6.
 */

import android.graphics.PointF;

import com.peter.hanzibihua.bean.Chinese;

import java.util.ArrayList;

/**
 * Created by jiangbin on 16/7/6.
 */
public class BihuaParser {

    public static class Bihua {
        public String hanzi;
        public int bihuaCount;
        public String bihuaStep;
        public String pinyin;
        public ArrayList<ArrayList<PointF>> points = new ArrayList<>();
    }


    public static Bihua parse(String bihua) {
        Bihua bihuaEntity = new Bihua();

        String bihuaReal = bihua.split(";")[0];
        System.out.println(bihuaReal);

        bihuaEntity.hanzi = bihuaReal.substring(bihua.indexOf("{") + 1, bihua.indexOf(":"));
        System.out.println(bihuaEntity.hanzi);


        int firstIndex = bihuaReal.indexOf("[");
        int lastIndex = bihuaReal.lastIndexOf("]");
        String bihuaWithCountAndSteps = bihuaReal.substring(firstIndex + 1, lastIndex);
//        System.out.println(bihuaWithCountAndSteps);
        bihuaEntity.bihuaCount = Integer.parseInt(bihuaWithCountAndSteps.substring(0, bihuaWithCountAndSteps.indexOf(",")));
//        System.out.println(bihuaEntity.bihuaCount);
        int count = bihuaWithCountAndSteps.length();
        bihuaEntity.pinyin = bihuaWithCountAndSteps.substring(bihuaWithCountAndSteps.lastIndexOf("'", count - 2) + 1, count - 1);
//        System.out.println(bihuaEntity.pinyin);

        String bihua2 = bihuaWithCountAndSteps.substring(bihuaWithCountAndSteps.indexOf(",") + 1, bihuaWithCountAndSteps.lastIndexOf(","));
        System.out.println(bihua2);
        bihuaEntity.bihuaStep = bihua2.substring(bihua2.indexOf("'") + 1, bihua2.indexOf("'", bihua2.indexOf("'") + 1));
        System.out.println(bihuaEntity.bihuaStep);
        String bihua3 = bihua2.substring(bihua2.indexOf(",") + 1);

        System.out.println(bihua3);
        String bihua4 = bihua3.substring(1, bihua3.length() - 1);
//        System.out.println(bihua4);

        String[] steps = bihua4.split("#");

        for (String s : steps) {
            System.out.println(s);
            String[] points = s.split(":")[1].split(" ");//(312,12),(360,54),(396,96)
            ArrayList<PointF> arryList = new ArrayList<>();
            for (String point : points) {
                System.out.println(point);
                String[] pointStr = point.substring(1, point.length() - 1).split(",");
                PointF point1 = new PointF();
                point1.x = Float.parseFloat(pointStr[0]);
                point1.y = Float.parseFloat(pointStr[1]);
                arryList.add(point1);
                System.out.println("x " + point1.x + " y " + point1.y);
            }
            bihuaEntity.points.add(arryList);
        }
        return bihuaEntity;
    }

    public static Bihua convertFromChinese(Chinese chinese) {
        Bihua bihua = new Bihua();
        bihua.bihuaCount = chinese.chineseCount;
        bihua.bihuaStep = chinese.chineseSteps;
        bihua.hanzi = chinese.chinese;
        bihua.pinyin = chinese.chineseSpell;
//        bihua.points = chinese.chineseSpell;

        String[] steps = chinese.points.split("#");

        for (String s : steps) {
            System.out.println(s);
            String[] points = s.split(":")[1].split(" ");//(312,12),(360,54),(396,96)
            ArrayList<PointF> arryList = new ArrayList<>();
            for (String point : points) {
                System.out.println(point);
                String[] pointStr = point.substring(1, point.length() - 1).split(",");
                PointF point1 = new PointF();
                point1.x = Float.parseFloat(pointStr[0]);
                point1.y = Float.parseFloat(pointStr[1]);
                arryList.add(point1);
                System.out.println("x " + point1.x + " y " + point1.y);
            }
            bihua.points.add(arryList);
        }
        return bihua;
    }
}
