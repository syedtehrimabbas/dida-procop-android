package com.dida.procop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgotPassActivity extends AppCompatActivity {

    AppCompatButton resetPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        resetPassBtn = findViewById(R.id.resetPassBtn);

        resetPassBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ForgotPassActivity.this, ResetPassActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.backBtn).setOnClickListener(view -> {
            onBackPressed();
        });
    }
}