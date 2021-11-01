<?php

require("koneksi.php");

$perintah = "SELECT * FROM tbl_laundry";
$eksekusi = mysqli_query($konek, $perintah);
$cek = mysqli_affected_rows($konek);

if ($cek > 0) {
    $response["kode"] = 1;                  // respon kode
    $response["pesan"] = "Data Tersedia";   // respon pesan
    $response["data"] = array();            // respon data

    // ambil datanya dari table 
    while ($ambil = mysqli_fetch_object($eksekusi)) {
        $F["id"]        = $ambil->id;
        $F["nama"]      = $ambil->nama;
        $F["alamat"]    = $ambil->alamat;
        $F["telepon"]   = $ambil->telepon;

        // push data
        array_push($response["data"], $F);
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Data Tidak Tersedia";
}

echo json_encode($response);
mysqli_close($konek);
