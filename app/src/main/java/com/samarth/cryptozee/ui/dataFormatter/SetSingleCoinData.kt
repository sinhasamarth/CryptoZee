package com.samarth.cryptozee.ui.dataFormatter

import android.util.Log
import com.samarth.cryptozee.data.model.MarketListCoinResponse.MarketCoinResponseItem
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinDetailResponse
import com.samarth.cryptozee.databinding.SingleCoinDetailFragmentBinding
import org.w3c.dom.Text
import java.math.BigDecimal

class SetSingleCoinData(
    private val response: SingleCoinDetailResponse,
    private val binding: SingleCoinDetailFragmentBinding,
    private val marketResponse:MarketCoinResponseItem
) {
    init {
        setAllDatatoView()
    }

    private fun setAllDatatoView() {

        binding.apply {
            NameOfCoin.text = response.name
            this.PriceOfCoin.text = TextFormat.formatPrice(marketResponse.currentPrice)

            TextFormat.getChangeFormatted(
                response.marketData.priceChangePercentage24h.toString(),
                this.changeInCoin
            )
            this.rank.text = response.rank.toString()
            this.NameOfCoins.text = response.name
            this.Symbol.text = response.symbol.uppercase()
            try{
                this.website.text = TextFormat.getHost(response.links.homepage[0])
                this.ExploreUrl.text = TextFormat.getHost(response.links.blockchainSite[0])
            }
            catch (e:Exception){
                Log.d("TAG", e.toString())
            }
            if (response.marketData.totalSupply.toString().isNotEmpty()) {
                this.totalSupply.text = response.marketData.totalSupply.toInt().toString()
            }
            if (response.marketData.circulatingSupply.toString().isNotEmpty()) {
                this.circulatingSupply.text = response.marketData.circulatingSupply.toInt().toString()
            }
            if (marketResponse.marketCap.toString().isNotEmpty()) {
                this.marketCap.text = TextFormat.marketCapTextFormatter(marketResponse.marketCap.toString())
            }
        }
    }
}