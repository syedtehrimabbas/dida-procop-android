package com.dida.procop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> catDataList = new ArrayList<>();
    RecyclerView catRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CatListAdapter catListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        catRecyclerView = findViewById(R.id.catRecyclerView);
        catDataList.add("All");
        catDataList.add("Offset");
        catDataList.add("Digital");
        catDataList.add("Office automation");
        catDataList.add("Analogue");

        linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        catListAdapter = new CatListAdapter(catDataList, HomeActivity.this);
        catRecyclerView.setLayoutManager(linearLayoutManager);
        catRecyclerView.setAdapter(catListAdapter);

    }
}