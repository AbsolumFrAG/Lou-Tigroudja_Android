package fr.nextu.loutigroudja.animequotes.domain

interface QuoteRepository {
    suspend fun getQuote(): Quote
}