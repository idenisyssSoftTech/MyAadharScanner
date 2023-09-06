package com.idenisyss.myaadharscanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;

import java.util.List;

public class HistoryAdapter extends ListAdapter<ScannedHistory, HistoryAdapter.MyHistoryView> {

    public HistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ScannedHistory> DIFF_CALLBACK = new DiffUtil.ItemCallback<ScannedHistory>() {
        @Override
        public boolean areItemsTheSame(@NonNull ScannedHistory oldItem, @NonNull ScannedHistory newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ScannedHistory oldItem, @NonNull ScannedHistory newItem) {
            return oldItem.getData().equals(newItem.getData()) && oldItem.getCodetype().equals(newItem.getCodetype());
        }

    };

    @NonNull
    @Override
    public HistoryAdapter.MyHistoryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);

        return new MyHistoryView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyHistoryView holder, int position) {

        holder.scanned_date_time.setText(getItem(position).timedate);
        holder.discription_tv.setText(getItem(position).data);
        holder.code_type.setText(getItem(position).codetype);

    }

    public void setContacts(List<ScannedHistory> histories) {

    }

    public static class MyHistoryView extends RecyclerView.ViewHolder {
        ImageView imageView, next_arrow;
        TextView code_type, scanned_date_time, discription_tv;

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
