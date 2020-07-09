package com.example.presensifacegeofencing.notif;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.presensifacegeofencing.Login;
import com.example.presensifacegeofencing.R;
import com.example.presensifacegeofencing.config.AppControler;
import com.example.presensifacegeofencing.config.ModelData;
import com.example.presensifacegeofencing.config.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Notif extends AppCompatActivity {
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    List<ModelData> mItems;
    ProgressDialog pd;
    RecyclerView.LayoutManager mManager;
    SharedPreferences iduser;
    String ambiliduser;
    TextView textnotif;
    ImageView gambar;
    String usernamee;
    Toolbar toolbar;
    SharedPreferences prefssatu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getContext().setTheme(R.style.AppThemebaru);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRecyclerview = findViewById(R.id.recyclerviewTemp);
        pd = new ProgressDialog(Notif.this);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterNotif(this, mItems);
        mRecyclerview.setAdapter(mAdapter);
        prefssatu = this.getSharedPreferences(
                Login.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        usernamee = (prefssatu.getString(
                Login.KEY_SATU, "NA"));
        gambar = findViewById(R.id.gambar);
        textnotif= findViewById(R.id.kosong );
        MengambilData();
    }
    private void MengambilData() {
        pd.setMessage("Fetching data...");
        pd.setCancelable(true);
        pd.show();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET,
                Server.URL + "web_service/list_notif.php?username=" + usernamee
                , null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                if (data.getString("message")=="kosong"|| data.getString("message").equals("kosong")){
                                    textnotif.setVisibility(View.VISIBLE);
                                    gambar.setVisibility(View.VISIBLE);
                                }else {
                                    md.setJudul(data.getString("judul"));
                                    md.setIsi(data.getString("isi"));
                                    md.setTgl(data.getString("tanggal"));
                                    md.setStatusbaca(data.getString("status"));
                                    textnotif.setVisibility(View.GONE);
                                    gambar.setVisibility(View.GONE);
                                    mItems.add(md);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        pd.cancel();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(getApplication(), "Tidak ada notifikasi", Toast.LENGTH_LONG).show();
                    }
                });
        AppControler.getInstance().addToRequestQueue(reqData);
    }

}
