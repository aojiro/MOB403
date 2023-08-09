package mob403.ph27046.slide8.exercise2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import mob403.ph27046.slide8.R;
import mob403.ph27046.slide8.exercise2.models.Items;
import mob403.ph27046.slide8.helpers.MyCallback;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>{
    private List<Items> list;
    private MyCallback myCallback;

    public ItemAdapter(List<Items> list, MyCallback myCallback) {
        this.list = list;
        this.myCallback = myCallback;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Items items = list.get(position);

        //  _UI
        holder.item_title.setText(items.getTitle());
        holder.item_isDone.setIconResource(items.isDone()?R.drawable.ic_done_all:R.drawable.ic_done);

        //  _event listener
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCallback.onClickUpdate(items,position);
            }
        });
        holder.item_isDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCallback.onClickUpdate(items,position);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCallback.onClickDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView item_title;
        private MaterialButton item_isDone,btn_delete;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            item_title = itemView.findViewById(R.id.item_title);
            item_isDone = itemView.findViewById(R.id.item_isDone);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
