package com.samarth.cryptozee.data.viewmodel

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.api.marketListCoinResponse.MarketCoinResponseItem
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.entities.AlertEntity
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.service.ForegroundService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    //Calling On Object Create
    init {
        getAllFavouriteCoin()

    }
    //LiveData


    val allCoinResponse: MutableLiveData<MarketCoinResponse> = MutableLiveData<MarketCoinResponse>()
    val singleCoinResponse: MutableLiveData<SingleCoinDetailResponse> = MutableLiveData<SingleCoinDetailResponse>()
    val singleCoinChartResponse: MutableLiveData<ArrayList<SingleCoinChartResponse>> = MutableLiveData<ArrayList<SingleCoinChartResponse>>()
    val allFavouriteCoin: MutableLiveData<List<FavouriteEntity>> = MutableLiveData<List<FavouriteEntity>>()
    val allAlertCoin:LiveData<List<AlertEntity>> = repository.allAlertCoin

    //Sharing Data Between Fragments
    var coinIDForSharing:String ?=null
    var coinForSharingChange: String ?= null
    var coinForSharingImage: String ?= null

    fun getAllCoin() {
        viewModelScope.launch {
            try {
                allCoinResponse.value = repository.getAllCoin()
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }
    }

    fun getSingleCoinDetail(coinId: String) {
        viewModelScope.launch {
            try {
                singleCoinResponse.value = repository.singleCoinDetails(coinId)
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }

    }

    fun getCoinChart(coinId: String) {
        viewModelScope.launch {
            try {
                singleCoinChartResponse.value = repository.singleCoinChart(coinId)
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }
    }

    fun addToFavourites(favouriteEntity: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavourite(favouriteEntity)
        }
    }

    fun getAllFavouriteCoin() {
        viewModelScope.launch(Dispatchers.IO) {
            allFavouriteCoin.postValue(repository.getAllFavourite())
        }
    }
    fun removeCoinFromFavourite( entity: FavouriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delFavourite(entity)
        }
    }

    //Alert

    fun addToAlert(alertEntity: AlertEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToAlert(alertEntity)
        }
    }
    fun delAlert(alertEntity: AlertEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delAlert(alertEntity)
        }
    }
}
