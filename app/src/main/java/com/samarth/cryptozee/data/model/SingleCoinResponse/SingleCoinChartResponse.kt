package com.samarth.cryptozee.data.model.SingleCoinResponse


import com.google.gson.annotations.SerializedName

data class SingleCoinChartResponse(
    @SerializedName("prices")
    val prices: List<List<Double>>
)