package com.samarth.cryptozee.ui.base.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import com.samarth.cryptozee.ui.base.viewmodel.MainViewModel
import com.samarth.cryptozee.ui.dataFormatter.SetSingleCoinData

private  var binding: SingleCoinDetailFragmentBinding ? = null

class SingleCoinDetail : Fragment() {
    val viewModelShared:MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        refreshit()
        return binding!!.root
    }

    private fun refreshit(){
        val viewModel = MainViewModel()
        val coinId = viewModelShared.marketCoinResponse?.id.toString()
        var coinDetailResponse:SingleCoinDetailResponse ?=null
        viewModel.getSingleCoinDetail(coinId)
        viewModel.singleCoinResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData.setAllTextDataToView(response, binding!! , viewModelShared.marketCoinResponse!!)
            coinDetailResponse  = response
        })
        viewModel.getCoinChart(coinId)
        viewModel.singleCoinChartResponse.observe(viewLifecycleOwner , {response->
           SetSingleCoinData.setAllChartsToView( response, binding!!,coinDetailResponse)
        })
    }

}