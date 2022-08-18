package com.dida.procop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ProductDetailsActivity extends AppCompatActivity {

    TabLayout productDetailTabLayout;
    TabItem tabDescription, tabInfo;
    ViewPager productDetailViewPager;
    ProductDetailsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productDetailTabLayout = findViewById(R.id. productDetailTabLayout);
        tabDescription = findViewById(R.id. tabDescription);
        tabInfo = findViewById(R.id. tabInfo);
        productDetailViewPager = findViewById(R.id. productDetailViewPager);

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

    }
}