package com.example.hotel_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.BookingScheduleAdapter;
import com.example.hotel_project.adapter.ReviewAdapter;
import com.example.hotel_project.api.BookingScheduleService;
import com.example.hotel_project.api.ReviewApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.BookingScheduleDTO;
import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingBookingFragment extends Fragment {

    private RecyclerView recyclerUpcoming;

    private BookingScheduleAdapter bookingScheduleAdapter;

    private List<BookingScheduleDTO> bookingScheduleDTOList;

    private AccountDTO accountDTO;

    public UpcomingBookingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_upcoming, container, false);

        recyclerUpcoming = view.findViewById(R.id.recyclerUpcoming);

        recyclerUpcoming.setLayoutManager(new LinearLayoutManager(getContext()));

        bookingScheduleDTOList = new ArrayList<>();

        accountDTO = SharedPreferencesManager.getAccountDTO(getActivity());

        BookingScheduleService apiService = RetrofitClient.getRetrofit().create(BookingScheduleService.class);
        Call<List<BookingScheduleDTO>> call = apiService.getBookingSchedules(accountDTO.getId());
        call.enqueue(new Callback<List<BookingScheduleDTO>>() {
            @Override
            public void onResponse(Call<List<BookingScheduleDTO>> call, Response<List<BookingScheduleDTO>> response) {
                if (response.isSuccessful()) {
                    bookingScheduleDTOList.clear();
                    bookingScheduleDTOList =  response.body();
                    System.out.println("Booking schedules:");
                    if (bookingScheduleDTOList != null) {
                        for (BookingScheduleDTO dto : bookingScheduleDTOList) {
                            System.out.println(dto); // toString()
                        }
                    }
                    bookingScheduleAdapter = new BookingScheduleAdapter(getActivity(), bookingScheduleDTOList);
                    recyclerUpcoming.setAdapter(bookingScheduleAdapter);
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e("BookingSchedule", "Error response: " + response.code() + " - " + errorBody);
                    } catch (IOException e) {
                        Log.e("BookingSchedule", "Error parsing errorBody", e);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<BookingScheduleDTO>> call, Throwable t) {
                Log.e("BookingSchedule", "API Failure: ", t);
            }
        });

        return view;
    }
}

