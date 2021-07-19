package com.samarth.cryptozee.data.model.api.singleCoinResponse


import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    val current_price:CurrentPrice,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,
    @SerializedName("max_supply")
    val maxSupply: Double,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
    @SerializedName("price_change_percentage_14d")
    val priceChangePercentage14d: Double,
    @SerializedName("price_change_percentage_1y")
    val priceChangePercentage1y: Double,
    @SerializedName("price_change_percentage_200d")
    val priceChangePercentage200d: Double,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("price_change_percentage_30d")
    val priceChangePercentage30d: Double,
    @SerializedName("price_change_percentage_60d")
    val priceChangePercentage60d: Double,
    @SerializedName("price_change_percentage_7d")
    val priceChangePercentage7d: Double,
    @SerializedName("total_supply")
    val totalSupply: Double
)