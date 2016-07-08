package com.peter.hanzibihua;

/**
 * Created by jiangbin on 16/7/6.
 */

import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by jiangbin on 16/7/6.
 */
public class BihuaParser {
    static String bihua = "hzbh.main('审',{审:[8,'kdefcjjf','0:(312,12) (360,54) (396,96)#1:(90,84) (90,126) (72,168) (36,222)#2:(90,120) (690,120) (720,102) (690,120) (642,204)#3:(156,594) (156,246)#4:(156,276) (594,276) (624,246) (594,276) " +
            "(594,582)#5:(594,408) (156,408)#6:(594,540) (156,540)#7:(330,138) (372,174) (372,768)','shěn']});document.getElementById(\"tianzi_jie_guo_dixiabeizhu\").innerHTML = \"一共<b>1</b>个汉字，共计笔画：<b>8</b>画\";\n";


    public static void main(String[] args) {
        parse(bihua);
    }

    static class Bihua {
        public String hanzi;
        public int bihuaCount;
        public String bihuaStep;
        public String pinyin;
        public ArrayList<ArrayList<PointF>> points = new ArrayList<>();

        @Override
        public String toString() {
            return "hanzi: " + hanzi + " bihuaCount " + bihua + " bihuaStep " + bihuaStep;
        }
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
        bihuaEntity.bihuaStep = bihua2.substring(bihua2.indexOf("'")+1,bihua2.indexOf("'",bihua2.indexOf("'")+1));
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
}
