package com.idenisyss.myaadharscanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.activities.IntroActivity;
import com.idenisyss.myaadharscanner.activities.QRCodeScannerActivity;
import com.idenisyss.myaadharscanner.activities.QRScannerResult;
import com.idenisyss.myaadharscanner.databases.dbtables.ScannedHistory;
import com.idenisyss.myaadharscanner.utilities.AppConstants;

import java.util.List;

public class HistoryAdapter extends ListAdapter<ScannedHistory, HistoryAdapter.MyHistoryView> {
    private static final String TAG_NAME = HistoryAdapter.class.getName();
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
        holder.next_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(, QRScannerResult.class);
                i.putExtra("result", scannedData);
                i.putExtra(AppConstants.GENERATE_CODE_TYPE, AppConstants.QR_CODE);
                startActivity(i);
                finish();*/
            }
        });

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
