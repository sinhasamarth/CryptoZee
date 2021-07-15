package com.samarth.cryptozee.ui.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samarth.cryptozee.data.model.MarketCoinResponse
import com.samarth.cryptozee.data.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private  val repository: Repository): ViewModel() {

    //LiveData

    val allCoinResponse:MutableLiveData<MarketCoinResponse> = MutableLiveData<MarketCoinResponse> ()

    fun getAllCoin(){
        viewModelScope.launch {
            try{
            allCoinResponse.value = repository.getAllCoin()}
            catch (e:Exception){
                Log.d("MYTAG", e.toString())
            }
        }

    }
}