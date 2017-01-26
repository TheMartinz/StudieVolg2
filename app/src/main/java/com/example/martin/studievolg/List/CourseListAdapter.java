package com.example.martin.studievolg.List;

/**
 * Created by Martin on 26-1-2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.martin.studievolg.Models.Course;
import com.example.martin.studievolg.R;

import java.util.List;

public class CourseListAdapter extends ArrayAdapter<Course> {

    public CourseListAdapter(Context context, int resource, List<Course> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.content_row, parent, false);
            vh.name = (TextView) convertView.findViewById(R.id.subject_name);
            vh.code = (TextView) convertView.findViewById(R.id.subject_code);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Course cm = getItem(position);
        vh.name.setText(cm.getModulecode());
        vh.code.setText(cm.getCijfer());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView code;
    }
}

