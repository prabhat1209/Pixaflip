package com.example.pixaflip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ToggleButton;

import com.example.pixaflip.sql.Favourite;
import com.example.pixaflip.sql.MyDbHelper;
import com.example.pixaflip.ui.favourite.FavAdapter;
import com.example.pixaflip.ui.favourite.SlideshowFragment;

import java.util.ArrayList;
import java.util.List;

public class DisplayPdfActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    static List<pdf> list;
    ToggleButton toggleButton;
    PDFAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pdf);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        toggleButton = (ToggleButton)findViewById(R.id.favourite);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;



        MyDbHelper db = new MyDbHelper(DisplayPdfActivity.this);

        list = new ArrayList<>();
        list.add(new pdf("Stories ","https://www.bartleby.com/ebook/adobe/3134.pdf"));
        list.add(new pdf("C++ ", "https://www.tutorialspoint.com/cplusplus/cpp_tutorial.pdf"));
        list.add(new pdf("Java ","https://www.tutorialspoint.com/java/java_tutorial.pdf"));
        list.add(new pdf("DSA ","https://www.tutorialspoint.com/data_structures_algorithms/data_structures_algorithms_tutorial.pdf"));
        list.add(new pdf("Python ","http://tdc-www.harvard.edu/Python.pdf"));

        //Log.i("Size", String.valueOf(list.size()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PDFAdapter(list,this);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PDFAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(DisplayPdfActivity.this,PDFActivity.class);
                intent.putExtra("position",pos);
                startActivity(intent);
            }

            @Override
            public void ontogclick(int pos,boolean state) {

                MyDbHelper db = new MyDbHelper(DisplayPdfActivity.this);
                if(state){
                    int x = pos;
                    System.out.println(db.isExist(list.get(x).getPdfName()));
                    if(!db.isExist(list.get(x).getPdfName())){
                        String url = list.get(x).getPdfUrl();
                        String name = list.get(x).getPdfName();
                        Favourite pro = new Favourite(name,url);
                        db.addFavourite(pro);
                    }

                }else{
                    if(db.isExist(list.get(pos).getPdfName())){
                        String name = list.get(pos).getPdfName();
                        db.deleteById(name);
                    }
                }
            }
        });

        //create recycler view in this activity and use it to display pdf files.


    }
}

