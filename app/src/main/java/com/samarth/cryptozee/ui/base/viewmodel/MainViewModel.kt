package com.samarth.cryptozee.ui.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponse
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponseItem
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.ui.base.fragments.home.SingleCoinDetail
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    //LiveData

    val allCoinResponse: MutableLiveData<MarketCoinResponse> = MutableLiveData<MarketCoinResponse>()
    val singleCoinResponse: MutableLiveData<SingleCoinDetailResponse> = MutableLiveData<SingleCoinDetailResponse>()
    var marketCoinResponse : MarketCoinResponseItem ? = null
    fun getAllCoin() {
        viewModelScope.launch {
            try {
                allCoinResponse.value = Repository.getAllCoin()
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }
    }

    fun getSingleCoinDetail(coinName: String) {
        viewModelScope.launch {
            try {
                singleCoinResponse.value = Repository.singleCoinDetails(coinName)
            } catch (e: Exception) {
                Log.d("MYTAG", e.toString())
            }
        }

    }
}
