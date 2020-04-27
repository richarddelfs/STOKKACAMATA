package com.example.stokkacamata;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.ViewHolder> {

    ArrayList<ProfilePegawai> employList = new ArrayList<>();
    HalamanPegawai delegate = new HalamanPegawai();

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pegawai_info, viewGroup, false);
//        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.onBindContent(employList.get(i));
    }

    @Override
    public int getItemCount() {
        return employList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameLabel, addressLabel, phoneLabel, pobLabel, dobLabel, descLabel, passwordLabel;
        ImageView employImageView;

        ViewHolder(View view){
            super(view);
            employImageView = itemView.findViewById(R.id.iv_employ);
            nameLabel = itemView.findViewById(R.id.tv_employ_name);
            addressLabel = itemView.findViewById(R.id.tv_employ_address);
            phoneLabel = itemView.findViewById(R.id.tv_employ_phone);
            pobLabel = itemView.findViewById(R.id.tv_employ_pob);
            dobLabel = itemView.findViewById(R.id.tv_employ_dob);
            descLabel = itemView.findViewById(R.id.tv_employ_desc);
            passwordLabel = itemView.findViewById(R.id.et_employ_password);
        }

        void onBindContent(final ProfilePegawai profilePegawai){

            Picasso.get().load(profilePegawai.getProfilepicturepegawai()).into(employImageView);
            nameLabel.setText(profilePegawai.getNama());
            addressLabel.setText(profilePegawai.getAlamat());
            phoneLabel.setText(profilePegawai.getNotelp());
            pobLabel.setText(profilePegawai.getTempatlahir());
            dobLabel.setText(profilePegawai.getTanggallahir());
            descLabel.setText(profilePegawai.getDeskripsi());
            passwordLabel.setText(profilePegawai.getPassword());

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent updateDelete = new Intent(delegate, EditHapusPegawai.class);
                    updateDelete.putExtra("nama", profilePegawai.getNama());
                    updateDelete.putExtra("alamat", profilePegawai.getAlamat());
                    updateDelete.putExtra("notelp", profilePegawai.getNotelp());
                    updateDelete.putExtra("tempatlahir", profilePegawai.getTempatlahir());
                    updateDelete.putExtra("tanggallahir", profilePegawai.getTanggallahir());
                    updateDelete.putExtra("deskripsi", profilePegawai.getDeskripsi());
                    updateDelete.putExtra("password", profilePegawai.getPassword());
                    delegate.startActivity(updateDelete);
                }
            });
        }
    }
}
