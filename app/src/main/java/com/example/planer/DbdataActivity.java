package com.example.planer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DbdataActivity extends AppCompatActivity {

    EditText email, password;
    Button btn_ok, btn_back;
    TextView text_warning;
    private static String dbname = "users_planer.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dbdata);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_password);

        btn_ok = findViewById(R.id.btn_ok);
        btn_back = findViewById(R.id.btn_back);
        text_warning = findViewById(R.id.text_warning);
    }

    public void addDb(View view) {
//        btn_ok.setText(getDatabasePath(dbname).getAbsolutePath());
        dbmanager db = new dbmanager(this);
        int res = db.add(email.getText().toString(), password.getText().toString());

        if (res == 1){
            text_warning.setText("Такая почта уже зарегистрирована");
        }
        else if (res == -1){
            text_warning.setText("Не удалось произвести регистрацию");
        }
        else{
            SharedPreferences sp = getSharedPreferences("my_settings",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            String user = email.getText().toString();
            e.putString("email", user);
            e.putString("count", "0");
            e.apply();

            Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void sign_inDb(View view) {
        dbmanager db = new dbmanager(this);
        int res = db.sign_in(email.getText().toString(), password.getText().toString());

        if (res == -1){
            text_warning.setText("Неправильно введена почта или пароль");
        }
        else{
            Toast.makeText(this, "Выполнен вход в аккаунт", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}