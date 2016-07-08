package com.peter.hanzibihua.database;

/**
 * Created by jiangbin on 16/7/8.
 */

import android.content.Context;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.peter.hanzibihua.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    protected String mDatabaseName;
    protected int mDatabaseVersion;
    protected WeakReference<Context> mContextRef;
    protected AtomicInteger mCounter = new AtomicInteger(0);


    public DatabaseHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        this.mDatabaseName = databaseName;
        this.mDatabaseVersion = databaseVersion;
        this.mContextRef = new WeakReference<>(context);
        copyDatabaseFromRawToData(databaseName);
    }

    private void copyDatabaseFromRawToData(String databaseName) {
        File databaseFile = mContextRef.get().getDatabasePath(databaseName);
        if (!databaseFile.exists()) {
            try {
                String path = databaseFile.getAbsolutePath();
                File dirs = new File(path.substring(0, path.lastIndexOf("/")));
                dirs.mkdir();
                databaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            return;
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = mContextRef.get().getResources().openRawResource(getRawResourceId());
            outputStream = new FileOutputStream(databaseFile);
            FileUtils.copyFile(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeInputStream(inputStream);
            FileUtils.closeOutputStream(outputStream);
        }
    }

    protected abstract int getRawResourceId();

    @Override
    public void close() {
        if (mCounter.decrementAndGet() != 0)
            return;
    }
}