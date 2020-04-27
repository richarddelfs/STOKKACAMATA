//package com.example.stokkacamata;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import java.util.ArrayList;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class AdapterClassBarang extends RecyclerView.Adapter<AdapterClassBarang.MyViewHolder> {
//
//    ArrayList<ProfileBarang> list;
//
//    public AdapterClassBarang(ArrayList<ProfileBarang> list)
//    {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder, viewGroup, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        myViewHolder.namabarang.setText(list.get(i).getNama());
//        myViewHolder.merk.setText(list.get(i).getMerk());
//        myViewHolder.tipe.setText(list.get(i).getTipe());
//        myViewHolder.warna.setText(list.get(i).getWarna());
//        myViewHolder.jumlah.setText(list.get(i).getJumlah());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView namabarang, merk, tipe, warna, jumlah;
//        public MyViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//            namabarang = itemView.findViewById(R.id.namabarang1);
//            merk = itemView.findViewById(R.id.merk4);
//            tipe = itemView.findViewById(R.id.tipe3);
//            warna = itemView.findViewById(R.id.warna3);
//            jumlah = itemView.findViewById(R.id.jumlah4);
//        }
//    }
//}
package com.example.stokkacamata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class AdapterClassBarang extends RecyclerView.Adapter<AdapterClassBarang.MyViewHolder> {

    ArrayList<ProfileBarang> list = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.onBindContent(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namabarang, merk, tipe, warna, jumlah;
        ImageView barangImageView, qrCodeImageView;
        public MyViewHolder (View view){
            super(view);
            barangImageView = itemView.findViewById(R.id.profilebarang1);
            qrCodeImageView = itemView.findViewById(R.id.qrcode);
            namabarang = itemView.findViewById(R.id.namabarang1);
            merk = itemView.findViewById(R.id.merk4);
            tipe = itemView.findViewById(R.id.tipe3);
            warna = itemView.findViewById(R.id.warna3);
            jumlah = itemView.findViewById(R.id.jumlah4);
        }

        void onBindContent(ProfileBarang profileBarang){
            Picasso.get().load(profileBarang.getProfilepicturebarang()).into(barangImageView);
            Picasso.get().load(profileBarang.getQrcodeurl()).into(qrCodeImageView);
            namabarang.setText(profileBarang.getNama());
            merk.setText(profileBarang.getMerk());
            tipe.setText(profileBarang.getTipe());
            warna.setText(profileBarang.getWarna());
            jumlah.setText(profileBarang.getJumlah());
        }
    }
}