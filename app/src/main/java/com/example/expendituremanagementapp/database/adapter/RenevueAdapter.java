package com.example.expendituremanagementapp.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.Renevue;

import java.util.List;

public class RenevueAdapter extends RecyclerView.Adapter<RenevueAdapter.RenevueViewAdapter>{
    private Context context;
    private List<Renevue> lists;

    public RenevueAdapter(Context context, List<Renevue> lists) {
        this.context = context;
        this.lists = lists;
    }

    public void setData(List<Renevue> list){
        this.lists = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RenevueViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.renevue_detail_item, parent, false);
        return new RenevueViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RenevueViewAdapter holder, int position) {
        Renevue renevue = lists.get(position);
        if (renevue == null)
            return;
        holder.name.setText(renevue.getName());
        holder.note.setText(renevue.getNote());
        holder.price.setText(renevue.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        if (lists != null)
        {
            return lists.size();
        }
        return 0;
    }

    public class RenevueViewAdapter extends RecyclerView.ViewHolder{
        private TextView name, note, price;
        public RenevueViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.revenue_detail_name);
            note = itemView.findViewById(R.id.revenue_detail_note);
            price = itemView.findViewById(R.id.revenue_detail_price);
        }
    }
}
