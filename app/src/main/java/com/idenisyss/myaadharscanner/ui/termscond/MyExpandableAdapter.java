package com.idenisyss.myaadharscanner.ui.termscond;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.ui.qrcustumviews.ExpandableLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyExpandableAdapter extends RecyclerView.Adapter<MyExpandableAdapter.ViewHolder> {

    private final List<String> itemList;
    private final Set<Integer> expandedItems;

    public MyExpandableAdapter(List<String> itemList) {
        this.itemList = itemList;
        this.expandedItems = new HashSet<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.terms_expandable_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = itemList.get(position);

        holder.textMainContent.setText(item);

        boolean isExpanded = expandedItems.contains(position);

        // Set the initial visibility of the ExpandableLayout
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        //holder.expandableLayout.setContentText("");

        holder.itemView.setOnClickListener(v -> {
            if (isExpanded) {
                expandedItems.remove(position);
                holder.expandableLayout.setVisibility(View.GONE);
            } else {
                expandedItems.add(position);
                holder.expandableLayout.setVisibility(View.VISIBLE);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textMainContent;
        ExpandableListView expandableLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textMainContent = itemView.findViewById(R.id.textMainContent);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
        }
    }
}
