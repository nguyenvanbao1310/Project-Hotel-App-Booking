package com.example.hotel_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.CancelledBookingAdapter;
import com.example.hotel_project.adapter.CompletedBookingAdapter;
import com.example.hotel_project.api.BookingOrderService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.BookingOrderDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelledBookingFragment extends Fragment {
    private RecyclerView recyclerView;

    private CancelledBookingAdapter completedBookingAdapter;

    private AccountDTO accountDTO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancelled, container, false);

        recyclerView = view.findViewById(R.id.recyclerCancel);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        accountDTO = SharedPreferencesManager.getAccountDTO(getActivity());

        BookingOrderService api = RetrofitClient.getRetrofit().create(BookingOrderService.class);
        Call<List<BookingOrderDTO>> call = api.getCancelledBookingOrdersByAccountId(accountDTO.getId());

        call.enqueue(new Callback<List<BookingOrderDTO>>() {
            @Override
            public void onResponse(Call<List<BookingOrderDTO>> call, Response<List<BookingOrderDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookingOrderDTO> orders = response.body();
                    completedBookingAdapter = new CancelledBookingAdapter(getActivity(), orders);
                    recyclerView.setAdapter(completedBookingAdapter);
                } else {
                    Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookingOrderDTO>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi gọi API: " + t.getMessage());
                Toast.makeText(getActivity(), "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
