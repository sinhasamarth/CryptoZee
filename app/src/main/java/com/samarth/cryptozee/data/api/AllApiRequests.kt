package com.samarth.cryptozee.data.api

import com.samarth.cryptozee.data.model.MarketCoinResponse
import retrofit2.http.GET

interface AllApiRequests {

    // Getting All the Market Coins
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1&sparkline=false&price_change_percentage=24h")
    suspend fun getAllCoins(): MarketCoinResponse

}