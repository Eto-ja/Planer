package com.example.planer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private EditText text_name_e, text_description_e;
    private TextView text_date_e;

    private Button btn_do_e, btn_yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String title = getIntent().getStringExtra("title");
//
        SharedPreferences sp = getSharedPreferences("my_settings",
                Context.MODE_PRIVATE);
        String mail = sp.getString("email","вы не в аккаунте");

        dbmanager_task db = new dbmanager_task(this);
        String[] res = db.edit_task(mail, title);

        text_name_e = findViewById(R.id.text_name_e);
        text_description_e = findViewById(R.id.text_description_e);
        text_date_e = findViewById(R.id.text_date_e);

        text_name_e.setText(title);
        text_description_e.setText(res[1]);
        text_date_e.setText(res[2]);

        btn_do_e = findViewById(R.id.btn_do_e);
        btn_yes = findViewById(R.id.btn_yes);

        btn_do_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.del_task(mail, title);
                back_e(v);
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(res[0].equals("Задача")){
                    db.del_task(mail, title);
                    SharedPreferences.Editor e = sp.edit();
                    String count = sp.getString("count","0");
                    String new_count = String.valueOf(Integer.parseInt(count) + 1);
                    e.putString("count", new_count);
                    e.apply();
                    back_e(v);
                }
                else{
                    back_e(v);
                }
            }
        });
    }

    public void back_e(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}