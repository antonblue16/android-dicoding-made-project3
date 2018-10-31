package com.antonblue16.mytranslateproject.Search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antonblue16.mytranslateproject.R;
import com.antonblue16.mytranslateproject.Translate.Translate;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>
{
    private ArrayList<Translate> list = new ArrayList<>();

    public SearchAdapter()
    {}

    public void replaceAll(ArrayList<Translate> item)
    {
        list = item;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position)
    {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
