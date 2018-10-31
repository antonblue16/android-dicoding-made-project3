package com.antonblue16.mytranslateproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailTransateActivity extends AppCompatActivity
{
    public static final String DETAIL_KATA = "kata";
    public static final String DETAIL_ARTI_KATA = "artiKata";
    public static final String DETAIL_SWAP_TRANSLATE = "swapTranslate";

    TextView tvDetailKata, tvDetailArtiKata;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transate);

        getSupportActionBar().setTitle(getIntent().getStringExtra(DETAIL_KATA));
        getSupportActionBar().setSubtitle(getIntent().getStringExtra(DETAIL_SWAP_TRANSLATE));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDetailKata = findViewById(R.id.tvDetailKata);
        tvDetailArtiKata = findViewById(R.id.tvDetailArtiKata);

        tvDetailKata.setText(getIntent().getStringExtra(DETAIL_KATA));
        tvDetailArtiKata.setText(getIntent().getStringExtra(DETAIL_ARTI_KATA));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
