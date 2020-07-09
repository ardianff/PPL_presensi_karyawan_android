package com.example.presensifacegeofencing.historipresensi;

import android.content.Context;
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

public class AdapterHistori extends RecyclerView.Adapter<AdapterHistori.HolderData> {
    private List<ModelData> mItems;
    private Context context;

    public AdapterHistori(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterHistori.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_histori, parent, false);
        return new HolderData(layout);
    }
    @Override
    public void onBindViewHolder(AdapterHistori.HolderData holder, int position) {
        ModelData md = mItems.get(position);
        holder.tgl.setText(md.getTgl());
        holder.jam.setText(md.getJam());
        holder.tipe.setText(md.getTipe());
        holder.md = md;
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    class HolderData extends RecyclerView.ViewHolder {
        TextView tgl,jam,tipe;
        ModelData md;
        RelativeLayout background;
        public HolderData(View view) {
            super(view);
            tgl = view.findViewById(R.id.tgl);
            jam = view.findViewById(R.id.jam);
            tipe = view.findViewById(R.id.tipe);
            background= view.findViewById(R.id.background);
        }
    }
}