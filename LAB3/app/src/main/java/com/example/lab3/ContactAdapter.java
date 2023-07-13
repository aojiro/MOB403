package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    Context context;
    ArrayList<Contact> contactList;

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.list_item, null);
            viewHolder.tv_Name = view.findViewById(R.id.name);
            viewHolder.tv_Mobile = view.findViewById(R.id.mobile);
            viewHolder.tv_Email = view.findViewById(R.id.email);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Contact contact = contactList.get(i);
        viewHolder.tv_Mobile.setText("0" + String.valueOf(contact.getMobile()));
        viewHolder.tv_Name.setText(contact.getName());
        viewHolder.tv_Email.setText(contact.getEmail());

        return view;
    }

    public static class ViewHolder {
        TextView tv_Name, tv_Mobile, tv_Email;
    }

}
