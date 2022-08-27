package com.dida.procop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    GridView favGridView;
    ImageView closeBtn;
    FavGridAdapter favGridAdapter;
    ArrayList<String> prodNamesData = new ArrayList<>();
    ArrayList<Integer> imgData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        favGridView = findViewById(R.id.favGridView);
        closeBtn = findViewById(R.id.closeBtn);

        prodNamesData.add("IQ COLOR PASTEL – FLAMMAND OPI174");
        prodNamesData.add("ENVELOPE\nINSERTION");
        prodNamesData.add("SELF-ADHESIVE EVERYDAY ENVELOPE");
        prodNamesData.add("IQ COLOR PASTEL – FLAMMAND OPI174");
        imgData.add(R.drawable.sample_image2);
        imgData.add(R.drawable.sample_image1);
        imgData.add(R.drawable.sample_image3);
        imgData.add(R.drawable.sample_image2);
        favGridAdapter = new FavGridAdapter(FavouritesActivity.this, prodNamesData, imgData);
        favGridView.setNumColumns(2);
        favGridView.setAdapter(favGridAdapter);

        closeBtn.setOnClickListener(view -> finish());

    }
}