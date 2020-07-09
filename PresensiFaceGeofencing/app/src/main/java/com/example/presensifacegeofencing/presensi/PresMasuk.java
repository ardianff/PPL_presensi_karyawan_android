package com.example.presensifacegeofencing.presensi;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.util.Util;
import com.example.presensifacegeofencing.Login;
import com.example.presensifacegeofencing.R;
import com.example.presensifacegeofencing.config.AppControler;
import com.example.presensifacegeofencing.config.Server;
import com.example.presensifacegeofencing.home.Home;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PresMasuk extends AppCompatActivity implements View.OnClickListener {
    String usernamee, password;
    private SharedPreferences prefssatu, prefpassword;
    Toolbar toolbar;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String id;
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_TAKE_PHOTO = 2;
    private int PICK_IMAGE_REQUEST1 = 3;
    private int REQUEST_TAKE_PHOTO1 = 4;
    public Uri mUri;
    public Bitmap bitmap;
    public ImageView fotobukti;
    TextView namaimage, namaimage1;
    Button pilihfoto;
    TextView simpan;
    //get emai hp
    String IMEI_Number_Holder;
    TelephonyManager telephonyManager;

    Time today;
    int cek;
    String ambilusername;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presmasuk);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getContext().setTheme(R.style.AppThemebaru);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        prefssatu = this.getSharedPreferences(
                Login.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        usernamee = (prefssatu.getString(
                Login.KEY_SATU, "NA"));
        prefpassword = this.getSharedPreferences(
                Login.PASSOWRD,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        password = (prefpassword.getString(
                Login.KEY_PASSWORD, "NA"));
        fotobukti = findViewById(R.id.fotobukti);
        pilihfoto = findViewById(R.id.pilihfoto);
        namaimage = findViewById(R.id.labelfoto);
        simpan = findViewById(R.id.simpan);
        simpan.setOnClickListener(this);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        peringatan();

//        jam.setText("Jam : " + today.format("%k:%M:%S"));

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        IMEI_Number_Holder = telephonyManager.getDeviceId();
        System.out.println(IMEI_Number_Holder);
//        pilihfoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showfilechoser();
//            }
//        });
    }

    private void showfilechoser() {
        final CharSequence[] items = {"Ambil Wajah",
                "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PresMasuk.this);
        builder.setTitle("");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil Wajah")) {
                    System.out.println("camera");
                    Intent galleryIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    galleryIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "pic_karyawan" + System.currentTimeMillis() + ".jpg"));
                    startActivityForResult(galleryIntent, REQUEST_TAKE_PHOTO);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
//        Intent galleryIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "Movie_pict" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
//        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    void peringatan ()
    {
        final Dialog dialog1 = new Dialog(PresMasuk.this, R.style.df_dialog);
        dialog1.setContentView(R.layout.peringatan);
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
        }
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
//            String filepath = mUri.getPath();
//            assert filepath != null;
//            String filename = filepath.substring(filepath.lastIndexOf("/") + 1);
//            Bundle extras = data.getExtras();
//            assert extras != null;
//            bitmap = (Bitmap) extras.get("data");
//            fotobukti.setImageBitmap(bitmap);
//            namaimage.setText(filename);
//        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.simpan) {
            Masuk();
        }
    }

    private void Masuk() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(PresMasuk.this, "Loading...", " Mohon Tunggu...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URL + "web_service/masuk.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                berhasil();
                            } else if (success == 3) {
                                sudahabsen();
                            } else {
                                gagal();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();
                        //menampilkan toast
                        Toast.makeText(getApplicationContext(), "Maaf Ada Kesalahan!!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", usernamee.trim());
                params.put("emai", IMEI_Number_Holder);
                params.put("jam", today.format("%k:%M:%S"));
//                params.put("namafoto", namaimage.getText().toString());
//                params.put("foto", getStringImage(bitmap));
                return params;
            }
        };

        AppControler.getInstance().addToRequestQueue(stringRequest, "json");
    }

    public void gagal() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Informasi")
                .setCancelText("Cancel")
                .setContentText("Presensi Gagal, Satu Handphone Hanya Digunakan Satu User / Anda Di Luar Kantor")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    public void berhasil() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Presensi Keluar Berhasil")
                .setContentText("Terima Kasih Telah Melakukan Absensi Karyawan!")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                        Intent i = new Intent(getApplicationContext(), Home.class);
                        startActivity(i);
                    }
                })
                .show();
    }

    public void sudahabsen() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Informasi")
                .setCancelText("Cancel")
                .setContentText("Hari Ini Anda Sudah Presensi... !!!")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();

                    }
                })
                .show();
    }

    public void cekemai() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Informasi")
                .setCancelText("Cancel")
                .setContentText("1 Handphone, hanya digunakan 1 User !!!")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();

                    }
                })
                .show();
    }

    public void belumabsenmasuk() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Informasi")
                .setCancelText("Cancel")
                .setContentText("Maaf, Hari ini anda belum absen Masuk,silahkan absen terlebih dahulu.. !!!")
                .setConfirmText("Oke")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();

                    }
                })
                .show();
    }
}
