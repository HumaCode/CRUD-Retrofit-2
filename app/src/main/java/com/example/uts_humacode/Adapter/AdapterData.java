package com.example.uts_humacode.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_humacode.API.APIRequestData;
import com.example.uts_humacode.API.RetroServer;
import com.example.uts_humacode.Activity.MainActivity;
import com.example.uts_humacode.Activity.Splash;
import com.example.uts_humacode.Activity.UbahActivity;
import com.example.uts_humacode.Model.DataModel;
import com.example.uts_humacode.Model.ResponseModel;
import com.example.uts_humacode.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listData;
    private List<DataModel> listLaundry;
    private int idLaundry;

//    constructor
    public AdapterData(Context ctx, List<DataModel> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        inflate data dari carditem ke recyclerview
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
//        panggil datamodel
        DataModel dm = listData.get(position);
        
        holder.TvId.setText(String.valueOf(dm.getId()));
        holder.TvNama.setText(dm.getNama());
        holder.TvAlamat.setText(dm.getAlamat());
        holder.TvTelepon.setText(dm.getTelepon());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    //    membuat class di dalam class, berfungsi menghendel cardview di dalam recyclerview
    public class HolderData extends RecyclerView.ViewHolder{
        TextView TvId, TvNama, TvAlamat, TvTelepon;

    public HolderData(@NonNull View itemView) {
        super(itemView);
        
        TvId = itemView.findViewById(R.id.tv_id);
        TvNama = itemView.findViewById(R.id.tv_nama);
        TvAlamat = itemView.findViewById(R.id.tv_alamat);
        TvTelepon = itemView.findViewById(R.id.tv_telepon);


//        ketika card di klik dan di tahan beberapa detik maka akan menjalankan popup /dialog
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                buat dialog
                AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                dialogPesan.setMessage("Menu Pilihan");
                dialogPesan.setTitle("Perhatian");
                dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                dialogPesan.setCancelable(true);

                idLaundry = Integer.parseInt(TvId.getText().toString());

//                jika memilih tombol hapus
                dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData();
                        dialogInterface.dismiss();

//                        beri jeda sebentar
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

//                        setelah berhasil dihapus maka arahkan ke mainActivity
                                ((MainActivity) ctx).retrievedData();
                            }
                        }, 1000);
                    }
                });

//                jika memilih tombol ubah
                dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                        panggil method getData
                        getData();

                        dialogInterface.dismiss();
                    }
                });

//                tampilkan dialog
                dialogPesan.show();

                return false;
            }
        });
    }

//    method hapus data
        private void deleteData() {
//        menghubungkan clas interface ke retrofit
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);

            Call<ResponseModel> hapusData = ardData.ardDelete(idLaundry);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();


                    Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


//        method get data by id
        private void getData() {
//        menghubungkan clas interface ke retrofit
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);

            Call<ResponseModel> ambilData = ardData.ardGetData(idLaundry);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listLaundry = response.body().getData();

//                    mengambil data
                    int varIdLaundry = listLaundry.get(0).getId();
                    String varNamaLaundry = listLaundry.get(0).getNama();
                    String varAlamatLaundry = listLaundry.get(0).getAlamat();
                    String varTeleponLaundry = listLaundry.get(0).getTelepon();

//                kirim datanya ke aktiviti ubah
                    Intent kirim = new Intent(ctx, UbahActivity.class);
                    kirim.putExtra("xId", varIdLaundry);
                    kirim.putExtra("xNama", varNamaLaundry);
                    kirim.putExtra("xAlamat", varAlamatLaundry);
                    kirim.putExtra("xTelepon", varTeleponLaundry);
                    ctx.startActivity(kirim);



//                    Toast.makeText(ctx, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
}   
}
