package com.example.planer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sign_inActivity extends AppCompatActivity {

    EditText email, password;
    Button btn_ok;
    private static String dbname = "users_planer.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_password);

        btn_ok = findViewById(R.id.btn_ok);
    }

    public void sign_inDb(View view) {
        dbmanager db = new dbmanager(this);
        int res = db.sign_in(email.getText().toString(), password.getText().toString());

        Toast.makeText(this, res, Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, MainActivity.class));
    }
}