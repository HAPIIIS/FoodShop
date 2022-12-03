package com.example.projectmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class bayar extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private TextView name;
    private ImageView kembali, logout;
    private Button selesai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        logout = findViewById(R.id.logout);
        kembali = findViewById(R.id.kembali);
        selesai = findViewById(R.id.btnSelesai);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        name=findViewById(R.id.nama);
        if (firebaseUser!=null){
            name.setText(firebaseUser.getDisplayName());
        }else{
            name.setText("Login Gagal!");
        }


        selesai.setOnClickListener(v ->{

            startActivity(new Intent(getApplicationContext(), PilihWarungActivity.class));
            Toast.makeText(getApplicationContext(), "Pesanan Segera Diproses", Toast.LENGTH_SHORT).show();
            finish();
        });

        kembali.setOnClickListener(v ->{

            startActivity(new Intent(getApplicationContext(), cart.class));
            finish();
        });



        logout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
    }
}