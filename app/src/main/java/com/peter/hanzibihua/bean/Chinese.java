package com.peter.hanzibihua.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jiangbin on 16/7/8.
 */
@DatabaseTable(tableName = "HANZI")
public class Chinese {

    @DatabaseField(columnName = "HANZI")
    public    String chinese;

    @DatabaseField(columnName = "ENCODE")
    public String encode;

    @DatabaseField(columnName = "BIHUACOUNT")
    public  int chineseCount;

    @DatabaseField(columnName = "BIHUASTEP")
    public String chineseSteps;

    @DatabaseField(columnName = "PINYIN")
    public String chineseSpell;

    @DatabaseField(columnName = "POINTS")
    public String points;

}
