package fr.nextu.loutigroudja.animequotes.data.retrofit

import fr.nextu.loutigroudja.animequotes.domain.QuoteRepository

class QuoteRepositoryImpl(private val quoteApiHelper: QuoteApiHelper): QuoteRepository {
    override suspend fun getQuote() = quoteApiHelper.getQuote()
}