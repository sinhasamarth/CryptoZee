package  com.samarth.cryptozee.ui.dataFormatter

import java.text.DecimalFormat

object TextFormat {
    fun getChangeFormatted(rawData: String?): Double {
        var finalChange = ""
        return try {

            finalChange += if (rawData?.get(0) == '-') {
                "-" + DecimalFormat("####.##").format(rawData.substring(1).toDouble())
                    .toString()

            } else {
                "+" + DecimalFormat("###.##").format(rawData?.toDouble()).toString()
                //                    changeInPrice.setTextColor(Color.parseColor("#A2D970"))
            }
            finalChange.toDouble()
        } catch (e: Exception) {
            finalChange.toDouble()
        }
    }

    fun formatPrice(currentPrice: String): String {
        return "$ " + DecimalFormat("##,###.##")
            .format(currentPrice.toDouble())
            .toString()
    }

    fun formatName(name: String): String {
        return if (name.length >= 14) {
            name.substring(0, 11) + "..."
        } else {
            name
        }

    }
}
