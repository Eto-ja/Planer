package com.example.planer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class HouseFragment extends Fragment {

    public ListView list_task, list_target;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_house, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_task = view.findViewById(R.id.list_task);
        list_target = view.findViewById(R.id.list_target);

        SharedPreferences sp = getContext().getSharedPreferences("my_settings",
                Context.MODE_PRIVATE);
        String mail = sp.getString("email","вы не в аккаунте");
        dbmanager_task db = new dbmanager_task(getContext());
        List<String> res = db.show_tasks(mail);
        List<String> res_t = db.show_targets(mail);

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                res);
        ArrayAdapter<String> adapter_t = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                res_t);

        list_task.setAdapter(adapter);
        list_target.setAdapter(adapter_t);


        list_task.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String selectedItem = (String)parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("title", selectedItem);
                startActivity(intent);
            }
        });

        list_target.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String)parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("title", selectedItem);
                startActivity(intent);
            }
        });
    }
}