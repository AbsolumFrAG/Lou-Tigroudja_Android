package fr.nextu.loutigroudja.animequotes.data.retrofit

class QuoteApiHelper(private val quoteApiService: QuoteApiService) {
    suspend fun getQuote() = quoteApiService.getQuote()
}