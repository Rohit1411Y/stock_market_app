package com.example.stockmarketapp.data.remote

import com.example.stockmarketapp.util.secrets
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apiKey") apiKey: String = API_KEY
    ): ResponseBody

    companion object {
        const val API_KEY = secrets.api_key
        const val BASE_URL = "https://www.alphavantage.co/"
    }
}