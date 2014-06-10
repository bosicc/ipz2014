package com.ipz2014.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.github.nrudenko.orm.LikeOrmSQLiteOpenHelper;
import com.ipz2014.android.model.FeedbackPOJO;

import java.util.List;

public class DatabaseHelper extends LikeOrmSQLiteOpenHelper {

    public static final String CONTENT_AUTHORITY = "com.ipz2014.android.provider";
    private static final String DATABASE_NAME = "ipz2014.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() [oldVersion=" + oldVersion + ", newVersion=" + newVersion + "]");
    }

    @Override
    protected void appendSchemes(List<Class> classes) {
        classes.add(FeedbackPOJO.class);
    }
}
