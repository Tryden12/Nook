package com.tryden.nook.ui

import java.text.SimpleDateFormat
import java.util.*

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("M/d/yy")
    return format.format(date)
}