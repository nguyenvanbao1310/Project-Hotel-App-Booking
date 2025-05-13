package com.example.hotel_project.dialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.hotel_project.R;
import com.example.hotel_project.activity.BookingDetailActivity;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.RoomDTO;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingDialog extends BottomSheetDialogFragment {

    public interface BookingListener {
        void onBookingConfirmed(String message);
    }

    private BookingListener bookingListener;
    private RoomDTO room;

    private Hotel hotel;

    public static BookingDialog newInstance(Hotel hotel, RoomDTO room) {
        BookingDialog dialog = new BookingDialog();
        Bundle args = new Bundle();
        args.putSerializable("room", room);   // RoomDTO implements Serializable
        args.putSerializable("hotel", hotel); // Hotel also must implement Serializable
        dialog.setArguments(args);
        return dialog;
    }

    public void setBookingListener(BookingListener listener) {
        this.bookingListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_booking, container, false);

        if (getArguments() != null) {
            room = (RoomDTO) getArguments().getSerializable("room");
            hotel = (Hotel) getArguments().getSerializable("hotel");
        }

        Spinner spinnerBookingType = view.findViewById(R.id.spinnerBookingType);
        LinearLayout layoutByDay = view.findViewById(R.id.layoutByDay);
        LinearLayout layoutByHour = view.findViewById(R.id.layoutByHour);
        Button btnTimePicker = view.findViewById(R.id.btnTimePicker);
        EditText editHourDuration = view.findViewById(R.id.editHourDuration);
        Button btnConfirmBooking = view.findViewById(R.id.btnConfirmBooking);
        TextView textCheckIn = view.findViewById(R.id.textCheckIn);
        TextView textCheckOut = view.findViewById(R.id.textCheckOut);
        TextView textPriceDay = view.findViewById(R.id.textPriceByDay);
        TextView textPriceHour = view.findViewById(R.id.textPriceByHour);

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"By Day", "By Hour"});
        spinnerBookingType.setAdapter(adapter);

        spinnerBookingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { // "By Day"
                    layoutByDay.setVisibility(View.VISIBLE);
                    layoutByHour.setVisibility(View.GONE);
                    textPriceDay.setText("Price: "+ room.getPriceByDay()+ "đ");
                } else { // "By Hour"
                    layoutByDay.setVisibility(View.GONE);
                    layoutByHour.setVisibility(View.VISIBLE);
                    textPriceHour.setText("Price: "+ room.getPriceByHour()+ "đ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        final int[] selectedHour = {12};
        final int[] selectedMinute = {0};

        // TimePicker setup
        btnTimePicker.setOnClickListener(v -> {
            MaterialTimePicker picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(selectedHour[0])
                    .setMinute(selectedMinute[0])
                    .setTitleText("Select time")
                    .build();

            picker.show(getParentFragmentManager(), "timePicker");

            picker.addOnPositiveButtonClickListener(view1 -> {
                selectedHour[0] = picker.getHour();
                selectedMinute[0] = picker.getMinute();
                btnTimePicker.setText(String.format("%02d:%02d", selectedHour[0], selectedMinute[0]));
            });
        });

        // Open DatePicker when textCheckIn is clicked
        textCheckIn.setOnClickListener(v -> showDatePickerDialog(textCheckIn));
        textCheckOut.setOnClickListener(v -> showDatePickerDialog(textCheckOut));

        // Confirm booking button
        btnConfirmBooking.setOnClickListener(v -> {
            double price;
            String message;

            Intent intent = new Intent(getContext(), BookingDetailActivity.class);
            intent.putExtra("room", room); // truyền RoomDTO

            if (spinnerBookingType.getSelectedItemPosition() == 0) {
                // Booking by day
                price = room.getPriceByDay();
                String checkIn = textCheckIn.getText().toString();
                String checkOut = textCheckOut.getText().toString();

                message = "Book \"" + room.getRoomType() + "\"\nFrom: " + checkIn + "\nTo: " + checkOut;

                intent.putExtra("checkIn", checkIn);
                intent.putExtra("checkOut", checkOut);
                intent.putExtra("bookingType", "day");
                intent.putExtra("hotel", hotel);
            } else {
                // Booking by hour
                price = room.getPriceByHour();
                String hourDur = editHourDuration.getText().toString();
                String startTime = String.format("%02d:%02d", selectedHour[0], selectedMinute[0]);

                message = "Book \"" + room.getRoomType() + "\"\nStart: " + startTime + "\nFor: " + hourDur + " hours";

                intent.putExtra("startTime", startTime);
                intent.putExtra("hourDuration", hourDur);
                intent.putExtra("bookingType", "hour");
                intent.putExtra("hotel", hotel);
            }

            // AlertDialog xác nhận
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Booking Confirmation");
            builder.setMessage("Do you want to book the \"" + room.getRoomType() + "\" room for " + price + "đ?");
            builder.setPositiveButton("Confirm", (dialog, which) -> {
                dismiss();
                if (bookingListener != null) {
                    bookingListener.onBookingConfirmed(message);
                }
                startActivity(intent);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
        return view;
    }

    private void showDatePickerDialog(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, monthOfYear, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    textView.setText(sdf.format(selectedDate.getTime()));
                },
                year, month, day);

        datePickerDialog.show();
    }
}
