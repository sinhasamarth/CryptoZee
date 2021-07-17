package  com.samarth.cryptozee.ui.dataFormatter

import android.graphics.Color
import android.util.Log
import android.widget.TextView
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.data.model.SingleCoinResponse.SingleCoinDetailResponse
import kotlinx.coroutines.flow.asFlow
import java.math.BigDecimal
import java.net.URL
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

object DataFormat {
    fun getChangeFormatted(rawData: String?, textView: TextView): TextView {
        var finalChange = ""
        try {

            finalChange += if (rawData?.get(0) == '-') {
                "-" + DecimalFormat("####.##").format(rawData.substring(1).toDouble())
                    .toString()

            } else {
                "+" + DecimalFormat("###.##").format(rawData?.toDouble()).toString()

            }

            // Setting the Data of Change in 24 hours with Colours
            if (finalChange.toDouble() < 0) {
                //Change Colour to Red
                textView.setTextColor(Color.parseColor("#F24E4E"))
                textView.text = "$finalChange%"
            } else {
                //Change Colour to Green
                textView.setTextColor(Color.parseColor("#A2D970"))
                textView.text = "$finalChange%"

            }
            return textView
        } catch (e: Exception) {

            return textView
        }
    }

    fun formatPrice(rawPrice: String): String {
        if (rawPrice.toDouble() <= 0.01) {
            val price = BigDecimal(rawPrice)
            return "$" + price.toString()
        }
        return "$ " + DecimalFormat("##,###.##")
            .format(rawPrice.toDouble())
            .toString()
    }

    //Format Name to Fix Overlap
    fun formatName(name: String): String {
        return if (name.length >= 14) {
            name.substring(0, 11) + "..."
        } else {
            name
        }

    }

    // Getting The  Domain  Name
    fun getHost(url: String): String {

        var isSubdmomain = false
        var trimToDomain = false
        var urlHost = URL(url).host
        if (urlHost.contains("www.")) {
            urlHost = urlHost.replace("www.", "")

        }
        for (counter in 0 until urlHost.length) {
            if (urlHost.get(counter).equals('.') && isSubdmomain) {
                trimToDomain = true
                break
            } else if (urlHost.get(counter).equals('.')) {
                isSubdmomain = true
            }
        }
        if (trimToDomain) {
            urlHost = urlHost.replaceBefore('.', "")
            urlHost = urlHost.subSequence(1,urlHost.length-1).toString()

        }
        return (urlHost.get(0).uppercaseChar().toString() + urlHost.subSequence(1, urlHost.length)
            .toString())
    }

    // Formatting MarketCapValue
    fun marketCapTextFormatter(rawPrice: String): String {
        return ("$" + DecimalFormat("#,##,##,###").format(rawPrice.toDouble()).toString())
    }

    // Changing Api Response of Chart To ArrayList
    fun formatChartResponse(rawData: ArrayList<SingleCoinChartResponse>): ArrayList<ArrayList<Double>> {
        val response = ArrayList<ArrayList<Double>>(0)
        try {
            response.add(getResponseofCharttoArrayList(rawData[0]))
            response.add(getResponseofCharttoArrayList(rawData[1]))
            response.add(getResponseofCharttoArrayList(rawData[2]))
            response.add(getResponseofCharttoArrayList(rawData[3]))
            response.add(getResponseofCharttoArrayList(rawData[4]))

        } catch (e: Exception) {

        }
        return response
    }

    // Changing Api Response To ArrayList
    private fun getResponseofCharttoArrayList(rawData: SingleCoinChartResponse): ArrayList<Double> {
        val response = ArrayList<Double>(0)
        rawData.prices.forEach {
            if (it[1] >= 0.01) {
                response.add(
                    DecimalFormat("#####.##")
                        .format(it[1]).toDouble()
                )
            } else {
                response.add(it[1])
            }
        }
        return response
    }

    // Setting Text To infinite
    fun changeTextToNA(textView: TextView) {
        textView.setTextColor(Color.parseColor("#A2D970"))
        textView.setText("N/A")
    }

}