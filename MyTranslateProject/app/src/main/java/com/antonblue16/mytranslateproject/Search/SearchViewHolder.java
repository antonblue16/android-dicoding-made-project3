package com.antonblue16.mytranslateproject.Search;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.antonblue16.mytranslateproject.DetailTransateActivity;
import com.antonblue16.mytranslateproject.R;
import com.antonblue16.mytranslateproject.Translate.Translate;

public class SearchViewHolder extends RecyclerView.ViewHolder
{
    TextView tvKata, tvArtiKata;

    public SearchViewHolder(View view)
    {
        super(view);

        tvKata = view.findViewById(R.id.tvKata);
        tvArtiKata = view.findViewById(R.id.tvArtiKata);
    }

    public void bind(final Translate translate)
    {
        tvKata.setText(translate.getKata());
        tvArtiKata.setText(translate.getDescription());

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(itemView.getContext(), DetailTransateActivity.class);
                intent.putExtra(DetailTransateActivity.DETAIL_KATA, translate.getKata());
                intent.putExtra(DetailTransateActivity.DETAIL_ARTI_KATA, translate.getDescription());
                intent.putExtra(DetailTransateActivity.DETAIL_SWAP_TRANSLATE, translate.getSwapTranslate());

                itemView.getContext().startActivity(intent);
            }
        });
    }
}
