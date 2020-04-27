package com.example.stokkacamata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ProfilePegawai> profiles;

    public MyAdapter(Context c, ArrayList<ProfilePegawai> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(profiles.get(position).getNama());
        holder.alamat.setText(profiles.get(position).getAlamat());
        holder.notelp.setText(profiles.get(position).getNotelp());
        holder.tempatlahir.setText(profiles.get(position).getTempatlahir());
        holder.tanggallahir.setText(profiles.get(position).getTanggallahir());
        holder.deskripsi.setText(profiles.get(position).getDeskripsi());
        holder.password.setText(profiles.get(position).getPassword());
        //Picasso.get().load(profiles.get(position).getProfilpicturepegawai()).into(holder.profilepicture);
        /*
        if(profiles.get(position).getPermission()) {
            holder.checkDetails.setVisibility(View.VISIBLE);
            holder.onClick(position);
        }
         */
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView nama, alamat, notelp, tempatlahir, tanggallahir,
                deskripsi, password;
        ImageView profilepicture;
        Button checkDetails;
        public MyViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama1);
            alamat = (TextView) itemView.findViewById(R.id.alamat1);
            notelp = (TextView) itemView.findViewById(R.id.notelp1);
            tempatlahir = (TextView) itemView.findViewById(R.id.tempatlahir1);
            tanggallahir = (TextView) itemView.findViewById(R.id.tanggallahir1);
            deskripsi = (TextView) itemView.findViewById(R.id.deskripsi1);
            password = (TextView) itemView.findViewById(R.id.password1);
            profilepicture = (ImageView) itemView.findViewById(R.id.profilepicture1);
            checkDetails = (Button) itemView.findViewById(R.id.checkDetails);
        }
        public void onClick(final int position){
            checkDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, position+" klik ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}