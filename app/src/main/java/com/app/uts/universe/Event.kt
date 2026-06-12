package com.app.uts.universe

data class Event(
    val id: Int,
    val nama: String,
    val kategori: String,
    val tanggal: String,
    val lokasi: String,
    val deskripsi: String,
)