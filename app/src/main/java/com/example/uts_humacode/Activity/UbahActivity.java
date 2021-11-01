package com.example.uts_humacode.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UbahActivity extends AppCompatActivity {

    private int xId;
    private String xNama, xAlamat, xTelepon;
    private EditText etNama, etAlamat, etTelepon;
    private Button btnUbah;
    private String yNama, yAlamat, yTelepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);


//        terima variabel yang dikirimkan dari adapter
        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xNama = terima.getStringExtra("xNama");
        xAlamat = terima.getStringExtra("xAlamat");
        xTelepon = terima.getStringExtra("xTelepon");

//        inisialisasi id dari form inputan
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etTelepon = findViewById(R.id.et_telepon);
        btnUbah = findViewById(R.id.btn_ubah);

//        tampilkan data ke dalam input form
        etNama.setText(xNama);
        etAlamat.setText(xAlamat);
        etTelepon.setText(xTelepon);


//        ketika tombol ubah di tekan, maka akan jalanjan proses ubah data
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ambil data yang sudah diubah
                yNama = etNama.getText().toString();
                yAlamat = etAlamat.getText().toString();
                yTelepon = etTelepon.getText().toString();


//                function ubah data
                updateData();
            }
        });

    }


//    method ubah data
    private void updateData() {
//        menghubungkan clas interface ke retrofit
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);

        Call<ResponseModel> updateData = ardData.ardUpdateData(xId, yNama, yAlamat, yTelepon);

        updateData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                buat variabel responya
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : "+kode+" | Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                jika tambah data gagal
                Toast.makeText(UbahActivity.this, "Gagal menghubungi server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}