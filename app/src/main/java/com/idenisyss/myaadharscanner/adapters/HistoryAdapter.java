package com.idenisyss.myaadharscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.ui.history.HistoryModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHistoryView> {

    Context context;
    List<HistoryModel> historyModelList;

    public HistoryAdapter(Context context, List<HistoryModel> historyModelList) {
        this.context = context;
        this.historyModelList = historyModelList;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyHistoryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(context).inflate(R.layout.history_list,parent,false);

        return new MyHistoryView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyHistoryView holder, int position) {

        holder.scanned_date_time.setText(historyModelList.get(position).getDate_time());
        holder.discription_tv.setText(historyModelList.get(position).getData_content());
        holder.code_type.setText(historyModelList.get(position).getCode_type());

    }

    @Override
    public int getItemCount() {
        return historyModelList.size();
    }

    public static class MyHistoryView extends RecyclerView.ViewHolder {
        ImageView imageView,next_arrow;
        TextView code_type,scanned_date_time,discription_tv;
        public MyHistoryView(@NonNull View itemView) {
            super(itemView);
            next_arrow = itemView.findViewById(R.id.next_arrow);
            discription_tv = itemView.findViewById(R.id.discription_tv);
            imageView = itemView.findViewById(R.id.imageView);
            code_type = itemView.findViewById(R.id.code_type);
            scanned_date_time = itemView.findViewById(R.id.scanned_date_time);

        }
    }
}
