package com.samarth.cryptozee.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.samarth.cryptozee.data.api.AllChartApiRequest
import com.samarth.cryptozee.data.api.RetrofitInstance
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.dao.AlertDao
import com.samarth.cryptozee.data.model.localStorage.dao.FavouriteDao
import com.samarth.cryptozee.data.model.localStorage.database.LocalStorageDatabase
import com.samarth.cryptozee.data.model.localStorage.entities.AlertEntity
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_PREFIX
import com.samarth.cryptozee.utils.CONSTANTS.Companion.SINGLE_COIN_URL_DETAIL_SUFFIX

class Repository(private val favouriteDao: FavouriteDao , private val alertDao: AlertDao) {

    val allAlertCoin: LiveData<List<AlertEntity>> = alertDao.getAllAlertCoins().asLiveData()
    suspend fun getAllCoin(): MarketCoinResponse {
        return RetrofitInstance.responseAllApi.getAllCoins()
    }

    suspend fun singleCoinDetails(coinId: String): SingleCoinDetailResponse {
        return RetrofitInstance
            .responseAllApi
            .getSingleCoinDetails(
                SINGLE_COIN_URL_DETAIL_PREFIX + coinId.lowercase() + SINGLE_COIN_URL_DETAIL_SUFFIX,
            )
    }

    suspend fun singleCoinChart(coinId: String): ArrayList<SingleCoinChartResponse> {
        return AllChartApiRequest.getAllCoinChart(coinId)
    }

    // Favourite Database Function

    suspend fun addFavourite(favouriteEntity: FavouriteEntity){
        favouriteDao.addFavourite(favouriteEntity)
    }
    suspend fun delFavourite(favouriteEntity: FavouriteEntity){
        favouriteDao.delFavourite(favouriteEntity)
    }

     fun getAllFavourite(): List<FavouriteEntity> {
        return favouriteDao.getAllFavourite()
    }
    suspend fun addToAlert(alertEntity: AlertEntity){
        alertDao.addToAlert(alertEntity)
    }
    suspend fun delAlert(alertEntity: AlertEntity){
        alertDao.delAlert(alertEntity)
    }
}