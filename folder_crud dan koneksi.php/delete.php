<?php

require("koneksi.php");

// buat response berbentuk array
$response = array();

// pengkondisian
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // ambil data yng di kirim
    $id       = $_POST["id"];

    // perintah hapus
    $perintah = "DELETE FROM tbl_laundry WHERE id='$id'";
    $eksekusi = mysqli_query($konek, $perintah);
    $cek = mysqli_affected_rows($konek);


    // cek apakah berhasil atau tidak
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "Data berhasil dihapus";
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "Data gagal dihapus";
    }
} else {
    $response["kode"] = 0;
    $response["pesan"] = "Tidak ada post Data";
}

echo json_encode($response);
mysqli_close($konek);
