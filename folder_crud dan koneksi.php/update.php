<?php

require("koneksi.php");

// buat response berbentuk array
$response = array();

// pengkondisian
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // ambil data yng di kirim
    $id       = $_POST["id"];
    $nama     = $_POST["nama"];
    $alamat   = $_POST["alamat"];
    $telepon  = $_POST["telepon"];

    // perintah ambil data by id
    $perintah = "UPDATE tbl_laundry 
        SET nama='$nama', 
        alamat='$alamat', 
        telepon='$telepon' 
    WHERE id='$id'";

    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);


    // cek apakah berhasil atau tidak
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data berhasil diubah";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data gagal diubah";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak ada post Data";
}

echo json_encode($response);
mysqli_close($konek);
