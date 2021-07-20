package com.samarth.cryptozee.data.api

import com.samarth.cryptozee.utils.CONSTANTS.Companion.URL_API_COINGECKO
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request


object RetrofitInstance {
    private val retrofit by lazy{
        //Building Retrofit Instance
        Retrofit.Builder()
            .baseUrl(URL_API_COINGECKO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val responseAllApi:AllApiRequests by lazy {
        retrofit.create(AllApiRequests::class.java)
    }
}