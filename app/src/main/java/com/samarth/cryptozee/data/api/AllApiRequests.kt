package com.samarth.cryptozee.data.api

import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Url

interface AllApiRequests {


    // Getting All the Market Coins
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1&sparkline=false&price_change_percentage=24h")
    suspend fun getAllCoins(): MarketCoinResponse

    // Single Coin Data
    @GET
    suspend fun getSingleCoinDetails(@Url coinName: String ): SingleCoinDetailResponse


    //MarketChart
    @GET
    suspend fun getSingleCoinMarketChart(@Url coinName: String): SingleCoinChartResponse

}