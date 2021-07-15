package com.samarth.cryptozee.data.repository

import com.samarth.cryptozee.data.api.RetrofitInstance
import com.samarth.cryptozee.data.model.MarketCoinResponse

class Repository {
    suspend fun getAllCoin(): MarketCoinResponse {
        return RetrofitInstance.responseAllCoin.getAllCoins()
    }

}