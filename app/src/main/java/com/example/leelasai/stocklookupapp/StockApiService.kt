package com.example.leelasai.stocklookupapp  // Ensure the package is correct

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApiService {
    @GET("v6/finance/quote")  // Update this endpoint based on the API documentation
    fun getStockData(
        @Query("symbols") symbol: String  // Use "symbols" as per the Yahoo Finance API requirements
    ): Call<StockResponse>
}
