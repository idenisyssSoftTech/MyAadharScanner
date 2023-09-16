package com.abhiram.qrbarscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhiram.qrbarscanner.R;
import com.abhiram.qrbarscanner.ui.termscond.TermsCondModel;

import java.util.List;

public class TermsconditionAdapter extends RecyclerView.Adapter<TermsconditionAdapter.MyTermsHolder> {
    Context context;
    List<TermsCondModel> list;

    public TermsconditionAdapter(Context context, List<TermsCondModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TermsconditionAdapter.MyTermsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.terms_cond_list,parent,false);
        return new MyTermsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsconditionAdapter.MyTermsHolder holder, int position) {
        holder.terms_title.setText(list.get(position).getTitlename());
        holder.terms_desc.setText(list.get(position).getDescrtion());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyTermsHolder extends RecyclerView.ViewHolder {
        TextView terms_title,terms_desc;
        public MyTermsHolder(@NonNull View itemView) {
            super(itemView);
            terms_title = itemView.findViewById(R.id.terms_title);
            terms_desc = itemView.findViewById(R.id.terms_desc);
        }
    }
}
