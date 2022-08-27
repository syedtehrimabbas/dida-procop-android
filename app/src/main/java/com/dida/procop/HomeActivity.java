package com.dida.procop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> catDataList = new ArrayList<>();
    RecyclerView catRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CatListAdapter catListAdapter;
    ImageSlider image_slider;
    LinearLayout scrollLayout1;
    ImageView filterBtn, favImage;
    DrawerLayout drawerLayout;
    ImageView drawerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerButton = findViewById(R.id.drawerButton);
        drawerLayout = findViewById(R.id.drawerLayout);

        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        image_slider = findViewById(R.id.image_slider);
        filterBtn = findViewById(R.id.filterBtn);
        favImage = findViewById(R.id.favImage);
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


        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });

        favImage.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.cartImage).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.closeBtn).setOnClickListener(view -> {
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        findViewById(R.id.homeNav).setOnClickListener(this);
        findViewById(R.id.orderNav).setOnClickListener(this);
        findViewById(R.id.profileNav).setOnClickListener(this);
        findViewById(R.id.settingNav).setOnClickListener(this);
        findViewById(R.id.faqNav).setOnClickListener(this);
        findViewById(R.id.aboutNav).setOnClickListener(this);
        findViewById(R.id.contactNav).setOnClickListener(this);
        findViewById(R.id.logoutNav).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.orderNav) {
            startActivity(new Intent(HomeActivity.this, OrdersActivity.class));
        } else if (v.getId() == R.id.profileNav) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        } else if (v.getId() == R.id.settingNav) {

        } else if (v.getId() == R.id.faqNav) {

        } else if (v.getId() == R.id.aboutNav) {

        } else if (v.getId() == R.id.contactNav) {

        } else if (v.getId() == R.id.logoutNav) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finishAffinity();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }
}