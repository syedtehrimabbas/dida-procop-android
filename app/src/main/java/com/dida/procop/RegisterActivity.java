package com.dida.procop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class RegisterActivity extends AppCompatActivity {

    AppCompatButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.registerButton);

//        Spannable word = new SpannableString("Note: ");
//        word.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appPink)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        noteTextView.setText(word);
//
//        Spannable wordTwo = new SpannableString("A password will be sent to your\nemail address");
//        wordTwo.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        noteTextView.append(wordTwo);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(view -> {
            onBackPressed();
        });

        findViewById(R.id.loginScreenBtn).setOnClickListener(view -> {
            onBackPressed();
        });

    }
}