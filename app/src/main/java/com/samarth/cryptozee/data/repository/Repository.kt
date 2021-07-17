package com.samarth.cryptozee.data.repository

import com.samarth.cryptozee.data.api.AllChartApiRequest
import com.samarth.cryptozee.data.api.RetrofitInstance
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_PREFIX
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_SUFFIX

object Repository {
    suspend fun getAllCoin(): MarketCoinResponse {
        return RetrofitInstance.responseAllApi.getAllCoins()
    }

    suspend fun singleCoinDetails(coinId: String): SingleCoinDetailResponse {
        return RetrofitInstance
            .responseAllApi
            .getSingleCoinDetails(
                SINGLE_COIN_URL_DETAIL_PREFIX + coinId.lowercase() + SINGLE_COIN_URL_DETAIL_SUFFIX
            )
    }

    suspend fun singleCoinChart(coinId: String): ArrayList<SingleCoinChartResponse> {
        return AllChartApiRequest.getAllCoinChart(coinId)
    }

}