package com.example.projectmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PilihWarungActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private TextView name;
    private ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_warung);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        name = findViewById(R.id.nama);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        logout = findViewById(R.id.logout);

        if (firebaseUser!=null){
            name.setText(firebaseUser.getDisplayName());
        }else{
            name.setText("Login Gagal!");
        }

        logout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
    }
}