package com.example.lab3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyContactAdapter extends ArrayAdapter<Contact_Bai4> {


    List<Contact_Bai4> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyContactAdapter(Context context, List<Contact_Bai4> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Contact_Bai4 getItem(int position) {
        return contactList.get(position);
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewEmail;

        private ViewHolder(RelativeLayout rootView, ImageView imageView,
                           TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView)
                    rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView)
                    rootView.findViewById(R.id.textViewName);
            TextView textViewEmail = (TextView)
                    rootView.findViewById(R.id.textViewEmail);
            return new ViewHolder(rootView, imageView, textViewName,
                    textViewEmail);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent,
                    false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Contact_Bai4 item = getItem(position);
        vh.textViewName.setText(item.getName());
        vh.textViewEmail.setText(item.getEmail());


        //phương thức cũ: Picasso.with(context).load(item.getProfilePic()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);

        Picasso.get().load(item.getProfilePic()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);
        return vh.rootView;
    }
}
