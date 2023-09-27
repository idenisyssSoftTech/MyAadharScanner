package com.abhiram.qrbarscanner.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.abhiram.qrbarscanner.R;
import com.abhiram.qrbarscanner.activities.QRScannerResult;
import com.abhiram.qrbarscanner.databases.dbtables.ScannedHistory;
import com.abhiram.qrbarscanner.databases.livedatamodel.ScannedLivedData;
import com.abhiram.qrbarscanner.utilities.AppConstants;
import com.abhiram.qrbarscanner.utilities.CodeUtils;

public class HistoryAdapter extends ListAdapter<ScannedHistory, HistoryAdapter.MyHistoryView> {
    Context context;
    private ScannedLivedData scannedLivedData;
    private Bitmap imageBitmap;
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
    public void onBindViewHolder(@NonNull HistoryAdapter.MyHistoryView holder, @SuppressLint("RecyclerView") int position) {
        String scannedData = getItem(position).data;
        String codetype =  getItem(position).codetype;
        byte[] imageByteArray = getItem(position).image;

        holder.scanned_date_time.setText(getItem(position).timedate);
        holder.discription_tv.setText(scannedData);
        holder.code_type.setText(codetype);

        if (imageByteArray != null) {
            // Convert the byte array back to a Bitmap and set it in the ImageView
            imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            holder.imageView.setImageBitmap(imageBitmap);
        }
        // Convert the Bitmap to a byte array
        byte[] byteArray = CodeUtils.convertBitTobyte(imageBitmap);
        holder.next_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, QRScannerResult.class);
                i.putExtra("result", scannedData);
                i.putExtra("image",byteArray);
                i.putExtra(AppConstants.GENERATE_CODE_TYPE,codetype);
                i.putExtra(AppConstants.HISTORY_PAGE,true);
                context.startActivity(i);
            }
        });
        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
//                int itemId = getItem(position).getId();
//                // scannedLivedData.deleteById(itemId);
//                showAlertDialog(itemId);
//                //update the fresh list of ArrayList data to recview
//                submitList(getCurrentList());
                int positionToDelete = holder.getAdapterPosition();
                if (positionToDelete != RecyclerView.NO_POSITION) {
                    int itemId = getItem(positionToDelete).getId();
                    // Call deleteById method to delete a specific item by its ID
                    showAlertDialog(itemId);
                }
            }
        });

    }

    public void getAppContext(FragmentActivity activity) {
        this.context = activity;
    }
    public int  getListSize(FragmentActivity activity) {
        this.context = activity;
        return getCurrentList().size();
    }

    public void setScannedLivedData(ScannedLivedData scannedLivedData) {
        this.scannedLivedData = scannedLivedData;
    }

    private void showAlertDialog(int itemId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert !");
        builder.setMessage("Do you want to Delete ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            scannedLivedData.deleteById(itemId);
            // The following line is important to automatically update the adapter
            notifyItemRemoved(itemId);
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.dismiss();
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static class MyHistoryView extends RecyclerView.ViewHolder {
        ImageView imageView, next_arrow, delete_item;
        TextView code_type, scanned_date_time, discription_tv;

        public MyHistoryView(@NonNull View itemView) {
            super(itemView);

            next_arrow = itemView.findViewById(R.id.next_arrow);
            discription_tv = itemView.findViewById(R.id.discription_tv);
            imageView = itemView.findViewById(R.id.imageView);
            code_type = itemView.findViewById(R.id.code_type);
            scanned_date_time = itemView.findViewById(R.id.scanned_date_time);
            delete_item = itemView.findViewById(R.id.delete_item);

        }
    }


}
