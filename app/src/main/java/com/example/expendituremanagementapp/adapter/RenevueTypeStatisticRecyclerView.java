package com.example.expendituremanagementapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.RenevueTypeStatistic;

import java.util.List;

public class RenevueTypeStatisticRecyclerView extends RecyclerView.Adapter<RenevueTypeStatisticRecyclerView.RenevueTypeStatisticViewHolder> {
    private LayoutInflater layoutInflater;
    private List<RenevueTypeStatistic> lsr;

    public RenevueTypeStatisticRecyclerView(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RenevueTypeStatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_renevue_statistic, parent, false);

        return new RenevueTypeStatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RenevueTypeStatisticViewHolder holder, int position) {
        if (lsr != null) {
            holder.tvRenevueType.setText(lsr.get(position).getName());
            holder.etTotalRenevueType.setText(lsr.get(position).getTotal().toString());
        }
    }

    @Override
    public int getItemCount() {
        if (lsr != null)
            return lsr.size();
        return 0;
    }

    public void setList(List<RenevueTypeStatistic> lsr) {
        this.lsr = lsr;
        notifyDataSetChanged();
    }

    public static class RenevueTypeStatisticViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRenevueType;
        private EditText etTotalRenevueType;

        public RenevueTypeStatisticViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRenevueType = itemView.findViewById(R.id.tvRenevueType);
            etTotalRenevueType = itemView.findViewById(R.id.etTotalRenevueType);
        }
    }
}
