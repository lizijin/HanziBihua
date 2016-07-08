package com.peter.hanzibihua.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jiangbin on 16/7/8.
 */
@DatabaseTable(tableName = "AIMATION")
public class ChineseAnimation {

    @DatabaseField(columnName = "ENCODE")
    public String encode;

    @DatabaseField(columnName = "HANZI")
    public String chinese;

    @DatabaseField(columnName = "ANIMATION")
    public String animation;
}
