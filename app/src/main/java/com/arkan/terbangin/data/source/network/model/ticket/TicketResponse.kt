package com.arkan.terbangin.data.source.network.model.ticket

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TicketResponse(
    @SerializedName("data")
    val data: List<TicketDataResponse?>?,
    @SerializedName("message")
    val message: String?,
)
