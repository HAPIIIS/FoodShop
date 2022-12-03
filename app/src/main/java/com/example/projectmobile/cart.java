package com.example.projectmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmobile.adapter.pesananadapter;
import com.example.projectmobile.model.pesanan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import com.example.projectmobile.adapter.pesananadapter;
import com.google.firebase.firestore.auth.User;

public class cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView namamakanan,harga ,jumlah;
    private ImageView logout, kembali, btnHapus;
    private CardView seblak, nasipadang, ayambakar;
    private List <pesanan> list = new ArrayList<>();
    private pesananadapter pesananadapter;
    private TextView name;
    private FirebaseUser firebaseUser;
    private Button checkout;
    private ProgressDialog progressDialog;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        name = findViewById(R.id.nama);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        logout = findViewById(R.id.logout);
        kembali = findViewById(R.id.kembali);

        checkout = findViewById(R.id.btnCheckout);
        progressDialog = new ProgressDialog(cart.this);
        progressDialog.setTitle("Loading");
        if (firebaseUser!=null){
            name.setText(firebaseUser.getDisplayName());
        }else{
            name.setText("Login Gagal!");
        }

        kembali.setOnClickListener(v ->{

            startActivity(new Intent(getApplicationContext(), PilihWarungActivity.class));
            finish();
        });

        checkout.setOnClickListener(v ->{

            startActivity(new Intent(getApplicationContext(), bayar.class));
            finish();
        });

        logout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

        recyclerView = findViewById(R.id.recyclerview);
        namamakanan = findViewById(R.id.namamakanan);
        harga = findViewById(R.id.harga);
        jumlah = findViewById(R.id.jumlah);

        pesananadapter = new pesananadapter(getApplicationContext(),list);
        pesananadapter.setDialog(new pesananadapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(cart.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        deleteData(list.get(pos).getId());



                    }

                });
                dialog.show();



            }
        }) ;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(pesananadapter);

        getData();




        }

    private void getData(){
        progressDialog.show();
        db.collection("pesanan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                pesanan Pesanan = new pesanan(document.getString("makanan"), document.getString("input"),
                                        document.getString("harga"), document.getString("total"));
                                Pesanan.setId(document.getId());
                                list.add(Pesanan);

                            }
                            pesananadapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data gagal diambil", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteData(String id){

        db.collection("pesanan").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                        }

                        getData();
                    }
                });
    }
}