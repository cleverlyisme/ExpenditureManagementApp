package com.example.expendituremanagementapp.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.RenevueType;

import java.util.ArrayList;
import java.util.List;

public class RenevueTypeAdapter extends RecyclerView.Adapter<RenevueTypeAdapter.RenevueTypeViewAdapter>{
    private Context context;
    private List<RenevueType> lists;

    public RenevueTypeAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<RenevueType> list){
        this.lists = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RenevueTypeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.renevue_type_item, parent, false);
        return new RenevueTypeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RenevueTypeViewAdapter holder, int position) {
        RenevueType renevueType = lists.get(position);
        if(renevueType == null)
            return;
        holder.name.setText(renevueType.getName());
    }

    @Override
    public int getItemCount() {
        if (lists != null)
        {
            return lists.size();
        }
        return 0;
    }

    public class RenevueTypeViewAdapter extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView img1, img2;
        public RenevueTypeViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.renevue_type_name);
        }
    }
}
