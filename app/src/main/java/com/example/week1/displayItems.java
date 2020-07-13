package com.example.week1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week1.provider.ItemViewModel;
import com.example.week1.provider.item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class displayItems extends AppCompatActivity {

    RecyclerView recyclerView;
    MyW6Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        adapter = new MyW6Adapter();
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setItems(newData);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

        });








    }
}

