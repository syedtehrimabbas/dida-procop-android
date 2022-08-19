package com.dida.procop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    ArrayList<String> catDataList = new ArrayList<>();
    RecyclerView categoryRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CatListAdapter catListAdapter;
    GridView brandsGridView;
    BrandsGridAdapter brandsGridAdapter;
    ArrayList<String> brandsData = new ArrayList<>();
    GridView thicknessGridView;
    BrandsGridAdapter thicknessGridAdapter;
    ArrayList<String> thicknessData = new ArrayList<>();

    ImageView closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        closeBtn = findViewById(R.id.closeBtn);
        brandsGridView = findViewById(R.id.brandsGridView);
        thicknessGridView = findViewById(R.id.thicknessGridView);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        catDataList.add("All");
        catDataList.add("Packaging");
        catDataList.add("Digital");
        catDataList.add("Office automation");
        catDataList.add("Creation");
        catDataList.add("Analogue");

        linearLayoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.HORIZONTAL, false);
        catListAdapter = new CatListAdapter(catDataList, FilterActivity.this);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        categoryRecyclerView.setAdapter(catListAdapter);


        brandsData.add("BURGO");
        brandsData.add("ESKA");
        brandsData.add("GMUND");
        brandsData.add("GYM");
        brandsData.add("SLYMO");
        brandsData.add("VILSA");
        brandsGridAdapter = new BrandsGridAdapter(FilterActivity.this, brandsData);
        brandsGridView.setNumColumns(3);
        brandsGridView.setAdapter(brandsGridAdapter);


        thicknessData.add("1.5 mm");
        thicknessData.add("1 mm");
        thicknessData.add("2 mm");
        thicknessData.add("3 mm");
        thicknessGridAdapter = new BrandsGridAdapter(FilterActivity.this, thicknessData);
        thicknessGridView.setNumColumns(3);
        thicknessGridView.setAdapter(thicknessGridAdapter);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}