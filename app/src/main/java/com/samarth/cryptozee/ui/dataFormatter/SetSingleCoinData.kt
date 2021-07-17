package com.samarth.cryptozee.ui.dataFormatter

import android.graphics.Color
import android.util.Log
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.samarth.cryptozee.R
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponseItem
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding

object SetSingleCoinData {

    fun setAllTextDataToView(
        response: SingleCoinDetailResponse,
        binding: SingleCoinDetailFragmentBinding,
        marketResponse: MarketCoinResponseItem
    ) {

        binding.apply {
            NameOfCoin.text = response.name
            this.PriceOfCoin.text = DataFormat.formatPrice(marketResponse.currentPrice)

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
                this.totalSupply.text = response.marketData.totalSupply.toInt().toString()
            }
            if (response.marketData.circulatingSupply.toString().isNotEmpty()) {
                this.circulatingSupply.text =
                    response.marketData.circulatingSupply.toInt().toString()
            }
            if (marketResponse.marketCap.toString().isNotEmpty()) {
                this.marketCap.text =
                    DataFormat.marketCapTextFormatter(marketResponse.marketCap.toString())
            }
        }
    }

    fun setAllChartsToView(
        response: ArrayList<SingleCoinChartResponse>,
        binding: SingleCoinDetailFragmentBinding,
    ) {
        val formattedResponse = DataFormat.formatChartResponse(response)
        binding.toggleButton.check(R.id.OneDay)
        setToChart(formattedResponse, binding.chart, 0)
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.OneDay -> setToChart(formattedResponse, binding.chart, 0)
                R.id.OneWeek -> setToChart(formattedResponse, binding.chart, 1)
                R.id.OneMonth -> setToChart(formattedResponse, binding.chart, 2)
                R.id.OneYear -> setToChart(formattedResponse, binding.chart, 3)
                R.id.max -> setToChart(formattedResponse, binding.chart, 4)
            }
        }
    }

    private fun setToChart(
        prices: ArrayList<ArrayList<Double>>,
        chart: AAChartView,
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
        chart.aa_drawChartWithChartModel(aaChartModel)
    }

}