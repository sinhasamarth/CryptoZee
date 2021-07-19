package com.samarth.cryptozee.data.model.api.singleCoinResponse


import com.google.gson.annotations.SerializedName

data class SingleCoinChartResponse(
    @SerializedName("prices")
    val prices: List<List<Double>>
)