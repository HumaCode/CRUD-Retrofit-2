package com.example.uts_humacode.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uts_humacode.API.APIRequestData;
import com.example.uts_humacode.API.RetroServer;
import com.example.uts_humacode.Model.ResponseModel;
import com.example.uts_humacode.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {

    private EditText etNama, etAlamat, etTelepon;
    private Button btnSimpan;
    private String nama, alamat, telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

//        inisialisasi
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etTelepon = findViewById(R.id.et_telepon);
        btnSimpan = findViewById(R.id.btn_simpan);

//        ketika tombol di klik
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                tampung inputan yang di input oleh user
                nama = etNama.getText().toString();
                alamat = etAlamat.getText().toString();
                telepon = etTelepon.getText().toString();

//                buat pengkondisian tidak boleh kosong
                if(nama.trim().equals("")) {
                    etNama.setError("Nama harus diisi");
                }else if(alamat.trim().equals("")) {
                    etAlamat.setError("Alamat harus diisi");
                }else if(telepon.trim().equals("")) {
                    etTelepon.setError("Telepon harus diisi");
                }else{
//                    tambah data
                    createData();
                }

            }
        });
    }

//    method tambah data
    private void createData() {
//        menghubungkan clas interface ke retrofit
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);

        Call<ResponseModel> simpanData = ardData.ardCreateData(nama, alamat, telepon);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                buat variabel responya
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : "+kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                jika tambah data gagal
                Toast.makeText(TambahActivity.this, "Gagal menghubungi server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}