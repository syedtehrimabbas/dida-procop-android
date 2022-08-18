package com.dida.procop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ResetPassActivity extends AppCompatActivity {

    AppCompatButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordUpdatePopup("Password Updated!", "Your password has been changed successfully!",
                        "Ok");
            }
        });
    }


    public void passwordUpdatePopup(String title, String description, String confirmText) {

        final Dialog dialog = new Dialog(ResetPassActivity.this);
        dialog.setContentView(R.layout.password_updated);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        TextView questionText = dialog.findViewById(R.id.questionText);
        TextView detailText = dialog.findViewById(R.id.detailText);
        TextView confirmBtn = dialog.findViewById(R.id.okBtn);

        questionText.setText(title);
        detailText.setText(description);
        confirmBtn.setText(confirmText);

        confirmBtn.setOnClickListener(v -> {
            dialog.dismiss();

            Intent intent = new Intent(ResetPassActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });
        dialog.show();
    }

}