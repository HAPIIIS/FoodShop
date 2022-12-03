package com.example.projectmobile.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.R;
import com.example.projectmobile.model.pesanan;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class pesananadapter extends RecyclerView.Adapter<pesananadapter.MyViewHolder> {
    private Context context;
    private List  <pesanan> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public Dialog setDialog(Dialog dialog){

        return this.dialog;
    }

    public pesananadapter(Context context, List<pesanan> list){
        this.context = context;
        this.list = list;
    }

    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowpesanan , parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyViewHolder holder, int position) {
        holder.namamakanan.setText(list.get(position).getNamamakanan());
        holder.jumlah.setText(list.get(position).getJumlah());
        holder.harga.setText(list.get(position).getHarga());
        holder.totalharga.setText(list.get(position).getTotalharga());
    //    holder.btnHapus.setOnClickListener(new View.OnClickListener() {
      //      @Override
   //         public void onClick(View view) {
     //           AlertDialog.Builder builder = new AlertDialog.Builder(context);
       //         builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
         //           @Override
           //         public void onClick(DialogInterface dialogInterface, int i) {
             //          database.child("pesanan").child()
               //     }
             //   }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
               //     @Override
                 //   public void onClick(DialogInterface dialogInterface, int i) {


                    }
           //     }).setMessage("apakah yakin mau dihapus? " +);
             //   builder.show();
        //    }
        //});
    //}

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView namamakanan, jumlah, harga, totalharga;
        ImageView btnHapus;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            namamakanan = itemView.findViewById(R.id.namamakanan);
            jumlah = itemView.findViewById(R.id.jumlah);
            harga = itemView.findViewById(R.id.harga);
            totalharga = itemView.findViewById(R.id.total);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
