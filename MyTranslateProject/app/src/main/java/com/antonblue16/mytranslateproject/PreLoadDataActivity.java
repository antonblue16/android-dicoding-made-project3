package com.antonblue16.mytranslateproject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.antonblue16.mytranslateproject.Helper.TranslateHelper;
import com.antonblue16.mytranslateproject.Translate.Translate;
import com.antonblue16.mytranslateproject.Translate.TranslatePreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreLoadDataActivity extends AppCompatActivity
{
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_load_data);

        progressBar = findViewById(R.id.progressBar);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void>
    {
        TranslateHelper translateHelper;
        TranslatePreference translatePreference;

        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute()
        {
            translateHelper = new TranslateHelper(PreLoadDataActivity.this);
            translatePreference = new TranslatePreference(PreLoadDataActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            Boolean runFirst = translatePreference.getFirstRun();

            if(runFirst)
            {
                ArrayList<Translate> translateEnglishToIndonesia = preLoadData("en");
                ArrayList<Translate> translateIndonesiaToEnglish = preLoadData("id");

                translateHelper.open();
                progress = 30;
                publishProgress((int) progress);

                Double maxInsert = 100.0;
                int totalData = translateEnglishToIndonesia.size() + translateIndonesiaToEnglish.size();
                Double progressDiff = (maxInsert - progress) / totalData;


                //English - Indonesia
                translateHelper.beginTransaction();
                try
                {
                    for(Translate model : translateEnglishToIndonesia)
                    {
                        translateHelper.insertTransaction(model, "en");
                    }
                    translateHelper.setTransactionSuccess();
                }
                catch(Exception e)
                {}

                translateHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                //Indonesia - English
                translateHelper.beginTransaction();
                try
                {
                    for(Translate model : translateIndonesiaToEnglish)
                    {
                        translateHelper.insertTransaction(model, "id");
                    }
                    translateHelper.setTransactionSuccess();
                }
                catch(Exception e)
                {}

                translateHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                translateHelper.close();

                translatePreference.setFirstRun(false);
                publishProgress((int) maxProgress);
            }
            else
            {
                try
                {
                    synchronized (this)
                    {
                        this.wait(1500);
                        publishProgress(50);
                        this.wait(1500);
                        publishProgress((int)maxProgress);
                    }
                }
                catch(Exception e)
                {}
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Intent intent = new Intent(PreLoadDataActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public ArrayList<Translate> preLoadData(String pick)
    {
        int raw_data;

        if(pick == "en")
        {
            raw_data = R.raw.english_indonesia;
        }
        else
        {
            raw_data = R.raw.indonesia_english;
        }

        ArrayList<Translate> translates = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try
        {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(raw_data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;

            do
            {
                line = reader.readLine();
                String[] splitStr = line.split("\t");

                Translate translate;

                translate = new Translate(splitStr[0], splitStr[1]);
                translates.add(translate);
                count++;
            } while(line != null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return translates;
    }
}
