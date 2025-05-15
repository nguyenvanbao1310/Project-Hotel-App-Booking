package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.RoomDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingDetailActivity extends AppCompatActivity {

    private RoomDTO roomDTO;

    private String checkIn, checkOut;

    private TextView txtHotelName, txtHotelCity, txtHotelAddress;

    private ImageView imgHotel;

    private TextView txtTypeRoom, txtDays, txtCheckIn, txtCheckOut, txtNumbers;

    private TextView txtGuestName, txtEmail, txtPhone;

    private TextView txtPriceDays ,txtPrice, txtServiceFee, txtVatFee, txtTotalPriceBottom, txtTotalPrice;

    private long numDays;
    private AccountDTO accountDTO;
    private GuestDTO guestDTO;

    private Hotel hotel;

    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        roomDTO = (RoomDTO) getIntent().getSerializableExtra("room");
        hotel = (Hotel)  getIntent().getSerializableExtra("hotel");
        String bookingType = getIntent().getStringExtra("bookingType");

        if (bookingType.equals("day")) {
            checkIn = getIntent().getStringExtra("checkIn");
            checkOut = getIntent().getStringExtra("checkOut");

        } else if (bookingType.equals("hour")) {
            String startTime = getIntent().getStringExtra("startTime");
            String hourDuration = getIntent().getStringExtra("hourDuration");
        }

        imgHotel = findViewById(R.id.imgHotel);
        txtHotelName = findViewById(R.id.txtHotelName);
        txtHotelCity = findViewById(R.id.txtHotelCity);
        txtHotelAddress = findViewById(R.id.txtHotelAddress);
        txtTypeRoom = findViewById(R.id.textTypeRoom);
        txtDays = findViewById(R.id.txtDays);
        txtCheckIn = findViewById(R.id.txtCheckIn);
        txtCheckOut = findViewById(R.id.txtCheckOut);
        txtNumbers = findViewById(R.id.txtBedNumbers);
        txtGuestName = findViewById(R.id.txtGuestName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtPriceDays = findViewById(R.id.txtPriceDays);
        txtPrice = findViewById(R.id.txtPrice);
        txtServiceFee = findViewById(R.id.txtServiceFee);
        txtVatFee = findViewById(R.id.txtVatFee);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtTotalPriceBottom = findViewById(R.id.txtTotalPriceBottom);
        btnSubmit = findViewById(R.id.btnSubmit);

        String Url = RetrofitClient.IMG_URL + hotel.getHotel_image_url();
        Glide.with(this)
                .load(Url)
                .into(imgHotel);

        txtHotelName.setText(hotel.getName());
        txtHotelCity.setText(hotel.getCity());
        txtHotelAddress.setText(hotel.getAddress());

        txtTypeRoom.setText(roomDTO.getRoomType());
        txtCheckIn.setText(checkIn);
        txtCheckOut.setText(checkOut);
        txtNumbers.setText(roomDTO.getBedNumber()+" bedroom - " + roomDTO.getBathNumber()+" bathroom / " + roomDTO.getBedNumber()*2 + " guest");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date checkInDate = sdf.parse(checkIn);
            Date checkOutDate = sdf.parse(checkOut);
            long diffMillis = checkOutDate.getTime() - checkInDate.getTime();
            numDays = diffMillis / (1000 * 60 * 60 * 24);
            txtDays.setText("" + numDays + " Days");
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi định dạng ngày", Toast.LENGTH_SHORT).show();
        }

        guestDTO = SharedPreferencesManager.getGuestDTO(this);
        accountDTO = SharedPreferencesManager.getAccountDTO(this);

        txtGuestName.setText("Full Name: " + guestDTO.getFullname());
        txtEmail.setText("Email: " + accountDTO.getEmail());
        txtPhone.setText("Phone Number: " + accountDTO.getPhone());

        double price = roomDTO.getPriceByDay() * numDays;
        double priceService = (price * 5)/100;
        double priceVat = (price * 5)/100;

        double totalPrice = price + priceService + priceVat;

        txtPriceDays.setText(String.valueOf(roomDTO.getPriceByDay()) +" x " + numDays);

        txtPrice.setText(String.format(Locale.getDefault(), "%.0fđ", price));
        txtServiceFee.setText(String.format(Locale.getDefault(), "%.0fđ", priceService));
        txtVatFee.setText(String.format(Locale.getDefault(), "%.0fđ", priceVat));
        txtTotalPrice.setText(String.format(Locale.getDefault(), "%.0fđ", totalPrice));
        txtTotalPriceBottom.setText(String.format(Locale.getDefault(), "%.0fđ", totalPrice));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingDetailActivity.this, PaymentActivity.class);
                int totalPriceInt = (int) totalPrice;
                intent.putExtra("roomDTO", roomDTO);
                intent.putExtra("totalPrice", totalPriceInt );
                intent.putExtra("hotel", hotel);
                intent.putExtra("checkIn", checkIn);
                intent.putExtra("checkOut", checkOut);
                startActivity(intent);
            }
        });



    }
}
