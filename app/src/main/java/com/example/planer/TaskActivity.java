package com.example.planer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TaskActivity extends AppCompatActivity {

    private EditText text_name, text_description, text_date;
    private CheckBox checkBox;

    private String type = "Задача";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text_name = findViewById(R.id.text_name);
        text_description = findViewById(R.id.text_description);
        text_date = findViewById(R.id.text_date);

        checkBox = findViewById(R.id.checkBox);

        findViewById(R.id.radio_case).setOnClickListener(this::onRadioButtonClicked);
//        findViewById(R.id.radio_target).setOnClickListener(this::onRadioButtonClicked);
    }

    private void onRadioButtonClicked(View view) {
        RadioButton radio = (RadioButton) view;
        boolean checked = radio.isChecked();
        if (checked){
            type = "Задача";
        }
        else{
            type = "Цель";
        }
    }

    public void back(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void addDb(View view) {
        SharedPreferences sp = getSharedPreferences("my_settings",
                Context.MODE_PRIVATE);
        String mail = sp.getString("email","вы не в аккаунте");

        dbmanager_task db = new dbmanager_task(this);
        int res = db.add(text_name.getText().toString(), text_description.getText().toString(),
                text_name.getText().toString(), type, mail);

        if (res == -1){
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Успешно добавлено", Toast.LENGTH_LONG).show();
        }
        startActivity(new Intent(this, MainActivity.class));
    }
}