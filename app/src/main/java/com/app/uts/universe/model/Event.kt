package com.app.uts.universe.model

data class Event(
    val id: Int,
    val nama: String,
    val kategori: String,
    val tanggal: String,
    val lokasi: String,
    val deskripsi: String,
    val benefit: String = ""
)