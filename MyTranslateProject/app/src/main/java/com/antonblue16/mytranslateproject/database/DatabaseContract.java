package com.antonblue16.mytranslateproject.database;

import android.provider.BaseColumns;

public class DatabaseContract
{
    public static String TABLE_IN_EN = "id_to_en";
    public static String TABLE_EN_IN = "en_to_id";

    public static final class TranslateColumns implements BaseColumns
    {
        public static String FIELD_KATA = "kataAja";
        public static String FIELD_ARTI = "artiKata";
    }
}
