<?php

require("koneksi.php");

// buat response berbentuk array
$response = array();

// pengkondisian
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // ambil data yng di kirim
    $nama       = $_POST["nama"];
    $alamat     = $_POST["alamat"];
    $telepon    = $_POST["telepon"];

    // masukan ke tabel
    $perintah = "INSERT INTO tbl_laundry (nama, alamat, telepon) VALUES ('$nama', '$alamat', '$telepon')";
    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);


    // cek apakah berhasil atau tidak
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Simpan data berhasil";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Gagal menyimpan data";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak ada post Data";
}

echo json_encode($response);
mysqli_close($konek);
