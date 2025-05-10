package com.example.hotel_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.HotelAdapter;
import com.example.hotel_project.adapter.NearbyHotelAdapter;
import com.example.hotel_project.api.HotelApiService;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.Hotel;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewNearby;
    private HotelAdapter adapter;
    private NearbyHotelAdapter nearbyHotelAdapter;
    private List<Hotel> hotelList;
    private TextView textUserName;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        GuestDTO guestDTO = SharedPreferencesManager.getGuestDTO(getActivity());

        textUserName = view.findViewById(R.id.textUserName);
        textUserName.setText(guestDTO.getFullname());

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewHotels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewNearby = view.findViewById(R.id.recyclerViewHotelsNearby);
        recyclerViewNearby.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Khởi tạo Retrofit và gọi API để lấy dữ liệu khách sạn
        HotelApiService apiService = RetrofitClient.getRetrofit().create(HotelApiService.class);
        Call<List<Hotel>> call = apiService.getHotels();

        // Gọi API và nhận phản hồi
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    hotelList = response.body();
                    adapter = new HotelAdapter(getActivity(), hotelList);
                    nearbyHotelAdapter = new NearbyHotelAdapter(getActivity(), hotelList);
                    recyclerView.setAdapter(adapter);
                    recyclerViewNearby.setAdapter(nearbyHotelAdapter);
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Error: " + t.getMessage());
            }
        });

        return view; // Đảm bảo trả về view sau khi các bước trên
    }
}
