package  com.samarth.cryptozee.ui.dataFormatter

import android.graphics.Color
import android.widget.TextView
import java.math.BigDecimal
import java.net.URL
import java.text.DecimalFormat

object TextFormat {
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
        if (rawPrice.toDouble()<= 0.01){
            val price = BigDecimal(rawPrice)
           return "$" + price.toString()
        }
        return "$ " + DecimalFormat("##,###.##")
            .format(rawPrice.toDouble())
            .toString()
    }

    fun formatName(name: String): String {
        return if (name.length >= 14) {
            name.substring(0, 11) + "..."
        } else {
            name
        }

    }


    fun getHost(url: String): String {

        var isSubdmomain = false
        var trimToDomain = false
        var urlHost = URL(url).host
        if (urlHost.contains("www.")) {
            urlHost = urlHost.replace("www.", "")

        }
        for (counter in 0 until urlHost.length) {
            if (urlHost.get(counter).equals('.')&& isSubdmomain) {
                trimToDomain = true
                break
            } else if (urlHost.get(counter).equals('.')) {
                isSubdmomain = true
            }
        }
       if (trimToDomain){
           urlHost = urlHost.replaceBefore('.',"")
       }
        return (urlHost.get(0).uppercaseChar().toString() + urlHost.subSequence(1, urlHost.length)
            .toString())
    }

    fun marketCapTextFormatter(rawPrice: String): String {
        return ("$" + DecimalFormat("#,##,##,###").format(rawPrice.toDouble()).toString())
    }

}
