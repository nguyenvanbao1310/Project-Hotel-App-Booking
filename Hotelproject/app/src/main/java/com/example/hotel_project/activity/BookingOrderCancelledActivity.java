package com.example.hotel_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.BookingOrderDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.HotelOrderDTO;
import com.example.hotel_project.model.RoomDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BookingOrderCancelledActivity extends AppCompatActivity {
    private RoomDTO roomDTO;

    private String checkIn, checkOut;

    private Button btnBookAgain, btnAddReview;

    private TextView txtHotelName, txtHotelCity, txtHotelAddress;

    private ImageView imgHotel;

    private TextView txtTypeRoom, txtDays, txtCheckIn, txtCheckOut, txtNumbers;

    private TextView txtGuestName, txtEmail, txtPhone;

    private TextView txtPriceDays ,txtPrice, txtServiceFee, txtVatFee, txtTotalPriceBottom, txtTotalPrice;

    private long numDays;
    private AccountDTO accountDTO;
    private GuestDTO guestDTO;

    private HotelOrderDTO hotel;

    private BookingOrderDTO bookingOrderDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_order_detail);

        roomDTO = (RoomDTO) getIntent().getSerializableExtra("roomDTO");
        hotel = (HotelOrderDTO)  getIntent().getSerializableExtra("hotelDTO");
        bookingOrderDTO = (BookingOrderDTO)  getIntent().getSerializableExtra("bookingOrderDTO");

        Log.d("DATE", "DateStart: " + bookingOrderDTO.getDateStart());
        Log.d("DATE", "DateEnd: " + bookingOrderDTO.getDateEnd());

        Log.d("IDORDER", "IDORDER: " + bookingOrderDTO.getIdOrder());

        checkIn = bookingOrderDTO.getDateStart().toString();
        checkOut = bookingOrderDTO.getDateEnd().toString();

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
        btnBookAgain = findViewById(R.id.btnBookAgain);
        btnAddReview = findViewById(R.id.btnWriteReview);
        btnAddReview.setVisibility(View.GONE);
        Log.d("DEBUG_DATE", "DateStart raw: " + bookingOrderDTO.getDateStart());


        String Url = RetrofitClient.IMG_URL + hotel.getHotel_image_url();
        Glide.with(this)
                .load(Url)
                .into(imgHotel);

        txtHotelName.setText(hotel.getName());
        txtHotelCity.setText(hotel.getCity());
        txtHotelAddress.setText(hotel.getAddress());

        txtTypeRoom.setText(roomDTO.getRoomType());
        txtNumbers.setText(roomDTO.getBedNumber()+" bedroom - " + roomDTO.getBathNumber()+" bathroom / " + roomDTO.getBedNumber()*2 + " guest");
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        originalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Gán đúng múi giờ UTC

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        displayFormat.setTimeZone(TimeZone.getDefault());  // Hiển thị theo múi giờ thiết bị

        try {
            Date checkInDate = originalFormat.parse(bookingOrderDTO.getDateStart());
            Date checkOutDate = originalFormat.parse(bookingOrderDTO.getDateEnd());

            String checkIn = displayFormat.format(checkInDate);
            String checkOut = displayFormat.format(checkOutDate);

            txtCheckIn.setText(checkIn);
            txtCheckOut.setText(checkOut);

            long diffMillis = checkOutDate.getTime() - checkInDate.getTime();
            numDays = diffMillis / (1000 * 60 * 60 * 24);
            txtDays.setText(numDays + " Days");

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

        txtPriceDays.setText(String.valueOf(roomDTO.getPriceByDay()) +" x " + numDays +" Days");
        txtPrice.setText(String.format(Locale.getDefault(), "%.0fđ", price));
        txtServiceFee.setText(String.format(Locale.getDefault(), "%.0fđ", priceService));
        txtVatFee.setText(String.format(Locale.getDefault(), "%.0fđ", priceVat));
        txtTotalPrice.setText(String.format(Locale.getDefault(), "%.0fđ", totalPrice));

        btnBookAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingOrderCancelledActivity.this, HotelDetailActivity.class);
                intent.putExtra("hotel_id", bookingOrderDTO.getHotelId());
                startActivity(intent);
            }
        });
    }
}
