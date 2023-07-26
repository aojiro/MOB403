package com.example.lab5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lab5.Interface.ItemClickListener;
import com.example.lab5.Model.Product;
import com.example.lab5.R;

import java.util.List;

public class AdapterListViewItemProduct extends BaseAdapter {
    private Context context;
    private List<Product> productList;


    public static ItemClickListener itemDeleteClickListener;
    public static ItemClickListener itemUpdateClickListener;

    public void setOnItemDeleteClickListener(ItemClickListener itemDeleteClickListener) {
        AdapterListViewItemProduct.itemDeleteClickListener = itemDeleteClickListener;
    }

    public void setOnItemUpdateClickListener(ItemClickListener itemUpdateClickListener) {
        AdapterListViewItemProduct.itemUpdateClickListener = itemUpdateClickListener;
    }

    public AdapterListViewItemProduct(Context context) {
        this.context = context;

    }

    public void setListProduct(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        if (this.productList.isEmpty()) {
            return 0;
        }
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        if (this.productList.isEmpty()) {
            return 0;
        }
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater inflater;
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview, null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName = view.findViewById(R.id.tv_name);
        viewHolder.iBtnUpdate = view.findViewById(R.id.iBtn_update);
        viewHolder.iBtnDelete = view.findViewById(R.id.iBtn_delete);
        Product product = productList.get(position);
        viewHolder.tvName.setText(product.getName());
        viewHolder.iBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemUpdateClickListener != null) {
                    itemUpdateClickListener.onItemClick(position);
                }
            }
        });
        viewHolder.iBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemDeleteClickListener != null) {
                    itemDeleteClickListener.onItemClick(position);
                }

            }
        });
        return view;
    }


    public class ViewHolder {
        TextView tvName;
        ImageButton iBtnUpdate;
        ImageButton iBtnDelete;
    }

}

