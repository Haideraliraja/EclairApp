package com.example.android.eclairiosappv3.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.eclairiosappv3.Models.Item;
import com.example.android.eclairiosappv3.R;
import com.example.android.eclairiosappv3.Adapters.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    List<Item> item;

    public DashboardFragment() {

        //Nullary Constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = new ArrayList<>();

        item.add(new Item(R.drawable.checkin, "Mark Attendance"));
        item.add(new Item(R.drawable.leaverequest, "Leave Request"));
        item.add(new Item(R.drawable.attendance, "Attendance Details"));
        item.add(new Item(R.drawable.complain, "Complain"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        RVAdapter myAdapter = new RVAdapter(getActivity(), item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        myrv.setLayoutManager(linearLayoutManager);
        myrv.setAdapter(myAdapter);
        return view;
    }
}