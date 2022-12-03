package com.example.projectmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Nasipadang extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private TextView name, namamakanan,harga ;
    private ImageView logout,minus,tambah;
    private CardView seblak, nasipadang, ayambakar,nasiuduk;
    private TextView input;
    private Button cart;
    private int inputjml,total,harga2,inputan;
    private String totalharga;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasipadang);

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

        nasipadang = findViewById(R.id.card3);
        nasipadang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(Nasipadang.this, Nasipadang.class);
                startActivity(pindah);
            }
        });

        seblak = findViewById(R.id.card2);
        seblak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(Nasipadang.this, seblak.class);
                startActivity(pindah);
            }
        });

        ayambakar = findViewById(R.id.card4);
        ayambakar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(Nasipadang.this, Ayambakar.class);
                startActivity(pindah);
            }
        });

       nasiuduk = findViewById(R.id.card1);
        nasiuduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(Nasipadang.this, PilihWarungActivity.class);
                startActivity(pindah);
            }
        });

        logout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

        harga = findViewById(R.id.harga);
        namamakanan = findViewById(R.id.namamakanan);
        input = findViewById(R.id.inputjumlah);
        cart = (Button) findViewById(R.id.btnCart);

        minus = findViewById(R.id.minus);
        tambah = findViewById(R.id.tambah);
        inputan = 0;
        input.setText(String.valueOf(inputan));


        tambah.setOnClickListener( v  -> {
            inputan += 1;
            input.setText(String.valueOf(inputan));
        });

        minus.setOnClickListener( v -> {
            if ( inputan > 0) {
                inputan -= 1;
                input.setText(String.valueOf(inputan));
            }else {
                input.setText(String.valueOf(inputan));
            }
        });


        cart.setOnClickListener(v -> {
            if (input.getText().length() > 0 && namamakanan.getText().length()>0
                    && harga.getText().length()>0 && name.getText().length()>0 ) {
                inputjml= Integer.parseInt(input.getText().toString());
                harga2 = Integer.parseInt(harga.getText().toString());
                if (inputjml > 0){
                    total = inputjml * harga2;
                    totalharga = String.valueOf(total);

                    saveData(input.getText().toString(), namamakanan.getText().toString(),
                            harga.getText().toString(), name.getText().toString(), totalharga);
                    startActivity(new Intent(getApplicationContext(),cart.class));
                    finish();}else{
                    Toast.makeText(getApplicationContext(), "Silahkan isi jumlah", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan isi jumlah", Toast.LENGTH_SHORT).show();

            }

        });
    }
    private void saveData ( String input, String namamakanan, String harga, String name, String totalharga){
        Map<String, Object> user = new HashMap<>();
        user.put("input", input);
        user.put("makanan",namamakanan);
        user.put("harga", harga);
        user.put("user", name);
        user.put("total",totalharga);


        db.collection("pesanan")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });












    }




}