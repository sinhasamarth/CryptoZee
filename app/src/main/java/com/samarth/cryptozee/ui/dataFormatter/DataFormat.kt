package  com.samarth.cryptozee.ui.dataFormatter

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.TextView
import com.samarth.cryptozee.data.model.api.singleCoinResponse.SingleCoinChartResponse
import com.samarth.cryptozee.ui.dataFormatter.SetWalletData.getPriceFormatted
import java.math.BigDecimal
import java.net.URL
import java.text.DecimalFormat
import kotlin.collections.ArrayList

object DataFormat {
    @SuppressLint("SetTextI18n")

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

    fun formatPrice(rawPrice: String, accurateToDecimal:Boolean = false ): String {
        if (rawPrice.toDouble() <= 0.01 && !accurateToDecimal) {
            val price = BigDecimal(rawPrice)
            return "$$price"
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
        for (counter in urlHost.indices) {
            if (urlHost[counter] == '.' && isSubdmomain) {
                trimToDomain = true
                break
            } else if (urlHost[counter] == '.') {
                isSubdmomain = true
            }
        }
        if (trimToDomain) {
            urlHost = urlHost.replaceBefore('.', "")
            urlHost = urlHost.subSequence(1, urlHost.length - 1).toString()

        }
        return (urlHost[0].uppercaseChar().toString() + urlHost.subSequence(1, urlHost.length)
            .toString())
    }


    // Changing Api Response of Chart To ArrayList
    fun formatChartResponse(rawData: ArrayList<SingleCoinChartResponse>): ArrayList<ArrayList<Double>> {
        val response = ArrayList<ArrayList<Double>>(0)
        try {
            response.add(getResponsiveCharityArrayList(rawData[0]))
            response.add(getResponsiveCharityArrayList(rawData[1]))
            response.add(getResponsiveCharityArrayList(rawData[2]))
            response.add(getResponsiveCharityArrayList(rawData[3]))
            response.add(getResponsiveCharityArrayList(rawData[4]))

        } catch (e: Exception) {

        }
        return response
    }

    // Changing Api Response To ArrayList
    private fun getResponsiveCharityArrayList(rawData: SingleCoinChartResponse): ArrayList<Double> {
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
        textView.text = "N/A"
    }

    fun formatQuantity(rawQuantity: Double): String {
        return DecimalFormat("#####.###")
            .format(rawQuantity)
            .toDouble().toString()
    }
}
