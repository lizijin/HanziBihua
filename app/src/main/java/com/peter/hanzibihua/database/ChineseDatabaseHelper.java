package com.peter.hanzibihua.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.peter.hanzibihua.R;
import com.peter.hanzibihua.bean.Chinese;

import java.sql.SQLException;

/**
 * Created by jiangbin on 16/7/8.
 */
public class ChineseDatabaseHelper extends DatabaseHelper {
    public final static String DATABASE_NAME = "HANZI.db";
    public final static int DATABASE_CODE = 1;
    private static ChineseDatabaseHelper sHelper = null;
    private Dao<Chinese, Integer> chineseIntegerDao = null;

    private ChineseDatabaseHelper(Context context) {
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
        return R.raw.bihua;
    }

    public static synchronized ChineseDatabaseHelper getHelper(Context context) {
        if (sHelper == null) {
            sHelper = new ChineseDatabaseHelper(context);
        }
        sHelper.mCounter.incrementAndGet();
        return sHelper;
    }

    @Override
    public void close() {
        super.close();
    }

    public Dao<Chinese, Integer> getChineseDao() {
        if (chineseIntegerDao == null) {
            try {
                chineseIntegerDao = getDao(Chinese.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return chineseIntegerDao;
    }
}
