package com.example.planer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_house, btn_calendar, btn_grafic;
    private TextView txtvw_title, textView;

    public CalendarFragment calendarFragment = new CalendarFragment();
    public HouseFragment houseFragment = new HouseFragment();
    public GrafFragment grafFragment = new GrafFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_house = findViewById(R.id.btn_house);
        btn_calendar = findViewById(R.id.btn_calendar);
        btn_grafic = findViewById(R.id.btn_grafic);

        txtvw_title = findViewById(R.id.txtvw_title);
        textView = findViewById(R.id.textView);

        btn_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_house.setImageResource(R.drawable.house_on);
                btn_calendar.setImageResource(R.drawable.calendar_off);
                btn_grafic.setImageResource(R.drawable.grafic_off);
                txtvw_title.setText(R.string.home);
                setNewFragment(houseFragment);
            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_calendar.setImageResource(R.drawable.calendar_on);
                btn_house.setImageResource(R.drawable.house_off);
                btn_grafic.setImageResource(R.drawable.grafic_off);
                txtvw_title.setText(R.string.calendar);
                setNewFragment(calendarFragment);
            }
        });

        btn_grafic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_calendar.setImageResource(R.drawable.calendar_off);
                btn_house.setImageResource(R.drawable.house_off);
                btn_grafic.setImageResource(R.drawable.grafic_on);
                txtvw_title.setText(R.string.progress);
                setNewFragment(grafFragment);
            }
        });

        setNewFragment(houseFragment);

//        dbmanager("add");
        SharedPreferences sp = getSharedPreferences("my_settings",
                Context.MODE_PRIVATE);
//        SharedPreferences.Editor start = sp.edit();
//        start.clear();
//        start.commit();
        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        String mail = sp.getString("email","вы не в аккаунте");
        textView.setText(mail);

        if (!hasVisited) {
            dbmanager("add");
            // выводим нужную активность
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);// не забудьте подтвердить изменения
            e.commit();
        }

    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void beforeWork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Регистрация/вход")
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage("Чтобы воспользоваться приложением необходимо войти в аккаунт или зарегистрироваться")
                .setCancelable(false)
                .setPositiveButton("Войти", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbmanager("sign_in");
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Регистрация", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dbmanager("add");
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showInfo(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void dbmanager(String f){
//        new dbmanager(this);
        if (f.equals("add")){
            startActivity(new Intent(this, DbdataActivity.class));
        }
    }

}