package com.example.presensifacegeofencing.historipresensi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

public class Masuk extends AppCompatActivity {
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    List<ModelData> mItems;
    ProgressDialog pd;
    RecyclerView.LayoutManager mManager;
    SharedPreferences iduser;
    String ambiliduser;
    TextView textnotif;
    EditText pencarian;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_histori);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getContext().setTheme(R.style.AppThemebaru);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textnotif=(TextView)findViewById(R.id.kosong) ;


        mRecyclerview = findViewById(R.id.recyclerviewTemp);
        pd = new ProgressDialog(Masuk.this);
        mItems = new ArrayList<>();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterHistori(this, mItems);
        mRecyclerview.setAdapter(mAdapter);
        iduser = getSharedPreferences(
                Login.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ambiliduser = (iduser.getString(
                Login.KEY_SATU, "NA"));
        MengambilData();
        pencarian= findViewById(R.id.pencarian);
        pencarian.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mItems.clear();
                    MengambilDataCari();
                    return true;
                }
                return false;
            }
        });
    }
    private void MengambilData() {
        pd.setMessage("Fetching data...");
        pd.setCancelable(true);
        pd.show();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET,
                Server.URL + "web_service/presensimasuk.php?username=" + ambiliduser
                , null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setTgl(data.getString("tanggal"));
                                md.setJam(data.getString("jam"));
                                md.setTipe(data.getString("tipe"));
                                if (data.getString("message")=="kosong"|| data.getString("message").equals("kosong")){
                                    textnotif.setVisibility(View.VISIBLE);
                                }else {
                                    textnotif.setVisibility(View.GONE);
                                }
                                mItems.add(md);
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
                        Toast.makeText(getApplication(), "Tidak ada histori presensi", Toast.LENGTH_LONG).show();
                    }
                });
        AppControler.getInstance().addToRequestQueue(reqData);
    }
    private void MengambilDataCari() {
        pd.setMessage("Fetching data...");
        pd.setCancelable(true);
        pd.show();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET,
                Server.URL + "web_service/presensimasuk.php?username=" + ambiliduser+"&cari="+pencarian.getText().toString()
                , null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setTgl(data.getString("tanggal"));
                                md.setJam(data.getString("jam"));
                                md.setTipe(data.getString("tipe"));

                                if (data.getString("message")=="kosong"|| data.getString("message").equals("kosong")){
                                    textnotif.setVisibility(View.VISIBLE);
                                }else {
                                    textnotif.setVisibility(View.GONE);
                                }
                                mItems.add(md);
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
                        Toast.makeText(getApplication(), "Histori Presensi yang dicari tidak ada", Toast.LENGTH_LONG).show();
                    }
                });
        AppControler.getInstance().addToRequestQueue(reqData);
    }
}

