package fr.nextu.loutigroudja.animequotes.data.retrofit

import fr.nextu.loutigroudja.animequotes.domain.Quote
import retrofit2.http.GET

interface QuoteApiService {
    @GET("api/v4/quote")
    suspend fun getQuote(): Quote
}