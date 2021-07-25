package com.samarth.cryptozee.utils


class CONSTANTS {
    companion object {
        val URL_API_COINGECKO by lazy { "https://api.coingecko.com/api/v3/" }
        val LOG_TAG by lazy { "MYDEBUGTAG" }
        val SINGLE_COIN_URL_DETAIL_SUFFIX by lazy {  "/?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false"}
        val SINGLE_COIN_URL_DETAIL_PREFIX  by lazy{ "coins/"}
        val SINGLE_COIN_CHART_SUFFIX by lazy { "/market_chart?vs_currency=usd&days=" }
    }
}