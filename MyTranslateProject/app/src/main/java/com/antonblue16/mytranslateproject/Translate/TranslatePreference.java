package com.antonblue16.mytranslateproject.Translate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.antonblue16.mytranslateproject.R;

public class TranslatePreference
{
    SharedPreferences preferences;
    Context context;

    public TranslatePreference(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input)
    {
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.translate_preference);

        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getFirstRun()
    {
        String key = context.getResources().getString(R.string.translate_preference);

        return preferences.getBoolean(key, true);
    }
}
