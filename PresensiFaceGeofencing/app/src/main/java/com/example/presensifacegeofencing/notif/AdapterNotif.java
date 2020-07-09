package com.example.presensifacegeofencing.notif;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.presensifacegeofencing.R;
import com.example.presensifacegeofencing.config.ModelData;

import java.util.List;

public class AdapterNotif extends RecyclerView.Adapter<AdapterNotif.HolderData> {
    private List<ModelData> mItems;
    private Context context;
    SharedPreferences iduser;
    String ambiliduser;
    String idstok;
    public AdapterNotif(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }
    @NonNull
    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notif, parent, false);
        return new HolderData(layout);
    }
    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelData md = mItems.get(position);
        holder.judul.setText(md.getJudul());
        holder.isi.setText(md.getIsi());
        holder.tanggal.setText(md.getTgl());
        if (md.getStatusbaca()=="N"||md.getStatusbaca().equals("N")||md.getStatusbaca()==""||md.getStatusbaca().equals("")){
            holder.background.setBackgroundResource(R.color.bacabelum);
        }else{
            holder.background.setBackgroundResource(R.color.sudahbaca);
        }
        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    class HolderData extends RecyclerView.ViewHolder {
        TextView judul,isi,tanggal;
        ModelData md;
        RelativeLayout background;
        public HolderData(View view) {
            super(view);
            judul = view.findViewById(R.id.judul);
            isi = view.findViewById(R.id.isi);
            tanggal = view.findViewById(R.id.tgl);
            background= view.findViewById(R.id.background);
        }

    }


}