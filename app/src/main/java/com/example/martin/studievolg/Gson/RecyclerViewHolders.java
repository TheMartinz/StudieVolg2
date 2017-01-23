package com.example.martin.studievolg.Gson;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.martin.studievolg.R;

/**
 * Created by Martin on 22-1-2017.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView name;
    public TextView ects;
    public TextView grade;
    public TextView periode;
    public RecyclerViewHolders(View itemView) {

        super(itemView);
        itemView.setOnClickListener(this);
        name = (TextView)itemView.findViewById(R.id.modulecode);
        ects = (TextView)itemView.findViewById(R.id.ects);
        grade = (TextView)itemView.findViewById(R.id.cijfer);
        periode = (TextView)itemView.findViewById(R.id.periode);
    }

    @Override
    public void onClick(View view) {
    }
}