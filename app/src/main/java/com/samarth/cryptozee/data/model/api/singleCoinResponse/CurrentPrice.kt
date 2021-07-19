package com.samarth.cryptozee.data.model.api.singleCoinResponse

import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("usd")
    val usd:Double
)
