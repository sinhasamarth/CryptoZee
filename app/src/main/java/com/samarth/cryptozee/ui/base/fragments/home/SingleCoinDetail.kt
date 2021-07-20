package com.samarth.cryptozee.ui.base.fragments.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.data.model.localStorage.entities.AlertEntity
import com.samarth.cryptozee.data.model.localStorage.entities.FavouriteEntity
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import com.samarth.cryptozee.ui.dataFormatter.SetSingleCoinData
import com.samarth.cryptozee.viewModelShared
import com.samarth.cryptozee.MainActivity


private lateinit var binding: SingleCoinDetailFragmentBinding

class SingleCoinDetail : Fragment() {


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainActivity.startLoading()
        binding = SingleCoinDetailFragmentBinding.inflate(layoutInflater)
        binding.chart.clearCache(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val coinId = viewModelShared.coinIDForSharing!!
        Log.d("NAME", coinId)
        viewModelShared.getSingleCoinDetail(coinId)
        viewModelShared.singleCoinResponse.observe(viewLifecycleOwner, { response ->
            if (response.id == coinId) {
                Log.d("Response", response.toString())
                SetSingleCoinData.setAllTextDataToView(response, binding)
            }

        })

        viewModelShared.getCoinChart(coinId)
        viewModelShared.singleCoinChartResponse.observe(viewLifecycleOwner, { response ->
            SetSingleCoinData.setAllChartsToView(response, binding)
            if (!response.isNullOrEmpty()) {
                Log.d("RESPONSE", response.toString())
            }
            MainActivity.stopLoading()
            binding.loadingAnimationChart.visibility = View.VISIBLE
        })

        viewModelShared.allFavouriteCoin.value?.forEach {
            if (it.coinId == coinId) {
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.favtoggleButton.tag = "ON"
            }
        }
        viewModelShared.allAlertCoin.value?.forEach {
            if (it.coinId == coinId) {
                binding.PriceAlertToogle.setImageResource(R.drawable.ic_checked)
                binding.PriceAlertToogle.tag = "ON"
            }
        }

        //Favourite
        binding.favtoggleButton.setOnClickListener {
            val element = FavouriteEntity(
                viewModelShared.coinIDForSharing.toString(),
                binding.NameOfCoin.text.toString(),
                binding.PriceOfCoin.text.toString().replace("$", "").trim(),
                viewModelShared.coinForSharingImage,
                viewModelShared.coinForSharingChange.toString()
            )

            if (binding.favtoggleButton.tag != "ON") {
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                viewModelShared.addToFavourites(element)
                binding.favtoggleButton.tag = "ON"
            } else {
                binding.favtoggleButton.tag = "OFF"
                viewModelShared.removeCoinFromFavourite(element)
                binding.favtoggleButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        binding.PriceAlertToogle.setOnClickListener {
            val element = AlertEntity(
                viewModelShared.coinIDForSharing.toString(),
                binding.NameOfCoin.text.toString(),
                binding.PriceOfCoin.text.toString().replace("$", "").trim(),
                viewModelShared.coinForSharingImage,
                viewModelShared.coinForSharingChange.toString()
            )
            if (binding.PriceAlertToogle.tag != "ON") {
                binding.PriceAlertToogle.setImageResource(R.drawable.ic_checked)
                viewModelShared.addToAlert(element)
                binding.PriceAlertToogle.tag = "ON"
            } else {
                binding.PriceAlertToogle.tag = "OFF"
                viewModelShared.delAlert(element)
                binding.PriceAlertToogle.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            }
        }



    }
}


