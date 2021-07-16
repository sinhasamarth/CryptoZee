package com.samarth.cryptozee.data.repository

import android.util.Log
import com.samarth.cryptozee.data.api.RetrofitInstance
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.ui.base.fragments.home.SingleCoinDetail
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_PREFIX
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_SUFFIX
import com.samarth.cryptozee.utils.CONSTANTS.Companion.URL_API_COINGECKO

object Repository {
    suspend fun getAllCoin(): MarketCoinResponse {
        return RetrofitInstance.responseAllCoin.getAllCoins()
    }

    suspend fun singleCoinDetails(coinName:String) : SingleCoinDetailResponse{
     val z = URL_API_COINGECKO+SINGLE_COIN_URL_DETAIL_PREFIX+coinName.lowercase()+SINGLE_COIN_URL_DETAIL_SUFFIX
        Log.d("TAG", z)
        return RetrofitInstance.responseAllCoin.getSingleCoinDetails(
            SINGLE_COIN_URL_DETAIL_PREFIX+coinName.lowercase()+SINGLE_COIN_URL_DETAIL_SUFFIX)
    }

}