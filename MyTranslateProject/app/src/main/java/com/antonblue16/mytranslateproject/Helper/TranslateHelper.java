package com.antonblue16.mytranslateproject.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.antonblue16.mytranslateproject.R;
import com.antonblue16.mytranslateproject.Translate.Translate;
import com.antonblue16.mytranslateproject.database.DatabaseHelper;
import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TABLE_EN_IN;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TABLE_IN_EN;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TranslateColumns.FIELD_ARTI;
import static com.antonblue16.mytranslateproject.database.DatabaseContract.TranslateColumns.FIELD_KATA;

public class TranslateHelper
{
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public TranslateHelper(Context context)
    {
        this.context = context;
    }

    public TranslateHelper open() throws SQLException
    {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        databaseHelper.close();
    }

    public ArrayList<Translate> getAllData(String pick)
    {
        Cursor cursor;
        String swapTranslate = null;

        if(pick == "en")
        {
            cursor = sqLiteDatabase.query(TABLE_EN_IN,null,null,null,null,null,_ID+ " ASC",null);
            swapTranslate = context.getResources().getString(R.string.english_indonesia);
        }
        else
        {
            cursor = sqLiteDatabase.query(TABLE_IN_EN,null,null,null,null,null,_ID+ " ASC",null);
            swapTranslate = context.getResources().getString(R.string.indonesia_english);
        }

        cursor.moveToFirst();

        ArrayList<Translate> arrayList = new ArrayList<>();
        Translate translate;

        if(cursor.getCount() > 0)
        {
            do
            {
                translate = new Translate();
                translate.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                translate.setKata(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_KATA)));
                translate.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_ARTI)));
                translate.setSwapTranslate(swapTranslate);

                arrayList.add(translate);
                cursor.moveToNext();
            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Translate> getDataByName(String search, String pick)
    {
        String swapTranslate = null;
        Cursor cursor;

        if(pick == "en")
        {
            cursor = sqLiteDatabase.query(TABLE_EN_IN,null,FIELD_KATA+" LIKE ?",new String[]{search.trim()+"%"},null,null,_ID + " ASC",null);
            swapTranslate = context.getResources().getString(R.string.english_indonesia);
        }
        else
        {
            cursor = sqLiteDatabase.query(TABLE_IN_EN,null,FIELD_KATA+" LIKE ?",new String[]{search.trim()+"%"},null,null,_ID + " ASC",null);
            swapTranslate = context.getResources().getString(R.string.indonesia_english);
        }

        cursor.moveToFirst();

        ArrayList<Translate> arrayList = new ArrayList<>();
        Translate translate;

        if(cursor.getCount() > 0)
        {
            do
            {
                translate = new Translate();
                translate.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                translate.setKata(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_KATA)));
                translate.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_ARTI)));
                translate.setSwapTranslate(swapTranslate);

                arrayList.add(translate);
                cursor.moveToNext();

            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertTransaction(Translate translate, String pick)
    {
        String table = null;

        if(pick == "en")
        {
            table = TABLE_EN_IN;
        }
        else
        {
            table = TABLE_IN_EN;
        }

        String sql = "INSERT INTO "+table+" ("+FIELD_KATA+", "+FIELD_ARTI +") VALUES (?, ?)";
        SQLiteStatement stmt = sqLiteDatabase.compileStatement(sql);
        stmt.bindString(1, translate.getKata());
        stmt.bindString(2, translate.getDescription());
        stmt.execute();
        stmt.clearBindings();
    }

    public void beginTransaction()
    {
        sqLiteDatabase.beginTransaction();
    }

    public void setTransactionSuccess()
    {
        sqLiteDatabase.setTransactionSuccessful();
    }

    public void endTransaction()
    {
        sqLiteDatabase.endTransaction();
    }
}