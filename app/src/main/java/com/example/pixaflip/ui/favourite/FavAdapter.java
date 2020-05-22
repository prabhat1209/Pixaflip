package com.example.pixaflip.ui.favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pixaflip.ItemClickListener;
import com.example.pixaflip.R;
import com.example.pixaflip.sql.Favourite;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.Holder> {

    private List<Favourite> list;
    ItemClickListener itemClickListener;
    Context context;


    public FavAdapter(List<Favourite> list, Context context, ItemClickListener itemClickListener) {
        this.list = list;
        this.context=context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_fav,parent,false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.pdfName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView pdfName;
        private ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.TV);
            imageView = itemView.findViewById(R.id.IV);

            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }
}
