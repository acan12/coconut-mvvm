package app.beelabs.coconut.mvvm.support.util

import app.beelabs.coconut.mvvm.base.BaseConfig
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    fun convertToDate(epoch: Long): Date = Date(epoch * 1000L)
    fun convertToEpoch(date: Date): Long {
        var cal = Calendar.getInstance().apply {
            timeZone = TimeZone.getTimeZone("UTC")
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return (cal.timeInMillis / 1000) + 86400
    }

    private fun formatString(date: Date, pattern: String): String? {
        val form = SimpleDateFormat(pattern)
        val format: String = form.format(date)
        var dateString = ""
        for (month in BaseConfig.monthLabels) {
            val labels = month.split("_").toTypedArray()
            dateString = format.replace(labels[0].toRegex(), labels[1])
            if (dateString != format) break
        }
        return dateString
    }
}