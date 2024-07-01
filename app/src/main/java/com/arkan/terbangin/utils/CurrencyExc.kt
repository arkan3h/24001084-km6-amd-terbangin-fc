package com.arkan.terbangin.utils

import java.text.NumberFormat
import java.util.Locale

fun Double?.doubleToCurrency(
    language: String,
    country: String,
): String? {
    return try {
        val localeID = Locale(language, country)
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.format(this).toString()
    } catch (e: Exception) {
        null
    }
}

fun Double?.toIndonesianFormat() = this.doubleToCurrency("in", "ID")

fun convertCurrencyFormatString(amount: String): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    val number = amount.toDouble()
    return numberFormat.format(number).replace("Rp", "Rp").replace(",00", ",00")
}
