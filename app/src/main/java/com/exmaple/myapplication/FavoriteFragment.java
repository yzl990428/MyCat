package com.exmaple.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private CatAdapter catAdapter;
    private Gson gson = new Gson();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        catAdapter = new CatAdapter();
        recyclerView.setAdapter(catAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Cat", MODE_PRIVATE);
        String json = sharedPreferences.getString("favourites", "null");
        if (!json.equals("null")){
            Type type = new TypeToken<ArrayList<Cat>>(){}.getType();
            ArrayList<Cat> cats = gson.fromJson(json, type);
            catAdapter.setData(cats);
        }


    }
}
