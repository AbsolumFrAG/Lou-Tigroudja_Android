package fr.nextu.loutigroudja.animequotes.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://waifu.it/"

    private const val accessToken = "ODExMzY5MDQ0Mzc2MDI3MTM2.MTcxNjY4NjUzOA--.771c71148eca"

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(accessToken))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val quoteApiService: QuoteApiService = retrofit.create(QuoteApiService::class.java)
}