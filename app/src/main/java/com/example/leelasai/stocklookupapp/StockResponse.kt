package com.example.leelasai.stocklookupapp

data class StockResponse(
    val companyName: String,
    val currentPrice: Double,
    val percentageChange: Double
)