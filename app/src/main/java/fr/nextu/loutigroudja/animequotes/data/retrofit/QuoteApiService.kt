package fr.nextu.loutigroudja.animequotes.data.retrofit

import fr.nextu.loutigroudja.animequotes.domain.Quote
import retrofit2.http.GET

interface QuoteApiService {
    @GET("api/random")
    suspend fun getQuote(): Quote
}