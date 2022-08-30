package com.dida.procop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class FaqActivity extends AppCompatActivity {

    RecyclerView faqRV;
    ImageView backBtn;
    ArrayList<FaqData> faqDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faqRV = findViewById(R.id.faqRV);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initData();
        initRecyclerView();

    }

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FaqActivity.this, LinearLayoutManager.VERTICAL, false);
        FaqAdapter faqAdapter = new FaqAdapter(faqDataList, FaqActivity.this);
        faqRV.setLayoutManager(linearLayoutManager);
        faqRV.setAdapter(faqAdapter);
    }

    public void initData() {
        faqDataList = new ArrayList<>();
        faqDataList.add(new FaqData("How do I create my account on the PROCOP eShop?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("How do I select the items I want to order?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("Are all the items visible on the site available?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("What is the minimum amount of sale quantity?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("How do I create my account on the PROCOP eShop?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("How do I select the items I want to order?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("Are all the items visible on the site available?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("What is the minimum amount of sale quantity?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("How do I create my account on the PROCOP eShop?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
        faqDataList.add(new FaqData("How do I select the items I want to order?", "If you are not already a PROCOP customer: click on “My account” at the bottom of the screen. Fill in the fields of the form, and you will then receive a confirmation email.\n" +
                "The “Email” field (your login), is essential to be recognized during your first connection with your personal password.\n" +
                "\n" +
                "If you are already a PROCOP customer through the old website, you will have to recreate your account."));
    }

}