package com.idenisyss.myaadharscanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.activities.EnterDetailsactivity;
import com.idenisyss.myaadharscanner.ui.home.HomeModel;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

import java.util.List;

public class HomeviewsAdapter extends RecyclerView.Adapter<HomeviewsAdapter.MyViewHolder> {

    Context context;
    List<HomeModel> list;

    public HomeviewsAdapter(Context context, List<HomeModel> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeviewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.home_views_list, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeviewsAdapter.MyViewHolder holder, int position) {
        String home_selected_title = list.get(position).getTitle_name();
        int drawableResourceId  = list.get(position).getNext_page_image();
        holder.list_tv1.setText(home_selected_title);
        holder.list_home_im.setImageResource(list.get(position).getImage_resurce_path());
        holder.list_cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent j = new Intent(context, EnterDetailsactivity.class);
                j.putExtra(AppConstants.HOMETITLE,home_selected_title);
                j.putExtra(AppConstants.HOMEIMAGE,drawableResourceId );
                context.startActivity(j);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView list_tv1;
        ImageView list_home_im;
        CardView list_cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_tv1 = itemView.findViewById(R.id.list_tv1);
            list_home_im = itemView.findViewById(R.id.list_home_im);
            list_cardView = itemView.findViewById(R.id.list_cardView);

        }
    }

}
