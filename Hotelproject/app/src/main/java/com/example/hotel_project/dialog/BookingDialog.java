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
import com.example.hotel_project.model.BookingScheduleDTO;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.model.RoomDTO;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class BookingDialog extends BottomSheetDialogFragment {

    public interface BookingListener {
        void onBookingConfirmed(String message);
    }

    private BookingListener bookingListener;
    private RoomDTO room;

    private Hotel hotel;

    private List<BookingScheduleDTO> bookingScheduleDTOList;

    private TextView textCheckIn, textCheckOut;

    public static BookingDialog newInstance(Hotel hotel, RoomDTO room, List<BookingScheduleDTO> bookingScheduleDTOList) {
        BookingDialog dialog = new BookingDialog();
        Bundle args = new Bundle();
        args.putSerializable("room", room);   // RoomDTO implements Serializable
        args.putSerializable("hotel", hotel); // Hotel also must implement Serializable
        args.putSerializable("bookedSchedules", (Serializable) bookingScheduleDTOList);
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
            bookingScheduleDTOList = (List<BookingScheduleDTO>) getArguments().getSerializable("bookedSchedules");

        }

        Spinner spinnerBookingType = view.findViewById(R.id.spinnerBookingType);
        LinearLayout layoutByDay = view.findViewById(R.id.layoutByDay);
        LinearLayout layoutByHour = view.findViewById(R.id.layoutByHour);
        Button btnTimePicker = view.findViewById(R.id.btnTimePicker);
        EditText editHourDuration = view.findViewById(R.id.editHourDuration);
        Button btnConfirmBooking = view.findViewById(R.id.btnConfirmBooking);
        textCheckIn = view.findViewById(R.id.textCheckIn);
        textCheckOut = view.findViewById(R.id.textCheckOut);
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
        textCheckIn.setOnClickListener(v -> showMaterialDatePickerDialog(textCheckIn));
        textCheckOut.setOnClickListener(v -> showMaterialDatePickerDialog(textCheckOut));


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
                if (checkIn.isEmpty() || checkOut.isEmpty()) {
                    Toast.makeText(getContext(), "Please select both check-in and check-out dates", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date dateCheckIn = sdf.parse(checkIn);
                    Date dateCheckOut = sdf.parse(checkOut);
                    Date now = new Date();
                    if (dateCheckOut.before(dateCheckIn)) {
                        Toast.makeText(getContext(), "Check-out date must be after or equal to check-in date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Check ngày check-in phải >= ngày hiện tại
                    if (dateCheckIn.before(now)) {
                        Toast.makeText(getContext(), "Check-in date must be today or later", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Check ngày check-out phải >= ngày hiện tại
                    if (dateCheckOut.before(now)) {
                        Toast.makeText(getContext(), "Check-out date must be today or later", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                    return;
                }

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
    private void showMaterialDatePickerDialog(final TextView textView) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        // Lấy thời gian bắt đầu ngày hôm nay theo múi giờ local
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayLocalStart = calendar.getTimeInMillis();
        long oneDayMillis = 24 * 60 * 60 * 1000;

        if (textView == textCheckIn) {
            long minDate = todayLocalStart + oneDayMillis; // ngày mai 00:00 local time
            constraintsBuilder.setStart(minDate);

            Set<Long> disabledDates = getDisabledDates();
            constraintsBuilder.setValidator(new DateValidatorExclude(disabledDates));

        } else if (textView == textCheckOut) {
            String checkInStr = textCheckIn.getText().toString();
            if (checkInStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn ngày Check-in trước", Toast.LENGTH_SHORT).show();
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date checkInDate = sdf.parse(checkInStr);
                long minDate = checkInDate.getTime();
                constraintsBuilder.setStart(minDate);

                Set<Long> disabledDates = getDisabledDates();
                constraintsBuilder.setValidator(new DateValidatorExclude(disabledDates));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Ngày Check-in không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(textView == textCheckIn ? "Chọn ngày Check-in" : "Chọn ngày Check-out")
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateStr = sdf.format(new Date(selection));

            if (textView == textCheckIn) {
                textCheckIn.setText(dateStr);

                String checkOutStr = textCheckOut.getText().toString();
                if (!checkOutStr.isEmpty()) {
                    try {
                        Date checkInDate = sdf.parse(dateStr);
                        Date checkOutDate = sdf.parse(checkOutStr);
                        if (checkOutDate.before(checkInDate)) {
                            textCheckOut.setText("");
                            Toast.makeText(getContext(), "Ngày Check-out phải lớn hơn hoặc bằng ngày Check-in", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (textView == textCheckOut) {
                String checkInStr = textCheckIn.getText().toString();
                if (!checkInStr.isEmpty()) {
                    try {
                        Date checkInDate = sdf.parse(checkInStr);
                        Date checkOutDate = sdf.parse(dateStr);
                        if (checkOutDate.before(checkInDate)) {
                            Toast.makeText(getContext(), "Ngày Check-out phải lớn hơn hoặc bằng ngày Check-in", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            textCheckOut.setText(dateStr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày Check-in trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    // Hàm kiểm tra 1 ngày có nằm trong bất kỳ khoảng đã book nào không
    private boolean isDateBooked(Calendar date) {
        if (bookingScheduleDTOList == null) return false;

        // Chuẩn hóa ngày chỉ còn ngày tháng năm (bỏ giờ phút giây)
        Calendar dayToCheck = (Calendar) date.clone();
        dayToCheck.set(Calendar.HOUR_OF_DAY, 0);
        dayToCheck.set(Calendar.MINUTE, 0);
        dayToCheck.set(Calendar.SECOND, 0);
        dayToCheck.set(Calendar.MILLISECOND, 0);

        for (BookingScheduleDTO schedule : bookingScheduleDTOList) {
            Date startDate = Date.from(schedule.getDateStart().atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(schedule.getDateEnd().atZone(ZoneId.systemDefault()).toInstant());

            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            start.set(Calendar.MILLISECOND, 0);

            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            end.set(Calendar.HOUR_OF_DAY, 0);
            end.set(Calendar.MINUTE, 0);
            end.set(Calendar.SECOND, 0);
            end.set(Calendar.MILLISECOND, 0);

            // Kiểm tra nếu ngày nằm trong khoảng từ start đến end (bao gồm cả 2 đầu)
            if (!dayToCheck.before(start) && !dayToCheck.after(end)) {
                return true; // Ngày đã bị đặt rồi
            }
        }
        return false; // Ngày chưa bị đặt
    }

    private Set<Long> getDisabledDates() {
        Set<Long> disabledDates = new HashSet<>();

        if (bookingScheduleDTOList == null) return disabledDates;

        for (BookingScheduleDTO schedule : bookingScheduleDTOList) {
            Calendar start = Calendar.getInstance();
            start.setTime(Date.from(schedule.getDateStart().atZone(ZoneId.systemDefault()).toInstant()));
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            start.set(Calendar.MILLISECOND, 0);
            Calendar end = Calendar.getInstance();
            end.setTime(Date.from(schedule.getDateEnd().atZone(ZoneId.systemDefault()).toInstant()));
            end.set(Calendar.HOUR_OF_DAY, 0);
            end.set(Calendar.MINUTE, 0);
            end.set(Calendar.SECOND, 0);
            end.set(Calendar.MILLISECOND, 0);
            while (!start.after(end)) {
                disabledDates.add(start.getTimeInMillis());
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return disabledDates;
    }
}
