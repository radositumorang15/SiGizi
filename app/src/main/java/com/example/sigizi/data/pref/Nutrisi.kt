package com.example.sigizi.data.pref

data class Nutrisi(
    val umurBayi: String,
    val jenisNutrisi: String,
    val jenisMakanan: String
)

data class DetailNutrisi(
    val umur: String,
    val nutrisi: String,
    val makanan: String
)