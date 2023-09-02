package com.idenisyss.myaadharscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idenisyss.myaadharscanner.R;

import java.util.ArrayList;
import java.util.List;

public class HomeviewsAdapter extends RecyclerView.Adapter<HomeviewsAdapter.MyViewHolder> {

    Context context;
    List<String>  list ;

    public HomeviewsAdapter(Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeviewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.home_views_list,parent,false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeviewsAdapter.MyViewHolder holder, int position) {
        holder.list_tv1.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView list_tv1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_tv1 = itemView.findViewById(R.id.list_tv1);


        }
    }
}
