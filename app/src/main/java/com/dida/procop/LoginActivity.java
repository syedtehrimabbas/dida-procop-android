package com.dida.procop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView lostPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lostPassword = findViewById(R.id.lostPassword);

        lostPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.registerScreenBtn).setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.loginButton).setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }
}