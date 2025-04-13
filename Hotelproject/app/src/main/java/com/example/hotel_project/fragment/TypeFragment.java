package com.example.hotel_project.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel_project.R;
import com.example.hotel_project.adapter.RoomAdapter;
import com.example.hotel_project.api.HotelApiService;
import com.example.hotel_project.api.RoomApiService;
import com.example.hotel_project.model.RoomDTO;
import com.example.hotel_project.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private List<RoomDTO> roomList;

    public TypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewType);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        roomList = new ArrayList<>();
        roomAdapter = new RoomAdapter(this,roomList);
        recyclerView.setAdapter(roomAdapter);

        fetchRooms();

        return view;
    }

    private void fetchRooms() {
        RoomApiService apiService = RetrofitClient.getRetrofit().create(RoomApiService.class);
        Call<List<RoomDTO>> call = apiService.getAllRooms();

        call.enqueue(new Callback<List<RoomDTO>>() {
            @Override
            public void onResponse(Call<List<RoomDTO>> call, Response<List<RoomDTO>> response) {
                if (response.isSuccessful()) {
                    roomList.clear();
                    roomList.addAll(response.body());
                    roomAdapter.notifyDataSetChanged();
                } else {
                    Log.e("TypeFragment", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RoomDTO>> call, Throwable t) {
                Log.e("TypeFragment", "API Failure: ", t);
            }
        });
    }
}

