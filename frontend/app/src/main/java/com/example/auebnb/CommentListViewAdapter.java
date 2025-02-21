package com.example.auebnb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.src.Room;

public class CommentListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    private ArrayList<String> comments;

    public CommentListViewAdapter(LayoutInflater inflater,ArrayList<String> comments){
        this.inflater = inflater;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_list_item, container, false);
        }

        ((TextView)convertView.findViewById(R.id.comment_list_text)).setText(comments.get(position));
        return convertView;
    }
}
