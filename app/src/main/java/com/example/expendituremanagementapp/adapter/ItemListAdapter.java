package com.example.expendituremanagementapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.model.Item;

import java.util.List;

public class ItemListAdapter extends BaseAdapter {
    private Context context;
    private List<Item> items;


    public ItemListAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_list, parent, false);
        }

        TextView tvItemName = convertView.findViewById(R.id.tvItemName);
        TextView tvItemAmount = convertView.findViewById(R.id.tvItemAmount);

        String itemName = items.get(position).getName();
        int itemAmount = items.get(position).getAmount();

        // Set values for TextViews
        tvItemName.setText(itemName);
        tvItemAmount.setText(String.valueOf(itemAmount));

        // Code xử lý khác

        return convertView;
    }
}