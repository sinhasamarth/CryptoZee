package com.samarth.cryptozee.ui.dataFormatter

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.samarth.cryptozee.MainActivity
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinDetailResponse
import  com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import com.samarth.cryptozee.viewModelShared

object SetSingleCoinData {


    //Setting Data For SingleCoin Fragment
    fun setAllTextDataToView(
        response: SingleCoinDetailResponse,
        binding: SingleCoinDetailFragmentBinding,
    ) {
        MainActivity.startLoading()
        // Setting Data From Response
        binding.apply {
            NameOfCoin.text = response.name
            this.PriceOfCoin.text =
                DataFormat.formatPrice(response.marketData.current_price.usd.toString())

            DataFormat.getChangeFormatted(
                response.marketData.priceChangePercentage24h.toString(),
                this.changeInCoin
            )
            this.rank.text = response.rank.toString()
            this.NameOfCoins.text = response.name
            this.Symbol.text = response.symbol.uppercase()
            try {
                this.website.text = DataFormat.getHost(response.links.homepage[0])
                this.ExploreUrl.text = DataFormat.getHost(response.links.blockchainSite[0])
            } catch (e: Exception) {
                Log.d("TAG", e.toString())
            }
            if (response.marketData.totalSupply.toString().isNotEmpty()) {
                if (response.marketData.totalSupply.toInt().toString().equals("0")) {
                    DataFormat.changeTextToNA(this.totalSupply)
                } else {
                    this.totalSupply.text = response.marketData.totalSupply.toInt().toString()
                }
            }
            if (response.marketData.circulatingSupply.toString().isNotEmpty()) {
                this.circulatingSupply.text =
                    response.marketData.circulatingSupply.toInt().toString()
            }

        }
        MainActivity.stopLoading()
    }


    // Setting And Caching the Chart
    fun setAllChartsToView(
        response: ArrayList<SingleCoinChartResponse>,
        binding: SingleCoinDetailFragmentBinding,
    ) {
        val formattedResponse = DataFormat.formatChartResponse(response)
        binding.toggleButton.check(R.id.OneDay)
        DataFormat.getChangeFormatted(viewModelShared.coinForSharingChange.toString(), binding.changeInCoin)
        setToChart(formattedResponse, binding, 0)
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.OneDay -> setToChart(formattedResponse, binding, 0)
                R.id.OneWeek -> setToChart(formattedResponse, binding, 1)
                R.id.OneMonth -> setToChart(formattedResponse, binding, 2)
                R.id.OneYear -> setToChart(formattedResponse, binding, 3)
                R.id.max -> setToChart(formattedResponse, binding, 4)
            }
        }
    }

    private fun setToChart(
        prices: ArrayList<ArrayList<Double>>,
        binding: SingleCoinDetailFragmentBinding,
        interval: Int
    ) {
        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .axesTextColor("#ffffff")
            .gradientColorEnable(true)
            .animationType(AAChartAnimationType.SwingTo)
            .tooltipEnabled(true)
            .xAxisVisible(false)
            .yAxisVisible(false)
            .backgroundColor(Color.TRANSPARENT)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Price")
                        .data(prices[interval].toArray())
                )
            )
        binding.chart.aa_drawChartWithChartModel(aaChartModel)

    }

}