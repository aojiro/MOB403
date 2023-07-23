package com.example.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment.Model.Account;
import com.example.assignment.R;

import java.util.List;

public class AdapterListViewItemAccount extends BaseAdapter {
    private Context context;
    private List<Account> accountList;

    public AdapterListViewItemAccount(Context context) {
        this.context = context;
    }

    public void setListProduct(List<Account> accountList) {
        this.accountList = accountList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return accountList != null ? accountList.size() : 0;
    }

    @Override
    public Account getItem(int position) {
        return accountList != null ? accountList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.awnitem_dish, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvAccountID = convertView.findViewById(R.id.tv_id);
            viewHolder.tvAccountName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvAccountEmail = convertView.findViewById(R.id.tv_email);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Account account = getItem(position);
        if (account != null) {
            viewHolder.tvAccountID.setText(String.valueOf(account.getId())); // Chuyển id thành chuỗi và hiển thị
            viewHolder.tvAccountName.setText(account.getName());
            viewHolder.tvAccountEmail.setText(account.getEmail());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvAccountName;
        TextView tvAccountEmail;
        TextView tvAccountID;
    }
}