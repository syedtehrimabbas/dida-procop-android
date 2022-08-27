package com.dida.procop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ProductDetailsActivity extends AppCompatActivity {

    TabLayout productDetailTabLayout;
    TabItem tabDescription, tabInfo;
    ViewPager productDetailViewPager;
    ProductDetailsPagerAdapter pagerAdapter;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productDetailTabLayout = findViewById(R.id.productDetailTabLayout);
        tabDescription = findViewById(R.id.tabDescription);
        backBtn = findViewById(R.id.backBtn);
        tabInfo = findViewById(R.id.tabInfo);
        productDetailViewPager = findViewById(R.id.productDetailViewPager);

        pagerAdapter = new ProductDetailsPagerAdapter(getSupportFragmentManager(), productDetailTabLayout.getTabCount());
        productDetailViewPager.setAdapter(pagerAdapter);

        productDetailTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        productDetailViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailTabLayout));


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.favImage).setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailsActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.addToFavBtn).setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailsActivity.this, FavouritesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.addToCartBtn).setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.cartImage).setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
}