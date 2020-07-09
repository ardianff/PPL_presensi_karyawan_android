package com.example.presensifacegeofencing.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.presensifacegeofencing.About;
import com.example.presensifacegeofencing.Login;
import com.example.presensifacegeofencing.R;
import com.example.presensifacegeofencing.config.AppControler;
import com.example.presensifacegeofencing.config.InternetDialog;
import com.example.presensifacegeofencing.config.PrefManager;
import com.example.presensifacegeofencing.config.Server;
import com.example.presensifacegeofencing.historipresensi.HistoriPresensi;
import com.example.presensifacegeofencing.historipresensi.Keluar;
import com.example.presensifacegeofencing.historipresensi.Masuk;
import com.example.presensifacegeofencing.notif.Notif;
import com.example.presensifacegeofencing.presensi.Presensi;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.prefs.PreferencesManager;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import de.hdodenhof.circleimageview.CircleImageView;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;
import ss.com.bannerslider.views.indicators.IndicatorShape;

import static co.mobiwise.materialintro.shape.Focus.MINIMUM;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialIntroListener {
    ImageView menubar;
    CircleImageView foto;
    TextView nama,alamat,tgl,no_hp,prodi, semester,tempat_lahir;
    String ambilusername, ambilpwd;
    private SharedPreferences prefssatu, prefpassword;
    private String mTitle = "Home";
    private DrawerLayout drawer;
    Spinner spinner;
    SeekBar intervalSeekBar;
    private BannerSlider bannerSlider;
    SeekBar indicatorSizeSeekBar;
    SwitchCompat loopSlidesSwitch, mustAnimateIndicators;
    SwitchCompat hideIndicatorsSwitch;
    ProgressBar p1, p2, p3, p4;
    LinearLayout tentang,histori,presensi,wa;
    ImageView notifikasi;
    boolean doubleBackToExitPressedOnce = false;
    Animation animFadein;
    ImageView up;
    EditText ekritik, esaran;
    Animation animation;
    String kritik, saran;
    String urlslide;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    Dialog dialogkritik;

    private static final String INTRO_FOCUS_1 = "intro_focus_1";
    private static final String INTRO_FOCUS_2 = "intro_focus_2";
    private static final String INTRO_FOCUS_3 = "intro_focus_3";
    private static final String INTRO_FOCUS_4 = "intro_focus_4";

    LinearLayout head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        if (new InternetDialog(this).getInternetStatus()) {
//            Toast.makeText(this, "INTERNET VALIDATION PASSED", Toast.LENGTH_SHORT).show();
        }
        up = findViewById(R.id.up);
        menubar = findViewById(R.id.menubar);
        nama = findViewById(R.id.nama);
        tempat_lahir = findViewById(R.id.tempat_lahir);
        tgl = findViewById(R.id.tanggal_lahir);
        no_hp=findViewById(R.id.no_hp);
        alamat = findViewById(R.id.alamat);
        head = findViewById(R.id.head);
        foto = findViewById(R.id.foto);
        presensi = findViewById(R.id.presensi);
        histori = findViewById(R.id.histori);
        tentang = findViewById(R.id.tentang);
        wa = findViewById(R.id.wa);
        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        prefssatu = this.getSharedPreferences(
                Login.SATU,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ambilusername = (prefssatu.getString(
                Login.KEY_SATU, "NA"));
        prefpassword = this.getSharedPreferences(
                Login.PASSOWRD,
                Context.MODE_PRIVATE +
                        Context.MODE_PRIVATE | Context.MODE_PRIVATE);
        ambilpwd = (prefpassword.getString(
                Login.KEY_PASSWORD, "NA"));


        p1 = findViewById(R.id.p1);
//        p2 = (ProgressBar) findViewById(R.id.p2);
        bannerSlider = findViewById(R.id.banner_slider1);
        intervalSeekBar = findViewById(R.id.seekbar_interval);
        spinner = findViewById(R.id.spinner_page_indicator);
        indicatorSizeSeekBar = findViewById(R.id.seekbar_indicator_size);
        indicatorSizeSeekBar.setMax(getResources().getDimensionPixelSize(R.dimen.max_slider_indicator_size));
        loopSlidesSwitch = findViewById(R.id.checkbox_loop_slides);
        mustAnimateIndicators = findViewById(R.id.checkbox_animate_indicators);
        hideIndicatorsSwitch = findViewById(R.id.checkbox_hide_indicators);
        notifikasi = findViewById(R.id.notifikasi);
        setupViews();
        Slider();
        CekUser();

        presensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Presensi.class);
                startActivity(intent);
            }
        });

        histori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HistoriPresensi.class);
                startActivity(intent);
            }
        });

        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
            }
        });
        notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Notif.class);
                startActivity(intent);
            }
        });

        showIntro(menubar, INTRO_FOCUS_1, "Gunakan menu sidebar ini, untuk mengakses modul-modul yang lain", Focus.NORMAL);
    }
    public void showIntro(View view, String id, String text, Focus focusType) {
        new MaterialIntroView.Builder(this)
                .setTextColor(R.color.colorText)
                .dismissOnTouch(true)
                .enableDotAnimation(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(focusType)
                .setDelayMillis(200)
                .enableFadeAnimation(true)
                .setListener(this)
                .performClick(true)
                .setInfoText(text)
                .setTarget(view)
                .setUsageId(id) //THIS SHOULD BE UNIQUE ID
                .show();
    }
    @Override
    public void onUserClicked(String materialIntroViewId) {
        switch (materialIntroViewId) {
            case INTRO_FOCUS_1:
                showIntro(notifikasi, INTRO_FOCUS_2, "Tekan tombol lonceng untuk melihat notifikasi ", MINIMUM);
                break;
            case INTRO_FOCUS_2:
                showIntro(wa, INTRO_FOCUS_3, "Kantor", Focus.NORMAL);
                break;
            case INTRO_FOCUS_3:
                showIntro(head, INTRO_FOCUS_4, "Profil User", Focus.ALL);
                break;
        }
    }
    private void setupViews() {
//        setupToolbar();
        setupBannerSlider();
        setupPageIndicatorChooser();
        setupSettingsUi();
//        addBanners();
    }
    private void setupSettingsUi() {
        intervalSeekBar.setMax(500);
        intervalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    bannerSlider.setInterval(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        indicatorSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    bannerSlider.setIndicatorSize(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        loopSlidesSwitch.setChecked(true);
        mustAnimateIndicators.setChecked(true);

        loopSlidesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bannerSlider.setLoopSlides(b);
            }
        });

        mustAnimateIndicators.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bannerSlider.setMustAnimateIndicators(b);
            }
        });


        hideIndicatorsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bannerSlider.setHideIndicators(b);
            }
        });
    }
    private void setupBannerSlider() {
        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlslide)));
