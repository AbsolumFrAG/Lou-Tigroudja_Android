package fr.nextu.loutigroudja.animequotes.domain

data class Quote(
    val _id: Int,
    val quote: String,
    val author: String,
    val from: String
)