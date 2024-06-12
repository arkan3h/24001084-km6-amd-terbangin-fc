package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AirportCity(
    val name: String,
    val code: String,
) : Parcelable
