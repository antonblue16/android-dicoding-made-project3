package com.antonblue16.mytranslateproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TABLE_EN_IN;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TABLE_IN_EN;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TranslateColumns.FIELD_ARTI;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TranslateColumns.FIELD_KATA;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static String DATABASE_NAME = "dbTranslate";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_IN_TO_EN = "CREATE TABLE " +TABLE_IN_EN+ " ("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ FIELD_KATA+" TEXT NOT NULL, "+ FIELD_ARTI+" TEXT NOT NULL);";

    public static String CREATE_TABLE_EN_TO_IN = "CREATE TABLE "+TABLE_EN_IN+ " ("+_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ FIELD_KATA+" TEXT NOT NULL, "+ FIELD_ARTI+" TEXT NOT NULL);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE_IN_TO_EN);
        sqLiteDatabase.execSQL(CREATE_TABLE_EN_TO_IN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_IN_EN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_EN_IN);
        onCreate(sqLiteDatabase);
    }
}
