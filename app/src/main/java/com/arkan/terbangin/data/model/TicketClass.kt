package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketClass(
    val name: String,
    val price: String,
) : Parcelable
