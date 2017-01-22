package com.example.martin.studievolg.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.martin.studievolg.Models.Course;
import com.example.martin.studievolg.R;


import java.util.List;

/**
 * Created by Martin on 21-1-2017.
 */

public class CourseListAdapter extends ArrayAdapter<Course> {
    public CourseListAdapter(Context context, int resources, List<Course> objects) {
        super(context, resources, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.view_content_row, parent, false);
            vh.modulecode = (TextView) convertView.findViewById(R.id.subject_modulecode);
            vh.cijfer = (TextView) convertView.findViewById(R.id.subject_cijfer);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Course cm = getItem(position);
        vh.modulecode.setText((CharSequence) cm.getModulecode());
        vh.cijfer.setText((CharSequence) cm.getCijfer());
        return convertView;
    }

    private static class ViewHolder {
        TextView modulecode;
        TextView cijfer;
    }
}