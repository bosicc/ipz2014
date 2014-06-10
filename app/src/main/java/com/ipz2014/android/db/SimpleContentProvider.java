package com.ipz2014.android.db;

import android.util.Log;
import com.github.nrudenko.orm.LikeOrmContentProvider;
import com.github.nrudenko.orm.LikeOrmSQLiteOpenHelper;

/**
 * Stub
 */
public class SimpleContentProvider extends LikeOrmContentProvider {

    private static final String TAG = SimpleContentProvider.class.getSimpleName();
    private DatabaseHelper db;

    public SimpleContentProvider() {

    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, ">>>> onCreate()");
        db = DatabaseHelper.getInstance(getContext());
        return super.onCreate();
    }

    @Override
    public LikeOrmSQLiteOpenHelper getDbHelper() {
        Log.i(TAG, ">>>> getDbHelper()");
        return db;
    }
}
