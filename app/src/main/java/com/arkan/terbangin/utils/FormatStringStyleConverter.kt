package com.arkan.terbangin.utils

fun String.capitalizeFirstLetter(): String {
    if (this.isEmpty()) return this // Return the original string if it's empty

    return this[0].uppercaseChar() + this.substring(1).lowercase()
}
