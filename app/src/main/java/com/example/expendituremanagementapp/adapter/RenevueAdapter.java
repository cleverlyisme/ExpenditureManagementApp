package com.example.expendituremanagementapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.Renevue;
import com.example.expendituremanagementapp.ui.renevue.RenevueDetailFragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RenevueAdapter extends RecyclerView.Adapter<RenevueAdapter.RenevueViewAdapter>{
    private RenevueDetailFragment context;
    private List<Renevue> lists;

    public RenevueAdapter(RenevueDetailFragment context, List<Renevue> lists) {
        this.context = context;
        this.lists = lists;
    }
    public void setData(List<Renevue> lists){
        this.lists = lists;
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
        holder.price.setText(renevue.getPrice() + "");

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(renevue.getId());
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(renevue.getNote().isEmpty()){
                    renevue.setNote("Bạn không có ghi chú!");
                }

                AlertDialog.Builder buider = new AlertDialog.Builder(v.getContext());
                buider.setTitle("Ghi chú");
                buider.setMessage("Tên: "+renevue.getName()+"\nSố tiền: "+renevue.getPrice()+"\nGhi chú: "+renevue.getNote()+"\nThời gian: "+renevue.getDate());
                buider.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                buider.create().show();
            }
        });
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
        private LinearLayout layout;
        private ImageButton btn;
        public RenevueViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.revenue_detail_name);
            price = itemView.findViewById(R.id.revenue_detail_price);
            btn = itemView.findViewById(R.id.btn_renevue_item_delete);
            layout = itemView.findViewById(R.id.renevue_item_layout);
        }
    }
}