//                Toast.makeText(Home.this, "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    private void addBanners(){
//        //Add banners using image urls
//        bannerSlider.addBanner(new RemoteBanner(
//                "https://i.imgsafe.org/10/10d81cd7b7.jpeg"
//        ));
//        bannerSlider.addBanner(new RemoteBanner(
//                "https://i.imgsafe.org/10/10d81bd13d.jpeg"
//        ));
////        bannerSlider.addBanner(new RemoteBanner(
////                "https://i.imgsafe.org/dc/dc74ea56f1.gif"
////        ));
////        bannerSlider.addBanner(new RemoteBanner(
////                "https://i.imgsafe.org/dc/dc74e8900d.jpeg"
////        ));
//
//    }


    private void Slider() {
//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();
        p1.setVisibility(View.VISIBLE);

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, Server.URL + "web_service/slider.php", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        pd.cancel();
                        p1.setVisibility(View.GONE);
                        System.out.println(response);
                        if (response == null || response.equals("[]") || response.equals(null) || response.equals("")
                                || response.toString().equals("") || response.toString().equals("[]")) {
                        } else {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject data = response.getJSONObject(i);
//                                    bannerSlider.addBanner(new RemoteBanner(Server.URL + "assets/slider/" + data.getString("Slider")
//                                    ));
//                                    System.out.println(data.getString("image"));
                                    bannerSlider.addBanner(new RemoteBanner(Server.URL +"assets/slider/"+data.getString("image")
                                    ));
//                                    urlslide = data.getString("url");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        pd.cancel();
                        p1.setVisibility(View.GONE);
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });
        AppControler.getInstance().addToRequestQueue(reqData);
    }
    private void setupPageIndicatorChooser() {
        String[] pageIndicatorsLabels = getResources().getStringArray(R.array.page_indicators);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                pageIndicatorsLabels
        );
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        bannerSlider.setDefaultIndicator(IndicatorShape.CIRCLE);
                        break;
                    case 1:
                        bannerSlider.setDefaultIndicator(IndicatorShape.DASH);
                        break;
                    case 2:
                        bannerSlider.setDefaultIndicator(IndicatorShape.ROUND_SQUARE);
                        break;
                    case 3:
                        bannerSlider.setDefaultIndicator(IndicatorShape.SQUARE);
                        break;
                    case 4:
                        bannerSlider.setCustomIndicator(VectorDrawableCompat.create(getResources(),
                                R.drawable.selected_slide_indicator, null),
                                VectorDrawableCompat.create(getResources(),
                                        R.drawable.unselected_slide_indicator, null));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logOut) {
            //                keluar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
           Intent intent = new Intent(getApplicationContext(), Home.class);
           startActivity(intent);
        }
        if (id == R.id.keluar) {
            Intent intent = new Intent(getApplicationContext(), Keluar.class);
            startActivity(intent);
        }
        if (id == R.id.masuk) {

            Intent intent = new Intent(getApplicationContext(), Masuk.class);
            startActivity(intent);
        }

        if (id == R.id.petunjuk) {
            new PreferencesManager(getApplicationContext()).resetAll();
            finish();
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }
        if (id == R.id.nav_keluar) {
            keluar();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void keluar() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Anda yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PrefManager prefManager = new PrefManager(getApplicationContext());

                        // make first time launch TRUE
                        prefManager.setFirstTimeLaunch(true);

                        startActivity(new Intent(Home.this, Login.class));
                        finish();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    void Maintenance() {
        final Dialog dialog1 = new Dialog(Home.this, R.style.df_dialog);
        dialog1.setContentView(R.layout.under);
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                finish();
            }
        });
        dialog1.show();
    }
    void Berhasil() {
        final Dialog dialog1 = new Dialog(Home.this, R.style.df_dialog);
        dialog1.setContentView(R.layout.berhasil);
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
    void Down() {
        final Dialog dialog1 = new Dialog(Home.this, R.style.df_dialog);
        dialog1.setContentView(R.layout.down);
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                finish();
            }
        });
        dialog1.show();
    }
    private void CekUser() {
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, Server.URL + "web_service/detailuser.php?username=" + ambilusername, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                nama.setText(data.getString("nama"));
                                tempat_lahir.setText(data.getString("tanggallahir"));
                                alamat.setText(data.getString("alamat"));
                                no_hp.setText(data.getString("phone"));
                                if (data.getString("foto").equals("null")
                                        || data.getString("nama").equals("null")
                                        || data.getString("tanggallahir").equals("null")
                                        || data.getString("alamat").equals("null")
                                        || data.getString("phone").equals("null")) {
                                    foto.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                                } else {
                                    Picasso.with(getApplicationContext())
                                            .load(Server.URL+data.getString("foto"))
                                            .resize(200,250)
                                            .into(foto);
                                }
                                if (data.getString("under").equals("Y") || data.getString("under").equals("Y")) {
                                    Maintenance();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Down();
//                        pd.cancel();
//                        p1.setVisibility(View.GONE);
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });

        AppControler.getInstance().addToRequestQueue(reqData);


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan tombol kembali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
