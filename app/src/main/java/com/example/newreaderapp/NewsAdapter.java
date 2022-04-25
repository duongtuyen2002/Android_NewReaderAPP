package com.example.newreaderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<News> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public NewsAdapter(MainActivity context, int simple_list_item_1, ArrayList<News> arrayList) {
        super(context,simple_list_item_1,arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.dong,null);
        }

        News news = getItem(position);
        if(news != null){
            TextView txtTitle = (TextView) view.findViewById(R.id.tvTitle);
            ImageView imgAnh = (ImageView) view.findViewById(R.id.imageView);
//            TextView txtDay = (TextView) view.findViewById(R.id.tvDay);

            txtTitle.setText(news.getTitle());
//            txtDay.setText(news.getDay());
            Picasso.with(getContext()).load(news.getImage()).into(imgAnh);
        }
        return view;
    }
}
