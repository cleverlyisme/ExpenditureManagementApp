package com.example.expendituremanagementapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.model.Renevue;
import com.example.expendituremanagementapp.model.RenevueType;
import com.example.expendituremanagementapp.ui.renevue.RenevueDetailViewModel;
import com.example.expendituremanagementapp.ui.renevue.RenevueTypeFragment;

import java.time.LocalDate;
import java.util.List;

public class RenevueTypeAdapter extends RecyclerView.Adapter<RenevueTypeAdapter.RenevueTypeViewAdapter>{
    private RenevueTypeFragment context;
    private List<RenevueType> lists;

    public RenevueTypeAdapter(RenevueTypeFragment context, List<RenevueType> lists) {
        this.context = context;
        this.lists = lists;
    }
    public void setData(List<RenevueType> lists){
        this.lists = lists;
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = renevueType.getUserId();
                if(id == 1 && !context.checkUserId(id)){
                    Toast.makeText(v.getContext(), "Loại thưởng này không thể xóa!", Toast.LENGTH_SHORT).show();
                }
                else
                    context.delete(renevueType.getName(), renevueType.getId());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = renevueType.getUserId();
                if(id == 1 && !context.checkUserId(id)){
                    Toast.makeText(v.getContext(), "Loại thưởng này không thể sửa!", Toast.LENGTH_SHORT).show();
                }
                else
                    context.edit(renevueType.getName(), renevueType.getId());
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

    public class RenevueTypeViewAdapter extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView edit, delete;
        private RelativeLayout layout;
        public RenevueTypeViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.renevue_type_item_name);
            edit = itemView.findViewById(R.id.renevue_type_item_edit);
            delete = itemView.findViewById(R.id.renevue_type_item_delete);
            layout = itemView.findViewById(R.id.renevue_type_item_layout);
        }
    }
}
