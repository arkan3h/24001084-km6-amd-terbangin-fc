package com.arkan.terbangin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusPayment(
    val statusPayment: String,
) : Parcelable
