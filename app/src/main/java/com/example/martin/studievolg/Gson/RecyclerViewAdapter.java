package com.example.martin.studievolg.Gson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.martin.studievolg.Models.Course;
import com.example.martin.studievolg.R;

import java.util.List;

/**
 * Created by Martin on 22-1-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Course> itemList;
    private Context context;
    public RecyclerViewAdapter(Context context, List<Course> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.name.setText("Modulecode: " + itemList.get(position).getModulecode());
        holder.ects.setText("Ects: " + itemList.get(position).getEcts());
        holder.grade.setText("Cijfer: " + itemList.get(position).getCijfer());
        holder.periode.setText("Periode: " + itemList.get(position).getPeriode());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}