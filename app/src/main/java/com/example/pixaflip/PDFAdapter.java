package com.example.pixaflip;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pixaflip.sql.MyDbHelper;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.Holder> {

    private List<pdf> list;
    private LayoutInflater inflater;
    ItemClickListener itemClickListener;
    
    MyDbHelper db;

    public interface ItemClickListener {
        void onItemClick(int pos);
        void ontogclick(int pos,boolean state);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }


    public PDFAdapter(List<pdf> list, Context context, MyDbHelper db) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.db = db;
    }

    @NonNull
    @Override
    public PDFAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.pdf_item,parent,false);
        Holder holder = new Holder(v,itemClickListener);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        
        holder.pdfName.setText(list.get(position).getPdfName());
        if(db.isExist(list.get(position).getPdfName()))
            holder.toggleButton.setChecked(true);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView pdfName;
        private ImageView imageView;
        public ToggleButton toggleButton;

        public Holder(View itemView,final ItemClickListener listener) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.TV);
            imageView = itemView.findViewById(R.id.IV);
            toggleButton = itemView.findViewById(R.id.favourite);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int p = getAdapterPosition();
                        if (p != RecyclerView.NO_POSITION){
                            listener.onItemClick(p);
                        }
                    }

                }
            });
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int p = getAdapterPosition();
                        boolean b = toggleButton.isChecked();
                        if (p != RecyclerView.NO_POSITION){
                            listener.ontogclick(p,b);
                        }
                }
            }});

            }
    }
}
