package com.example.hotel_project.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotel_project.R;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

public class MyInformationActivity extends AppCompatActivity {

    private EditText txtFullName, txtUserName, txtEmail, txtPhone, txtAddress;

    private TextView editText;

    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        txtFullName = findViewById(R.id.txtFullName);
        txtUserName = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);

        GuestDTO guestDTO = SharedPreferencesManager.getGuestDTO(this);
        AccountDTO accountDTO = SharedPreferencesManager.getAccountDTO(this);

        txtFullName.setText(guestDTO.getFullname());
        txtUserName.setText(accountDTO.getUsername());
        txtEmail.setText(accountDTO.getEmail());
        txtPhone.setText(accountDTO.getPhone());
        txtAddress.setText(guestDTO.getAddress());

        editText = findViewById(R.id.txtEdit);
        btnSave = findViewById(R.id.btnSave);

        setEditable(false);
        editText.setOnClickListener(v -> {
            setEditable(true);
        });

        btnSave.setOnClickListener(v -> {
            setEditable(false);
        });

    }
    private void setEditable(boolean enabled) {
        txtFullName.setEnabled(enabled);
        txtUserName.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
        txtPhone.setEnabled(enabled);
        txtAddress.setEnabled(enabled);
    }
}
