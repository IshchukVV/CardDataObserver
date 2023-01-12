package com.example.cardDataObserverTestApplication.network

import com.example.cardDataObserverTestApplication.model.CardData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("Accept-Version: 3")
    @GET("{cardNumber}")
    suspend fun getCardData(@Path("cardNumber") query: String): CardData

    companion object {
        private const val baseUrl = "https://lookup.binlist.net/"
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}