package com.olehsh.newsapp.common

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val DEFAULT_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

private const val SECOND = 1
private const val MINUTE = 60 * SECOND
private const val HOUR = 60 * MINUTE
private const val DAY = 24 * HOUR
private const val MONTH = 30 * DAY
private const val YEAR = 12 * MONTH

fun Context.toTimeAgo(dateString: String, inputDatePattern: String = DEFAULT_DATE_PATTERN): String {

    val now = Calendar.getInstance().timeInMillis

    val dateFormat = SimpleDateFormat(inputDatePattern, Locale.getDefault())

    val diff = (now - (dateFormat.parse(dateString)?.time ?: 0L)) / 1000

    return when {
        diff < MINUTE -> getString(R.string.txt_just_now)
        diff < 2 * MINUTE -> getString(R.string.txt_minute_ago)
        diff < 60 * MINUTE -> getString(R.string.txt_minutes_ago_format, (diff / MINUTE).toInt())
        diff < 2 * HOUR -> getString(R.string.txt_hour_ago)
        diff < 24 * HOUR -> getString(R.string.txt_hours_ago_format, (diff / HOUR).toInt())
        diff < 2 * DAY -> getString(R.string.txt_yesterday)
        diff < 30 * DAY -> getString(R.string.txt_days_ago_format, (diff / DAY).toInt())
        diff < 2 * MONTH -> getString(R.string.txt_month_ago)
        diff < 12 * MONTH -> getString(R.string.txt_months_ago_format, (diff / MONTH).toInt())
        diff < 2 * YEAR -> getString(R.string.txt_year_ago)
        else -> getString(R.string.txt_years_ago_format, (diff / YEAR).toInt())
    }
}