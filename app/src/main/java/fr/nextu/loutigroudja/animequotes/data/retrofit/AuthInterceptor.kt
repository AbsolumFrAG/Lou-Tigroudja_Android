package fr.nextu.loutigroudja.animequotes.data.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", accessToken)
            .build()

        Log.d("AuthInterceptor", "URL: ${newRequest.url()}")
        Log.d("AuthInterceptor", "Headers: ${newRequest.headers()}")
        val response = chain.proceed(newRequest)

        Log.d("AuthInterceptor", "Response Code: ${response.code()}")

        return response
    }
}