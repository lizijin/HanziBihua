package com.peter.hanzibihua.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.peter.hanzibihua.R;
import com.peter.hanzibihua.bean.ChineseAnimation;

import java.sql.SQLException;

/**
 * Created by jiangbin on 16/7/8.
 */
public class ChineseAnimationDatabaseHelper extends DatabaseHelper {
    public final static String DATABASE_NAME = "ANIMATION.db";
    public final static int DATABASE_CODE = 1;
    private static ChineseAnimationDatabaseHelper sHelper = null;
    private Dao<ChineseAnimation,Integer> chineseAnimationIntegerDao;


    private ChineseAnimationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, DATABASE_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    @Override
    protected int getRawResourceId() {
        return R.raw.anmation;
    }

    public static synchronized ChineseAnimationDatabaseHelper getHelper(Context context) {
        if (sHelper == null) {
            sHelper = new ChineseAnimationDatabaseHelper(context);
        }
        sHelper.mCounter.incrementAndGet();
        return sHelper;
    }

    public Dao<ChineseAnimation, Integer> getChineseAnimationDao() {
        if (chineseAnimationIntegerDao == null) {
            try {
                chineseAnimationIntegerDao = getDao(ChineseAnimation.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return chineseAnimationIntegerDao;
    }

    @Override
    public void close() {
        super.close();
        //TODO做自己的事情
    }
}
