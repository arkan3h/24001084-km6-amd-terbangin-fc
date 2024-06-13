package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Airport(
    val city: String,
    val continent: String,
    val country: String,
    val code: String,
    val id: String,
    val name: String,
    val picture: String,
    val terminal: String,
) : Parcelable
