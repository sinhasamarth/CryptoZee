package com.samarth.cryptozee.data.model.SingleCoinResponse


import com.google.gson.annotations.SerializedName
import com.samarth.cryptozee.data.model.SingleCoinResponse.Links
import com.samarth.cryptozee.data.model.SingleCoinResponse.MarketData

data class SingleCoinDetailResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("coingecko_rank")
    val rank:Int,
    @SerializedName("links")
    val links: Links,
    @SerializedName("market_data")
    val marketData: MarketData,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)