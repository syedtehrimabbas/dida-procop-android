package com.dida.procop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> catDataList = new ArrayList<>();
    RecyclerView catRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CatListAdapter catListAdapter;
    ImageSlider image_slider;
    LinearLayout scrollLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        image_slider = findViewById(R.id.image_slider);
        scrollLayout1 = findViewById(R.id.scrollLayout1);
        catRecyclerView = findViewById(R.id.catRecyclerView);
        catDataList.add("All");
        catDataList.add("Offset");
        catDataList.add("Digital");
        catDataList.add("Office automation");
        catDataList.add("Analogue");
        catDataList.add("Creation");

        linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        catListAdapter = new CatListAdapter(catDataList, HomeActivity.this);
        catRecyclerView.setLayoutManager(linearLayoutManager);
        catRecyclerView.setAdapter(catListAdapter);



        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.slider1, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.slider2, ScaleTypes.CENTER_CROP));

        image_slider.setImageList(imageList);


        scrollLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                startActivity(intent);
            }
        });

    }
}