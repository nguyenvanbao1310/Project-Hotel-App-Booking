package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;

public class ThankYouActivity extends AppCompatActivity {

    private Button backHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou); // XML bạn vừa tạo

        backHomeButton = findViewById(R.id.backHomeButton);

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HotelActivity
                Intent intent = new Intent(ThankYouActivity.this, HotelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
            }
        });
    }
}