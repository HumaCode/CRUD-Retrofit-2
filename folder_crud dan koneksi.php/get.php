<?php

require("koneksi.php");

// buat response berbentuk array
$response = array();

// pengkondisian
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // ambil data yng di kirim
    $id       = $_POST["id"];

    // perintah ambil data by id
    $perintah = "SELECT * FROM tbl_laundry WHERE id='$id'";
    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);


    // cek apakah berhasil atau tidak
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data tersedia";
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
        $response["pesan"] = "Data tidak tersedia";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak ada post Data";
}

echo json_encode($response);
mysqli_close($konek);
